package Controller;

import java.io.IOException;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
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
import model.User; // Thay thế bằng lớp User của bạn

@WebServlet("/ChangePasswordAfterLogin")
public class ChangePasswordAfterLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		// Lấy thông tin người dùng từ session hoặc database
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username"); // Thay thế bằng đối tượng User của bạn
		User user = null;
		try {
			user = userDAO.getIntance().selectByID(username);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (username != null) {
			// Lấy mật khẩu hiện tại từ biểu mẫu
			String currentPassword = request.getParameter("currentPassword");

			// Kiểm tra xem mật khẩu hiện tại có khớp với mật khẩu trong database không
			if (user.getPassword().equals(currentPassword)) {
				// Lấy mật khẩu mới và xác nhận mật khẩu mới từ biểu mẫu
				String newPassword = request.getParameter("newPassword");
				String confirmPassword = request.getParameter("confirmPassword");

				// Kiểm tra xem mật khẩu mới và xác nhận mật khẩu mới có khớp nhau không
				if (newPassword.equals(confirmPassword)) {
					try {
						userDAO.getIntance().updatePassword(username, newPassword);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

					// Hiển thị thông báo đổi mật khẩu thành công và chuyển hướng người dùng
					response.sendRedirect("login.jsp");
					try {
						sendConfirmationEmail(username);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					// Hiển thị thông báo lỗi khi mật khẩu mới và xác nhận không khớp
					response.sendRedirect("changePasswordMismatched.jsp");
				}
			} else {
				// Hiển thị thông báo lỗi khi mật khẩu hiện tại không chính xác
				response.sendRedirect("changePasswordIncorrect.jsp");
			}
		} else {
			// Hiển thị thông báo lỗi khi không tìm thấy thông tin người dùng trong session
			response.sendRedirect("login.jsp"); // Hoặc chuyển hướng đến trang đăng nhập
		}
	}

	private void sendConfirmationEmail(String username) throws ClassNotFoundException {
		// Cài đặt thông tin của email
		String to = customerDAO.getIntance().selectEmailByUsername(username); // Thay thế bằng địa chỉ email của người
																				// dùng
		String subject = "Xác nhận thay đổi mật khẩu";
		String body = "Mật khẩu của tài khoản " + username + " đã được thay đổi thành công.";

		// Cài đặt thông tin của tài khoản email gửi
		final String from = "philong2m@gmail.com";
		final String password = "nqjk dbbg ilbi faaf";

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
