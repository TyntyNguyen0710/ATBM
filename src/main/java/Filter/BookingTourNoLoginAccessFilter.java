package Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/bookingTourAfterLogin.jsp")
public class BookingTourNoLoginAccessFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
    	
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

  
        HttpSession session = httpRequest.getSession(false);
        String userRole = (session != null) ? (String) session.getAttribute("role") : null;
        if ("Customer".equals(userRole)) {
            chain.doFilter(request, response);
        } else {
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
