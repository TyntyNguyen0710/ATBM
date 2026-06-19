package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/optionBookingTour")
public class optionBookingTour extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        boolean isLoggedIn = username != null && !username.isEmpty();

        // Chuyển hướng đến trang JSP tương ứng với điều kiện đã xác định
        if (isLoggedIn) {
            response.sendRedirect("bookingTourAfterLogin.jsp");
        } else {
            response.sendRedirect("bookingTour.jsp");
        }
    }
}
