package Filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/verifyOTPRegain.jsp")
public class OTPTourConfirmationFilterRegain implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	
    	response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Kiểm tra xem giá trị của session.getAttribute("otp") có bằng null hay không
        HttpSession session = httpRequest.getSession(false);

        if (session == null || session.getAttribute("otp") == null) {
            // Nếu giá trị bằng null hoặc không có, chuyển hướng đến trang checkingTourNoLogin.jsp
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/checkingTourNoLogin.jsp");
        } else {
            // Giá trị tồn tại, cho phép tiếp tục xử lý request
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Không cần thiết trong trường hợp này
    }

    @Override
    public void destroy() {
        // Không cần thiết trong trường hợp này
    }
}
