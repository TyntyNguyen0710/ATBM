package Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/verifyOTPForgetPassword.jsp")
public class OTPConfirmationAccessRegain implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
    	
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Lấy thông tin về OTP từ session
        HttpSession session = httpRequest.getSession(false);
        String otpPassword = (session != null) ? (String) session.getAttribute("otpPassword") : null;

        // Kiểm tra xem OTP có tồn tại không
        if (otpPassword != null) {
            // Nếu OTP tồn tại, cho phép tiếp tục chuỗi filter
            chain.doFilter(request, response);
        } else {
            // Nếu không có OTP, chuyển hướng về trang forgetPassword.jsp
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/forgetPassword.jsp");
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
