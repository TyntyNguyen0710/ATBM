package Controller;

import dao.bookingDAO;
import dao.customerDAO;
import dao.tourDAO;
import model.Booking;
import model.Customer;
import model.Tour;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@WebServlet("/GenerateSignatureServlet")
public class GenerateSignatureServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String invoiceHash = request.getParameter("invoiceHash");
        String privateKeyBase64 = request.getParameter("privateKeyPem");
        String bookingIdStr = request.getParameter("bookingId");

        try {
            // Load Private Key từ Base64
            PrivateKey privateKey = loadPrivateKeyFromBase64(privateKeyBase64);

            // Ký hash bằng Private Key (SHA256withRSA)
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(invoiceHash.getBytes("UTF-8"));

            byte[] digitalSignature = signature.sign();
            String signatureBase64 = Base64.getEncoder().encodeToString(digitalSignature);

            // Lấy thông tin booking
            int bookingId = Integer.parseInt(bookingIdStr);
            Booking booking = bookingDAO.getIntance().selectByBookingId(bookingId);

            if (booking == null) {
                response.getWriter().println("Không tìm thấy booking.");
                return;
            }

            Customer customer = customerDAO.getIntance().selectByEmail(booking.getEmail());
            Tour tour = tourDAO.getIntance().selectByID(String.valueOf(booking.getTourID()));

            // Gửi email chứa chữ ký số
            sendSignedInvoiceEmail(customer.getEmail(), customer.getName(), tour, booking, invoiceHash, signatureBase64);

            // Trả kết quả
            request.setAttribute("invoiceHash", invoiceHash);
            request.setAttribute("digitalSignature", signatureBase64);
            request.setAttribute("publicKeyBase64", getPublicKeyFromPrivate(privateKey)); // Gửi luôn Public Key để verify

            request.getRequestDispatcher("signatureResult.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi khi ký: " + e.getMessage());
        }
    }

    // Load Private Key từ Base64
    private PrivateKey loadPrivateKeyFromBase64(String base64Key) throws Exception {
        String cleanKey = base64Key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] keyBytes = Base64.getDecoder().decode(cleanKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    // Lấy Public Key từ Private Key (để người dùng verify)
    private String getPublicKeyFromPrivate(PrivateKey privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        java.security.spec.RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(privateKey, java.security.spec.RSAPublicKeySpec.class);
        java.security.PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    // Gửi email (giữ nguyên)
    private void sendSignedInvoiceEmail(String toEmail, String customerName, Tour tour, Booking booking,
                                        String invoiceHash, String signatureBase64) {
        // Giữ nguyên code gửi email như trước
        final String from = "philong2m@gmail.com";
        final String password = "nqjk dbbg ilbi faaf";

        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        javax.mail.Session mailSession = javax.mail.Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(from, password);
            }
        });

        try {
            javax.mail.Message message = new javax.mail.internet.MimeMessage(mailSession);
            message.setFrom(new javax.mail.internet.InternetAddress(from));
            message.setRecipients(javax.mail.Message.RecipientType.TO, javax.mail.internet.InternetAddress.parse(toEmail));
            message.setSubject("Hóa đơn đặt tour + Chữ ký số");

            String body = "Kính gửi " + customerName + ",\n\n"
                    + "=== HASH HÓA ĐƠN (SHA-256) ===\n" + invoiceHash + "\n\n"
                    + "=== CHỮ KÝ SỐ (Base64 - Ký bằng Private Key) ===\n" + signatureBase64 + "\n\n"
                    + "Bạn có thể dùng Public Key tương ứng để xác minh chữ ký này.";

            message.setText(body);
            javax.mail.Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}