package Controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

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

			request.setAttribute("booking", booking);

			Tour tour = tourDAO.getIntance().selectByID(String.valueOf(tourId));
			request.setAttribute("tour", tour);

			Customer customer = (username != null && !username.isEmpty())
					? customerDAO.getIntance().selectByUsername(username)
					: new Customer(fullName, address, email, phone);

			request.setAttribute("customer", customer);

			String activePublicKey = customerDAO.getIntance().getActivePublicKey(customer.getID());
			request.setAttribute("activePublicKey", activePublicKey);

			RequestDispatcher dispatcher = request.getRequestDispatcher("invoice.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
}