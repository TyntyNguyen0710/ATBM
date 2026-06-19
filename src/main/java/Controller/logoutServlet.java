package Controller;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class logoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public logoutServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		response.sendRedirect("trangchu.jsp");
	}

	@Override
	public void init() throws ServletException {
		Timer timer = new Timer(true);

		// Thêm công việc định kỳ với Timer
		timer.scheduleAtFixedRate(new LogoutTask(), 30 * 60 * 1000, 30 * 60 * 1000);
	}

	class LogoutTask extends TimerTask {
		@Override
		public void run() {
			HttpSession session = getSession();
			if (session != null) {
				session.invalidate();
			}
		}

		private HttpSession getSession() {
			// Lấy session từ ServletContext
			return ((HttpServletRequest) getServletContext()).getSession(false);
		}
	}
}
