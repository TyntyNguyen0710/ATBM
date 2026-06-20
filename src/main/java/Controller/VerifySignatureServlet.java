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
 * ĐÂY LÀ SERVLET TRẢ LỜI TRỰC TIẾP CÂU HỎI: "Nếu vô đổi thông tin trong
 * DB rồi F5 thì có verify lại hay không?"
 *
 * CÓ — vì servlet này chạy LẠI TỪ ĐẦU mỗi lần người dùng load trang xem
 * hợp đồng (GET /VerifySignatureServlet?bookingId=...), không hề đọc
 * cờ "đã verify" cũ nào cả. Quy trình mỗi lần gọi:
 *
 *   1. Lấy chữ ký đã lưu (hash cũ + signature cũ + publicKeyId) từ DB
 *   2. Lấy dữ liệu booking/tour/customer HIỆN TẠI từ DB (có thể đã bị sửa)
 *   3. Băm lại dữ liệu hiện tại bằng ĐÚNG công thức lúc ký (InvoiceHashUtil)
 *   4. So sánh hash mới tính với hash đã lưu:
 *        - Khác nhau → dữ liệu đã bị sửa sau khi ký → TAMPERED, dừng ở đây,
 *          không cần verify chữ ký (vì hash đã sai thì chữ ký chắc chắn
 *          không khớp dữ liệu hiện tại).
 *        - Giống nhau → đi tiếp bước 5
 *   5. Lấy public key theo publicKeyId đã lưu lúc ký
 *        - Nếu publicKey đó đã bị revoke (báo mất khóa) → cảnh báo
 *          KEY_REVOKED (xem thêm: vẫn có thể coi hợp đồng hợp lệ nếu
 *          ký TRƯỚC thời điểm revoke — tùy chính sách, có ghi chú dưới)
 *   6. Gọi Signature.verify(hash, signature, publicKey)
 *        - false → chữ ký giả/sai → TAMPERED
 *        - true  → VALID
 *
 * Kết quả KHÔNG bao giờ lấy từ cột "status" cũ trong DB để quyết định —
 * cột đó chỉ được GHI lại sau khi có kết quả thật của lần verify này.
 */
@WebServlet("/VerifySignatureServlet")
public class VerifySignatureServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookingIdStr = request.getParameter("bookingId");
        if (bookingIdStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu bookingId.");
            return;
        }

        try {
            long bookingId = Long.parseLong(bookingIdStr);

            // ── Bước 1: Lấy chữ ký đã lưu ──────────────────────────────
            InvoiceSignature savedSig = InvoiceSignatureDAO.getInstance().selectLatestByBookingId(bookingId);
            if (savedSig == null) {
                request.setAttribute("verifyStatus", "NOT_SIGNED");
                request.setAttribute("message", "Hợp đồng này chưa được ký số.");
                request.getRequestDispatcher("signatureResult.jsp").forward(request, response);
                return;
            }

            // ── Bước 2: Lấy dữ liệu HIỆN TẠI (có thể đã bị sửa) ────────
            Booking booking = bookingDAO.getIntance().selectByBookingId(bookingId);
            if (booking == null) {
                request.setAttribute("verifyStatus", "ERROR");
                request.setAttribute("message", "Không tìm thấy booking.");
                request.getRequestDispatcher("signatureResult.jsp").forward(request, response);
                return;
            }
            Customer customer = customerDAO.getIntance().selectByEmail(booking.getEmail());
            Tour tour = tourDAO.getIntance().selectByID(String.valueOf(booking.getTourID()));

            // ── Bước 3: Băm lại dữ liệu HIỆN TẠI bằng đúng công thức cũ ─
            String currentHash = InvoiceHashUtil.hashInvoice(booking, tour, customer);

            String finalStatus;
            String message;

            // ── Bước 4: So khớp hash ───────────────────────────────────
            if (!currentHash.equals(savedSig.getInvoiceHash())) {
                finalStatus = InvoiceSignature.STATUS_TAMPERED;
                message = "CẢNH BÁO: Dữ liệu hợp đồng đã bị thay đổi sau khi khách hàng ký. "
                        + "Hợp đồng KHÔNG còn hiệu lực pháp lý ở trạng thái hiện tại.";
            } else {
                // ── Bước 5: Lấy public key đã dùng để ký ───────────────
                CustomerPublicKey usedKey =
                        CustomerPublicKeyDAO.getInstance().selectById(savedSig.getPublicKeyId());

                if (usedKey == null) {
                    finalStatus = InvoiceSignature.STATUS_TAMPERED;
                    message = "Không tìm thấy khóa công khai dùng để ký — không thể xác thực.";
                } else {
                    // ── Bước 6: Verify chữ ký bằng Signature.verify ────
                    boolean sigValid = verifySignature(
                            savedSig.getInvoiceHash(), savedSig.getSignature(), usedKey.getPublicKey());

                    if (!sigValid) {
                        finalStatus = InvoiceSignature.STATUS_TAMPERED;
                        message = "Chữ ký số không hợp lệ — dữ liệu hoặc chữ ký đã bị can thiệp.";
                    } else if (!usedKey.isActive()
                            && usedKey.getRevokedAt().before(savedSig.getSignedAt()) == false) {
                        /* Khóa đã bị revoke. Chính sách áp dụng ở đây:
                           - Nếu thời điểm KÝ xảy ra TRƯỚC thời điểm revoke
                             → hợp đồng vẫn coi là VALID (khách ký hợp lệ
                             khi khóa còn sống), chỉ cảnh báo thông tin.
                           - Nếu thời điểm KÝ xảy ra SAU thời điểm revoke
                             (vd: kẻ gian dùng khóa bị lộ để ký sau khi đã
                             báo mất) → KHÔNG chấp nhận, đúng kịch bản bạn
                             mô tả "báo mất 7h, đơn ký lúc 9h thì không
                             được duyệt". */
                        finalStatus = InvoiceSignature.STATUS_KEY_REVOKED;
                        message = "Hợp đồng được ký SAU thời điểm khóa bị báo mất/thu hồi — "
                                + "không được công nhận, cần khách hàng ký lại bằng khóa mới.";
                    } else {
                        finalStatus = InvoiceSignature.STATUS_VALID;
                        message = "Hợp đồng hợp lệ. Dữ liệu khớp với chữ ký đã ký, chưa bị thay đổi.";
                    }
                }
            }

            // Ghi lại kết quả lần verify này vào DB (chỉ để tra cứu/audit —
            // không dùng giá trị này cho lần verify tiếp theo).
            InvoiceSignatureDAO.getInstance().updateStatusAfterCheck(savedSig.getId(), finalStatus);

            request.setAttribute("verifyStatus", finalStatus);
            request.setAttribute("message", message);
            request.setAttribute("invoiceHash", currentHash);
            request.setAttribute("savedHash", savedSig.getInvoiceHash());
            request.setAttribute("signature", savedSig.getSignature());
            request.setAttribute("signedAt", savedSig.getSignedAt());
            request.setAttribute("booking", booking);
            request.setAttribute("tour", tour);
            request.setAttribute("customer", customer);

            request.getRequestDispatcher("signatureResult.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "bookingId không hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi xác thực chữ ký.");
        }
    }

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
