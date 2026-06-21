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
 * KHÔNG hardcode trong source code.
 *
 * BẢN MỞ RỘNG: thêm 2 hàm dùng cho CreateAccountServlet và
 * ChangePasswordAfterLoginServlet (trước đây 2 servlet này tự định
 * nghĩa lại toàn bộ logic gửi mail + hardcode credential riêng —
 * nay dùng chung 1 nơi để dễ bảo trì và đảm bảo không sót chỗ nào còn
 * hardcode).
 */
public final class EmailUtil {

    private EmailUtil() {
    }

    public static void sendSignedInvoiceEmail(String toEmail, String customerName, Tour tour,
                                                Booking booking, String invoiceHash, String signatureBase64) {
        String body = "Kính gửi " + customerName + ",\n\n"
                + "Hợp đồng đặt tour \"" + tour.getName() + "\" đã được ký số thành công.\n\n"
                + "=== HASH HÓA ĐƠN (SHA-256) ===\n" + invoiceHash + "\n\n"
                + "=== CHỮ KÝ SỐ (Base64) ===\n" + signatureBase64 + "\n\n"
                + "Vui lòng lưu lại email này. Nếu nội dung hợp đồng bị thay đổi sau khi ký, "
                + "hệ thống sẽ tự động cảnh báo khi bạn xem lại hợp đồng.";

        send(toEmail, "Xác nhận ký số hợp đồng đặt tour", body);
    }

    public static void sendAccountCreatedEmail(String toEmail, String fullName) {
        String body = "Kính gửi " + fullName + ",\n\n"
                + "Cảm ơn bạn đã đăng ký tài khoản. Tài khoản của bạn đã được tạo thành công.";

        send(toEmail, "Tạo tài khoản thành công", body);
    }

    public static void sendPasswordChangedEmail(String toEmail, String username) {
        if (toEmail == null) {
            System.err.println("[EmailUtil] Không gửi được email đổi mật khẩu — thiếu địa chỉ email cho user: " + username);
            return;
        }
        String body = "Mật khẩu của tài khoản \"" + username + "\" vừa được thay đổi thành công.\n\n"
                + "Nếu bạn không thực hiện thay đổi này, vui lòng liên hệ ngay bộ phận hỗ trợ.";

        send(toEmail, "Xác nhận thay đổi mật khẩu", body);
    }

    /** Hàm dùng chung — đọc credential từ biến môi trường, gửi email đơn giản dạng text. */
    private static void send(String toEmail, String subject, String body) {
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
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);

        } catch (MessagingException e) {
            // Log lỗi gửi mail, không ném ra ngoài làm fail toàn bộ luồng nghiệp vụ
            // chính (đăng ký / đổi mật khẩu / ký số vẫn nên thành công dù gửi email lỗi).
            System.err.println("[EmailUtil] Gửi email thất bại: " + e.getMessage());
        }
    }
}
