package Controller;

import dao.customerDAO;
import model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/KeyManagement")
public class KeyManagementServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            Customer customer = customerDAO.getIntance().selectByUsername(username);
            int customerId = customer.getID();

            String activePublicKey = customerDAO.getIntance().getActivePublicKey(customerId);
            List<String> allKeys = customerDAO.getIntance().getAllPublicKeysByCustomerId(customerId);

            request.setAttribute("activePublicKey", activePublicKey);
            request.setAttribute("allKeys", allKeys);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi khi tải dữ liệu khóa!");
        }

        request.getRequestDispatcher("keyManagement.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String newPublicKey = request.getParameter("publicKey");
        String fromBooking = request.getParameter("fromBooking");

        try {
            Customer customer = customerDAO.getIntance().selectByUsername(username);
            int customerId = customer.getID();

            customerDAO.getIntance().addNewPublicKey(customerId, newPublicKey);

            if ("true".equals(fromBooking)) {
                // Quay lại trang hóa đơn
                response.sendRedirect("invoice.jsp");
            } else {
                response.sendRedirect("KeyManagement");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi khi lưu Public Key: " + e.getMessage());
        }
    }
}