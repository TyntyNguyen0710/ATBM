package Controller;

import java.io.IOException;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.bookingDAO;

@WebServlet("/DeleteBookingServlet")
public class DeleteBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		// Retrieve the booking ID and email from the form
		String bookingIdString = request.getParameter("bookingId");
		String bookingEmail = request.getParameter("email");

		if (bookingIdString != null && !bookingIdString.isEmpty()) {
			try {
				// Convert the ID to a Long
				Long bookingId = Long.valueOf(bookingIdString);

				// Call the DAO to delete the booking
				bookingDAO bookingdao = bookingDAO.getIntance();
				bookingdao.deleteByID(bookingId);

				// Send confirmation email
				sendConfirmationEmail(bookingEmail, bookingIdString);

				// Redirect back to the booking list page
				response.sendRedirect(request.getHeader("referer"));

			} catch (NumberFormatException e) {
				// Handle invalid booking ID (not a number)
				response.getWriter().println("Invalid booking ID");
			}
		} else {
			// Handle missing booking ID
			response.getWriter().println("Booking ID is missing");
		}
	}

	private void sendConfirmationEmail(String email, String bookingID) {
		String from = "philong2m@gmail.com";
		String password = "123@";

		// Set the recipient email address
		String to = email;

		// Set the subject and body of the email
		String subject = "Tour Booking Cancellation Confirmation";
		String body = "Đơn hàng: " +bookingID +  "của bạn đã bị hủy.";

		// Set the host and port for your email provider
		String host = "smtp.gmail.com";
		String port = "587";

		// Get the system properties
		Properties properties = System.getProperties();

		// Set the mail server
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		// Get the Session object
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		try {
			// Create a default MimeMessage object
			MimeMessage message = new MimeMessage(session);

			// Set the sender and recipient addresses
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set the subject and body of the email
			message.setSubject(subject);
			message.setText(body);

			// Send the email
			Transport.send(message);
			System.out.println("Email sent successfully to " + to);

		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
