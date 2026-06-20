package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import DataBase.JDBCUltil;

@WebServlet("/checkingLogin")
public class CheckingLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		// Retrieve form parameters
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		// Validate credentials against the database
		if (validateCredentials(username, password)) {
			// Authentication successful, set user role in session
			String role = null;
			try {
				role = getUserRole(username);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			session.setAttribute("role", role);

			// Redirect based on role
			if ("Admin".equals(role)) {
				response.sendRedirect("admin.jsp");
			} else if ("Customer".equals(role)) {
				response.sendRedirect("checkingTourAfterLogin.jsp");
			} else {
				// Handle other roles or redirect to a default page
				response.sendRedirect("defaultPage.jsp");
			}
		} else {
			// Authentication failed, forward back to the login page with an error message
			request.setAttribute("errorMessage", "Invalid username or password");
			request.getRequestDispatcher("regain.jsp").forward(request, response);
		}
	}

	private boolean validateCredentials(String username, String password) {
		// Your logic to validate credentials against the database
		// Return true if valid, false otherwise
		// Example: You might call a method in a DAO class to validate the credentials
		return true;
	}

	private String getUserRole(String username) throws ClassNotFoundException {
		try (Connection connection = JDBCUltil.getConnection()) {
			String sql = "SELECT role FROM Customer WHERE username=?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, username);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						return resultSet.getString("role");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; // Return null if role is not found
	}
}