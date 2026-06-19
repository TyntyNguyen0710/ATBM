package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import DataBase.JDBCUltil;
import dao.customerDAO;
import dao.userDAO;
import model.Customer;
import model.User;

@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		// Lấy thông tin từ form
		String fullName = request.getParameter("fullName");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		// Xử lý thông tin đăng ký ở đây (ví dụ: lưu vào cơ sở dữ liệu)

		try {
			if (!isValidUser(username, password)) {
			    // Đăng nhập thành công
				userDAO ud = userDAO.getIntance();
				customerDAO cd = customerDAO.getIntance();
				try {
					ud.insert(new User(username, password));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					cd.insert(new Customer(fullName, address, email, phone, new User(username, password)));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//			 Gửi email
				sendRegistrationEmail(fullName, email);

//			 Hiển thị thông báo đăng ký thành công
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println("<html><body>");
				out.println("<h2>Registration Successful</h2>");
				out.println("<p>Thank you for registering. Your account has been created successfully.</p>");
				out.println("</body></html>");
				response.sendRedirect("login.jsp");

			} else {
			    // Đăng ký thất bại
			    response.sendRedirect("error.jsp");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void sendRegistrationEmail(String fullName, String email) {
		// Thông tin tài khoản email gửi
		final String from = "philong2m@gmail.com";
		final String password = "nqjk dbbg ilbi faaf";

		// Thông tin máy chủ SMTP
		String host = "smtp.gmail.com";
		int port = 587;

		// Cấu hình Properties cho máy chủ SMTP
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);

		// Tạo phiên làm việc cho session email
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(from, password);
			}
		});

		try {
			// Tạo đối tượng MimeMessage
			MimeMessage message = new MimeMessage(session);

			// Đặt người gửi và người nhận
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

			// Đặt chủ đề và nội dung email
			message.setSubject("Tạo tài khoản thành công");
			message.setText(
					"Kính gửi: " + fullName + ",\n\nCảm ơn vì đã đăng kí. Tài khoản của bạn đã được tạo thành công.");

			// Gửi email
			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	  private boolean isValidUser(String username, String password) throws ClassNotFoundException {
	        Connection connection = null;
	        try {
	            // Use a connection pool library like Apache DBCP or HikariCP
	            connection = JDBCUltil.getConnection();

	            String sql = "SELECT * FROM Users WHERE username=? AND password=?";
	            try (PreparedStatement statement = connection.prepareStatement(sql)) {
	                statement.setString(1, username);
	                statement.setString(2, password);

	                try (ResultSet resultSet = statement.executeQuery()) {
	                    return resultSet.next(); // If there is a result, it means the username and password are valid
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace(); // Log the exception using a logging framework
	            return false;
	        } finally {
	            // Close the database connection in a finally block
	            JDBCUltil.closeConnection(connection);
	        }

	  }
}
