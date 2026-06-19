package Controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.bookingDAO;
import dao.customerDAO;
import dao.tourDAO;
import model.Booking;
import model.Customer;
import model.Tour;

@WebServlet("/SaveBooking")
public class SaveBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		int tourId = (int) session.getAttribute("tourID");
		String username = (String) session.getAttribute("username");

		String fullName = request.getParameter("fullName");
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String departureDate = request.getParameter("departureDate");
		String adults = request.getParameter("adults");
		String childs = request.getParameter("childs");

		try {
			Booking booking = null;

			if (username != null && !username.equals("")) {
				booking = saveBookingToDatabaseAfterLogin(tourId, fullName, address, email, phone,
						departureDate, adults, childs, username);
			} else {
				booking = saveBookingToDatabase(tourId, fullName, address, email, phone, departureDate, adults, childs);
			}

			// === ĐÃ XÓA dòng sendBookingConfirmationEmail(booking); ===

			// Set dữ liệu để chuyển sang invoice.jsp
			request.setAttribute("booking", booking);

			Tour tour = tourDAO.getIntance().selectByID(String.valueOf(tourId));
			request.setAttribute("tour", tour);

			Customer customer = (username != null && !username.isEmpty())
					? customerDAO.getIntance().selectByUsername(username)
					: new Customer(fullName, address, email, phone);

			request.setAttribute("customer", customer);

			// Forward sang trang hóa đơn + chữ ký số
			RequestDispatcher dispatcher = request.getRequestDispatcher("invoice.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			// Có thể forward sang trang lỗi nếu cần
		}
	}

	// ==================== CÁC PHƯƠNG THỨC CŨ GIỮ NGUYÊN ====================

	private Booking saveBookingToDatabaseAfterLogin(int tourId, String fullName, String address, String email,
			String phone, String departureDate, String adults, String childs, String username) throws ClassNotFoundException {
		int customerID = customerDAO.getIntance().selectByUsername(username).getID();
		bookingDAO tour = bookingDAO.getIntance();
		Customer customer = new Customer(fullName, address, email, phone);
		Booking booking = new Booking(customer, Date.valueOf(departureDate), Integer.valueOf(adults),
				Integer.valueOf(childs), customer.getEmail(), tourId, customerID);
		tour.insert(booking);
		return booking;
	}

	private Booking saveBookingToDatabase(int tourId, String fullName, String address, String email, String phone,
			String departureDate, String adults, String childs) throws SQLException, ClassNotFoundException {
		bookingDAO tour = bookingDAO.getIntance();
		Customer customer = new Customer(fullName, address, email, phone);
		Booking booking = new Booking(customer, Date.valueOf(departureDate), Integer.valueOf(adults),
				Integer.valueOf(childs), customer.getEmail(), tourId);
		customerDAO.getIntance().insertNoLogin(customer);
		tour.insertNoLogin(booking);
		return booking;
	}

	private void sendBookingConfirmationEmail(Booking booking) {
		// Giữ nguyên method này để dùng lại sau (khi gửi email có chữ ký số)
		final String username = "philong2m@gmail.com";
		final String password = "nqjk dbbg ilbi faaf";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(booking.getCustomer().getEmail()));
			message.setSubject("Booking Confirmation");
			message.setText("Dear " + booking.getCustomer().getName() + ",\n\n"
					+ "Cảm ơn vì bạn đã đặt hàng. Đơn đặt hàng của bạn là:\n\n" + "Mã số Tour: "
					+ booking.getTourID() + "\n" + "Ngày khởi hành: " + booking.getDepartureDate() + "\n"
					+ "Số lượng người lớn: " + booking.getNoAdults() + "\n" + "Số lượng trẻ em "
					+ booking.getNoChildren() + "\n\n"
					+ "Chúng tôi rất vui vì được phục vụ bạn. Chúc bạn có một chuyện đi tuyệt vời!");

			Transport.send(message);
			System.out.println("Email sent successfully!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}