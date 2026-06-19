package Controller;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.customerDAO;
import dao.userDAO;

@WebServlet("/ChangePassword")
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		// Lấy dữ liệu từ biểu mẫu
		String newPassword = request.getParameter("newPassword");
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		// Thực hiện cập nhật mật khẩu trong cơ sở dữ liệu
		try {
			userDAO.getIntance().updatePassword(username, newPassword);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Gửi email xác nhận
		try {
			sendConfirmationEmail(username, newPassword);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// In ra màn hình để kiểm tra
		response.sendRedirect("login.jsp");
	}

	private void sendConfirmationEmail(String username, String newPassword) throws ClassNotFoundException {
		// Cài đặt thông tin của email
		String to = customerDAO.getIntance().selectEmailByUsername(username); // Thay thế bằng địa chỉ email của người
																				// dùng
		String subject = "Xác nhận thay đổi mật khẩu";
		String body = "Mật khẩu của tài khoản " + username + " đã được thay đổi thành công. Mật khẩu bạn vừa đổi là: "
				+ newPassword;

		// Cài đặt thông tin của tài khoản email gửi
		final String from = "philong2m@gmail.com";
		final String password = "hgxt eszi yqcs uhzb";

		// Cài đặt thông tin của máy chủ SMTP
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		// Xác thực bằng tài khoản email gửi
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		};

		// Tạo phiên làm việc
		Session session = Session.getInstance(properties, authenticator);

		try {
			// Tạo đối tượng MimeMessage
			MimeMessage message = new MimeMessage(session);

			// Đặt thông tin người gửi, người nhận và tiêu đề
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);

			// Đặt nội dung của email
			message.setText(body);

			// Gửi email
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
