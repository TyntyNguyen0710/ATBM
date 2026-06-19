package Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/changePasswordMismatched.jsp")
public class ChangePasswordFilterAfterLoginMismatched implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Lấy thông tin username từ session
        HttpSession session = httpRequest.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;

        // Kiểm tra xem username có tồn tại không
        if (username != null && !username.isEmpty()) {
            // Nếu username tồn tại, cho phép tiếp tục chuỗi filter
            chain.doFilter(request, response);
        } else {
            // Nếu không có hoặc không tồn tại username, chuyển hướng về trang chủ
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
