package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.customerDAO;

@WebServlet("/ChangeInformation")
public class ChangeInformationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		// Xử lý yêu cầu POST
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		try {
			customerDAO.getIntance().update(name, phone, address, username);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		response.sendRedirect("information.jsp");
	}

}
