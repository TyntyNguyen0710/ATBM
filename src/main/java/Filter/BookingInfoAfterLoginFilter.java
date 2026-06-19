package Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/checkingTourAfterLogin.jsp")
public class BookingInfoAfterLoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
    	
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Lấy thông tin email từ session
        HttpSession session = httpRequest.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;

        // Kiểm tra xem email có tồn tại không
        if (username != null) {
            // Nếu email tồn tại, cho phép tiếp tục chuỗi filter
            chain.doFilter(request, response);
        } else {
            // Nếu không có email, chuyển hướng về trang trangchu.jsp
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/trangchu.jsp");
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
