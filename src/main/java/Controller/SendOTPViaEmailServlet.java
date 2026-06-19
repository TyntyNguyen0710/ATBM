package Controller;

import java.io.IOException;

import java.util.Properties;

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

@WebServlet("/SendOTPViaEmailServlet")
public class SendOTPViaEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		// Lấy giá trị tourID từ yêu cầu
		String email = request.getParameter("email");


		// Sinh mã OTP
		String otp = generateOTP();

		// Lưu OTP vào session
		HttpSession session = request.getSession();
		session.setAttribute("otp", otp);
		session.setAttribute("email", email);

		// Gửi OTP đến địa chỉ email
		sendOTPEmail(email, otp);

		// Chuyển hướng đến trang verifyOTP.jsp
		response.sendRedirect("verifyOTP.jsp");
	}

	private String generateOTP() {
		// Logic sinh mã OTP ở đây (ví dụ: mã ngẫu nhiên)
		return String.valueOf((int) (Math.random() * 9000) + 1000);
	}

	private void sendOTPEmail(String toEmail, String otp) {
		final String username = "philong2m@gmail.com";
		final String password = "nqjk dbbg ilbi faaf";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject("Mã xác thực");
			message.setText("Mã xác thực của bạn là " + otp);

			Transport.send(message);

			System.out.println("Mã OTP gửi về mail thành công!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
