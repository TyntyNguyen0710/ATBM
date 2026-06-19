package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class checkingTourServlet
 */
@WebServlet("/checkingTour")
public class checkingTourServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		// Kiểm tra xem người dùng đã đăng nhập hay chưa
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		boolean isLoggedIn = username != null && !username.isEmpty();
		String role = (String) session.getAttribute("role");
		// Chuyển hướng đến trang JSP tương ứng với điều kiện đã xác định

		if (isLoggedIn && role.equals("Admin")) {
		    response.sendRedirect("admin.jsp");
		} else if (isLoggedIn && role.equals("Customer")) {
		    response.sendRedirect("checkingTourAfterLogin.jsp");
		} else {
		    response.sendRedirect("checkingTourNoLogin.jsp");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);

	}

}
