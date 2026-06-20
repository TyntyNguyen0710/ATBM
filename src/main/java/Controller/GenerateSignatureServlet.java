package Controller;

import dao.CustomerPublicKeyDAO;
import dao.InvoiceSignatureDAO;
import dao.bookingDAO;
import dao.customerDAO;
import dao.tourDAO;
import model.Booking;
import model.Customer;
import model.CustomerPublicKey;
import model.InvoiceSignature;
import model.Tour;
import util.InvoiceHashUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Servlet nhận chữ ký đã được tạo SẴN Ở PHÍA CLIENT (trình duyệt khách
 * hàng, bằng Web Crypto API hoặc tool ký riêng) — KHÔNG nhận private
 * key, KHÔNG tự ký hộ khách.
 *
 * THAY ĐỔI SO VỚI BẢN TRƯỚC:
 *   1) KHÔNG còn nhận "privateKeyPem" từ request — đây là lỗi bảo mật
 *      nghiêm trọng (private key đi qua mạng). Bỏ hoàn toàn tham số này.
 *   2) Nhận "signatureBase64" — chữ ký khách đã tự ký bằng private key
 *      TRÊN TRÌNH DUYỆT của họ, server chỉ kiểm tra xem chữ ký đó có
 *      khớp với public key đã đăng ký hay không (Signature.verify),
 *      rồi mới lưu lại.
 *   3) invoiceHash KHÔNG nhận từ client gửi lên — server tự tính lại
 *      từ dữ liệu booking hiện có trong DB (InvoiceHashUtil), để chống
 *      trường hợp client gửi hash giả không khớp dữ liệu thật.
 *   4) Dùng java.security.Signature (SHA256withRSA) — đúng API dành
 *      cho CHỮ KÝ SỐ, không dùng Cipher (vốn dùng cho MÃ HÓA, là khái
 *      niệm khác — xem giải thích đã trao đổi).
 */
@WebServlet("/GenerateSignatureServlet")
public class GenerateSignatureServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookingIdStr     = request.getParameter("bookingId");
        String signatureBase64  = request.getParameter("signatureBase64");

        if (bookingIdStr == null || signatureBase64 == null || signatureBase64.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu dữ liệu chữ ký.");
            return;
        }

        try {
            long bookingId = Long.parseLong(bookingIdStr);

            // 1) Lấy dữ liệu THẬT từ DB — không tin dữ liệu client tự khai
            Booking booking = bookingDAO.getIntance().selectByBookingId(bookingId);
            if (booking == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy booking.");
                return;
            }

            Customer customer = customerDAO.getIntance().selectByEmail(booking.getEmail());
            Tour tour = tourDAO.getIntance().selectByID(String.valueOf(booking.getTourID()));

            if (customer == null || tour == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Dữ liệu khách hàng / tour không hợp lệ.");
                return;
            }

            // 2) Server tự tính hash từ dữ liệu thật trong DB
            String invoiceHash = InvoiceHashUtil.hashInvoice(booking, tour, customer);

            // 3) Lấy public key đang hoạt động của khách
            CustomerPublicKey activeKey =
                    CustomerPublicKeyDAO.getInstance().selectActiveByCustomerId(booking.getCustomerID());

            if (activeKey == null) {
                response.getWriter().println("Khách hàng chưa đăng ký public key. Vui lòng tạo cặp khóa trước.");
                return;
            }

            // 4) Verify chữ ký khách gửi lên KHỚP với public key đã đăng ký
            //    trước khi chấp nhận lưu — không lưu mù chữ ký chưa kiểm tra.
            boolean validSignature = verifySignature(invoiceHash, signatureBase64, activeKey.getPublicKey());

            if (!validSignature) {
                response.getWriter().println("Chữ ký không hợp lệ — không khớp với public key đã đăng ký.");
                return;
            }

            // 5) Lưu vào DB
            InvoiceSignature sig = new InvoiceSignature();
            sig.setBookingId(bookingId);
            sig.setInvoiceHash(invoiceHash);
            sig.setSignature(signatureBase64);
            sig.setPublicKeyId(activeKey.getId());

            InvoiceSignatureDAO.getInstance().insert(sig);

            // 6) Gửi email xác nhận (đọc credential từ biến môi trường — xem EmailUtil)
            util.EmailUtil.sendSignedInvoiceEmail(
                    customer.getEmail(), customer.getName(), tour, booking, invoiceHash, signatureBase64);

            request.setAttribute("bookingId", bookingId);
            request.getRequestDispatcher("VerifySignatureServlet?bookingId=" + bookingId)
                   .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "bookingId không hợp lệ.");
        } catch (Exception e) {
            // Log chi tiết phía server, KHÔNG trả chi tiết lỗi ra cho client.
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi xử lý chữ ký số.");
        }
    }

    /** Kiểm tra signature (Base64) có khớp với hash + publicKey (Base64, X.509) không. */
    private boolean verifySignature(String invoiceHash, String signatureBase64, String publicKeyBase64) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyBase64);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(invoiceHash.getBytes(java.nio.charset.StandardCharsets.UTF_8));

        byte[] signatureBytes = Base64.getDecoder().decode(signatureBase64);
        return sig.verify(signatureBytes);
    }
}
