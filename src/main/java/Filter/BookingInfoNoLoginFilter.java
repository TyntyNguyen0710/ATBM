package Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/checkingTourNoLoginFinal.jsp")
public class BookingInfoNoLoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// Lấy thông tin email từ session
		HttpSession session = httpRequest.getSession(false);
		String email = (session != null) ? (String) session.getAttribute("email") : null;

		// Kiểm tra xem email có tồn tại không
		if (email != null) {
			// Nếu email tồn tại, cho phép tiếp tục chuỗi filter
			chain.doFilter(request, response);
		} else {
			// Nếu không có email, chuyển hướng về trang trangchu.jsp
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/checkingTourNoLogin.jsp");
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Khởi tạo filter - được gọi một lần khi filter được tạo
	}

	@Override
	public void destroy() {
		// Hủy filter - được gọi khi filter bị hủy
	}
}
