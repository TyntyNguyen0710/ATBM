package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class VerifyOTPTourServlet
 */
@WebServlet("/VerifyOTPTourServlet")
public class VerifyOTPTourServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.setContentType("text/html;charset=UTF-8");
    	request.setCharacterEncoding("UTF-8");
        // Lấy giá trị OTP nhập từ trang verifyOTP.jsp
        String enteredOTP = request.getParameter("otp");

        // Lấy giá trị OTP từ session
        HttpSession session = request.getSession();
        String storedOTP = (String) session.getAttribute("otp");

        // Kiểm tra xem OTP nhập vào có khớp với OTP từ session hay không
        if (enteredOTP != null && storedOTP != null && enteredOTP.equals(storedOTP)) {
            // Xác nhận thành công

            // Chuyển hướng đến trang checkingTourNoLoginFinal
            response.sendRedirect("checkingTourNoLoginFinal.jsp");
        } else {
            // Xác nhận thất bại
            response.sendRedirect("verifyOTPRegain.jsp");
        }
    }
}
