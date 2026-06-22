package Controller;

import java.io.IOException;
import java.sql.Date;

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        int tourId = (int) session.getAttribute("tourID");
        String username = (String) session.getAttribute("username");

        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String departureDate = request.getParameter("departureDate");
        int adults = Integer.parseInt(request.getParameter("adults"));
        int childs = Integer.parseInt(request.getParameter("childs"));

        try {
            Tour tour = tourDAO.getIntance().selectByID(String.valueOf(tourId));
            request.setAttribute("tour", tour);

            Customer tempCustomer = new Customer(fullName, address, email, phone);

            Booking booking;

            if (username != null && !username.isEmpty()) {
                // Đã đăng nhập → insert bình thường
                int customerID = customerDAO.getIntance().selectByUsername(username).getID();
                booking = new Booking(tempCustomer, Date.valueOf(departureDate), adults, childs, email, tourId, customerID);
                bookingDAO.getIntance().insert(booking);
            } else {
                // Chưa đăng nhập → insertNoLogin
                booking = new Booking(tempCustomer, Date.valueOf(departureDate), adults, childs, email, tourId);
                bookingDAO.getIntance().insertNoLogin(booking);
            }

            request.setAttribute("booking", booking);
            request.setAttribute("customer", tempCustomer);

            // Chuyển sang trang hóa đơn + chữ ký
            RequestDispatcher dispatcher = request.getRequestDispatcher("invoice.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi khi lưu booking: " + e.getMessage());
        }
    }
}