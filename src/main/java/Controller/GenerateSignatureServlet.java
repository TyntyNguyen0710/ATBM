package Controller;

import dao.bookingDAO;
import dao.customerDAO;
import dao.tourDAO;
import model.Booking;
import model.Customer;
import model.Tour;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.SecureRandom;
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
            // Load Private Key
            PrivateKey privateKey = loadPrivateKeyFromBase64(privateKeyBase64);

            // Mã hóa hash theo đúng logic RSA.java (Hybrid RSA + AES)
            String encryptedResult = encryptWithPrivateKey(invoiceHash, privateKey);

            // Lấy thông tin booking
            int bookingId = Integer.parseInt(bookingIdStr);
            Booking booking = bookingDAO.getIntance().selectByBookingId(bookingId);

            if (booking == null) {
                response.getWriter().println("Không tìm thấy booking.");
                return;
            }

            Customer customer = customerDAO.getIntance().selectByEmail(booking.getEmail());
            Tour tour = tourDAO.getIntance().selectByID(String.valueOf(booking.getTourID()));

            // Gửi email
            sendSignedInvoiceEmail(customer.getEmail(), customer.getName(), tour, booking, invoiceHash, encryptedResult);

            request.setAttribute("invoiceHash", invoiceHash);
            request.setAttribute("encryptedResult", encryptedResult); // Kết quả mã hóa
            request.getRequestDispatcher("signatureResult.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi: " + e.getMessage());
        }
    }

    // ==================== HÀM MÃ HÓA GIỐNG HỆT RSA.java ====================
    private String encryptWithPrivateKey(String plainText, PrivateKey privateKey) throws Exception {
        if (plainText == null || plainText.isEmpty()) {
            return "";
        }

        // 1. Tạo AES key ngẫu nhiên
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(256);
        SecretKey aesKey = kgen.generateKey();

        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // 2. Mã hóa dữ liệu bằng AES
        Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey, ivSpec);
        byte[] encryptedBytes = aesCipher.doFinal(plainText.getBytes("UTF-8"));

        // 3. Mã hóa AES key + IV bằng RSA (dùng Private Key)
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] encAESKey = rsaCipher.doFinal(aesKey.getEncoded());
        byte[] encIV = rsaCipher.doFinal(iv);

        // 4. Gói dữ liệu giống RSA.java
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             DataOutputStream dos = new DataOutputStream(baos)) {
            dos.writeUTF("AES/CBC/PKCS5Padding");
            dos.writeInt(256);
            dos.writeInt(encAESKey.length);
            dos.write(encAESKey);
            dos.writeInt(encIV.length);
            dos.write(encIV);
            dos.writeInt(encryptedBytes.length);
            dos.write(encryptedBytes);

            return Base64.getEncoder().encodeToString(baos.toByteArray());
        }
    }

    private PrivateKey loadPrivateKeyFromBase64(String base64Key) throws Exception {
        String cleanKey = base64Key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replaceAll("\\s+", "")
                .trim();

        byte[] keyBytes = Base64.getDecoder().decode(cleanKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    // Gửi email (giữ nguyên)
    private void sendSignedInvoiceEmail(String toEmail, String customerName, Tour tour, Booking booking,
                                        String invoiceHash, String encryptedResult) {
        // Giữ nguyên code gửi email như trước...
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
            message.setSubject("Hóa đơn đặt tour + Dữ liệu đã mã hóa");

            String body = "Kính gửi " + customerName + ",\n\n"
                    + "=== HASH HÓA ĐƠN ===\n" + invoiceHash + "\n\n"
                    + "=== DỮ LIỆU ĐÃ MÃ HÓA (Hybrid RSA + AES) ===\n" + encryptedResult + "\n\n"
                    + "Bạn có thể dùng Public Key để giải mã bằng class RSA.java";

            message.setText(body);
            javax.mail.Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}