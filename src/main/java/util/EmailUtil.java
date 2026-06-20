package util;

import model.Booking;
import model.Tour;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Gửi email — credential đọc từ biến môi trường (MAIL_USER, MAIL_PASS),
 * KHÔNG hardcode trong source code (xem JDBCUltil.java để biết lý do/cách
 * set biến môi trường tương tự).
 */
public final class EmailUtil {

    private EmailUtil() {
    }

    public static void sendSignedInvoiceEmail(String toEmail, String customerName, Tour tour,
                                                Booking booking, String invoiceHash, String signatureBase64) {
        String from = System.getenv("MAIL_USER");
        String password = System.getenv("MAIL_PASS");

        if (from == null || password == null) {
            System.err.println("[EmailUtil] Thiếu biến môi trường MAIL_USER / MAIL_PASS — không thể gửi email.");
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        String finalFrom = from;
        Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(finalFrom, password);
            }
        });

        try {
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Xác nhận ký số hợp đồng đặt tour");

            String body = "Kính gửi " + customerName + ",\n\n"
                    + "Hợp đồng đặt tour \"" + tour.getName() + "\" đã được ký số thành công.\n\n"
                    + "=== HASH HÓA ĐƠN (SHA-256) ===\n" + invoiceHash + "\n\n"
                    + "=== CHỮ KÝ SỐ (Base64) ===\n" + signatureBase64 + "\n\n"
                    + "Vui lòng lưu lại email này. Nếu nội dung hợp đồng bị thay đổi sau khi ký, "
                    + "hệ thống sẽ tự động cảnh báo khi bạn xem lại hợp đồng.";

            message.setText(body);
            Transport.send(message);

        } catch (MessagingException e) {
            // Log lỗi gửi mail, không ném ra ngoài làm fail toàn bộ luồng ký số
            // (việc ký vẫn nên thành công dù gửi email thất bại).
            System.err.println("[EmailUtil] Gửi email thất bại: " + e.getMessage());
        }
    }
}
