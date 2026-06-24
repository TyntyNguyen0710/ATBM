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
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@WebServlet("/GenerateSignatureServlet")
public class GenerateSignatureServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        String invoiceHash = request.getParameter("invoiceHash");
        String privateKeyBase64 = request.getParameter("privateKeyPem");
        String bookingIdStr = request.getParameter("bookingId");
        String publicKeyInput = request.getParameter("publicKey");

        try {
            int bookingId = Integer.parseInt(bookingIdStr);
            Booking booking = bookingDAO.getIntance().selectByBookingId(bookingId);

            if (booking == null) {
                response.getWriter().println("Không tìm thấy booking.");
                return;
            }

            Customer customer = customerDAO.getIntance().selectByEmail(booking.getEmail());
            Tour tour = tourDAO.getIntance().selectByID(String.valueOf(booking.getTourID()));

            String publicKeyBase64 = customerDAO.getIntance().getActivePublicKey(customer.getID());
            if (publicKeyBase64 == null || publicKeyBase64.trim().isEmpty()) {
                publicKeyBase64 = publicKeyInput;
            }

            PrivateKey privateKey = loadPrivateKeyFromBase64(privateKeyBase64);
            String encryptedResult = encryptWithPrivateKey(invoiceHash, privateKey);

            boolean isValid = verifySignature(invoiceHash, encryptedResult, publicKeyBase64);

            if (!isValid) {
                request.setAttribute("signatureError", "Chữ ký không hợp lệ!");
                request.setAttribute("activePublicKey", publicKeyBase64);
                request.setAttribute("invoiceHash", invoiceHash);
                request.setAttribute("booking", booking);
                request.setAttribute("tour", tour);
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("invoice.jsp").forward(request, response);
                return;
            }

            bookingDAO.getIntance().updateSignature(bookingId, encryptedResult);

            request.setAttribute("tour", tour);
            request.setAttribute("booking", booking);
            request.setAttribute("customer", customer);

            sendSignedInvoiceEmail(customer.getEmail(), customer.getName(), tour, booking, invoiceHash, encryptedResult);

            request.getRequestDispatcher("signatureResult.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi: " + e.getMessage());
        }
    }

    // ==================== GIỮ NGUYÊN CÁC HÀM HỖ TRỢ ====================
    private boolean verifySignature(String originalHash, String encryptedData, String publicKeyBase64) {
        try {
            String decrypted = decryptWithPublicKey(encryptedData, publicKeyBase64);
            return originalHash.equals(decrypted);
        } catch (Exception e) {
            return false;
        }
    }

    private String decryptWithPublicKey(String encryptedData, String publicKeyBase64) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyBase64.trim());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);

        byte[] data = Base64.getDecoder().decode(encryptedData);

        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             DataInputStream dis = new DataInputStream(bais)) {

            String algorithm = dis.readUTF();
            int keySize = dis.readInt();
            int encKeyLen = dis.readInt();
            byte[] encAESKey = new byte[encKeyLen];
            dis.readFully(encAESKey);

            int encIVLen = dis.readInt();
            byte[] encIV = new byte[encIVLen];
            dis.readFully(encIV);

            int cipherLen = dis.readInt();
            byte[] encryptedBytes = new byte[cipherLen];
            dis.readFully(encryptedBytes);

            Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            rsaCipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] aesKeyBytes = rsaCipher.doFinal(encAESKey);
            byte[] iv = rsaCipher.doFinal(encIV);

            SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            Cipher aesCipher = Cipher.getInstance(algorithm);
            aesCipher.init(Cipher.DECRYPT_MODE, aesKey, ivSpec);
            byte[] decryptedBytes = aesCipher.doFinal(encryptedBytes);

            return new String(decryptedBytes, "UTF-8");
        }
    }

    private String encryptWithPrivateKey(String plainText, PrivateKey privateKey) throws Exception {
        if (plainText == null || plainText.isEmpty()) return "";

        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(256);
        SecretKey aesKey = kgen.generateKey();

        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey, ivSpec);
        byte[] encryptedBytes = aesCipher.doFinal(plainText.getBytes("UTF-8"));

        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] encAESKey = rsaCipher.doFinal(aesKey.getEncoded());
        byte[] encIV = rsaCipher.doFinal(iv);

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

    private void sendSignedInvoiceEmail(String toEmail, String customerName, Tour tour, Booking booking,
                                    String invoiceHash, String encryptedResult) {
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
            javax.mail.internet.MimeMessage message = new javax.mail.internet.MimeMessage(mailSession);
            message.setFrom(new javax.mail.internet.InternetAddress(from));
            message.setRecipients(javax.mail.Message.RecipientType.TO, javax.mail.internet.InternetAddress.parse(toEmail));
            message.setSubject("Xác nhận đặt tour và Chữ ký số - " + tour.getName());

            // ===== Nội dung email =====
            String body = "Kính gửi " + customerName + ",\n\n"
                    + "Bạn đã đặt tour thành công và ký chữ ký số.\n\n"
                    + "=== THÔNG TIN TOUR ===\n"
                    + "Tour: " + tour.getName() + "\n"
                    + "Ngày khởi hành: " + booking.getDepartureDate() + "\n"
                    + "Số người lớn: " + booking.getNoAdults() + "\n"
                    + "Số trẻ em: " + booking.getNoChildren() + "\n\n"
                    + "Đính kèm theo email này là:\n"
                    + "- hash_hoa_don.txt : Giá trị hash của hóa đơn\n"
                    + "- chu_ky_so.txt    : Chữ ký số đã tạo\n\n"
                    + "Vui lòng lưu lại các file này để đối chiếu sau này.\n\n"
                    + "Cảm ơn bạn đã sử dụng dịch vụ!\n\n"
                    + "Trân trọng,\n"
                    + "Đội ngũ hỗ trợ";

            javax.mail.internet.MimeBodyPart textPart = new javax.mail.internet.MimeBodyPart();
            textPart.setText(body);

            javax.mail.internet.MimeBodyPart hashPart = new javax.mail.internet.MimeBodyPart();
            hashPart.setContent(invoiceHash, "text/plain; charset=UTF-8");
            hashPart.setFileName("hash_hoa_don.txt");

            javax.mail.internet.MimeBodyPart signaturePart = new javax.mail.internet.MimeBodyPart();
            signaturePart.setContent(encryptedResult, "text/plain; charset=UTF-8");
            signaturePart.setFileName("chu_ky_so.txt");

            javax.mail.internet.MimeMultipart multipart = new javax.mail.internet.MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(hashPart);
            multipart.addBodyPart(signaturePart);

            message.setContent(multipart);

            // Gửi email
            javax.mail.Transport.send(message);

            System.out.println("Email đã gửi thành công kèm 2 file đính kèm cho: " + toEmail);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}