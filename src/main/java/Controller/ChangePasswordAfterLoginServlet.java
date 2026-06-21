package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.customerDAO;
import dao.userDAO;
import model.User;
import util.EmailUtil;
import util.PasswordUtil;

/**
 * THAY ĐỔI BẢO MẬT so với bản gốc:
 *
 *  1) So khớp mật khẩu hiện tại bằng PasswordUtil.verifyPassword()
 *     (BCrypt), KHÔNG dùng user.getPassword().equals(currentPassword)
 *     nữa — phép so sánh "==" / equals() trực tiếp với hash KHÔNG BAO
 *     GIỜ đúng (hash không thể so sánh kiểu chuỗi thường).
 *
 *  2) Mật khẩu MỚI được hash bằng PasswordUtil.hashPassword() trước
 *     khi gọi updatePassword() — đây là chỗ thứ 2 (ngoài đăng ký) có
 *     thể sinh ra plain text nếu không sửa, nay đã được chặn.
 *
 *  3) Credential email chuyển sang EmailUtil đọc từ biến môi trường.
 */
@WebServlet("/ChangePasswordAfterLogin")
public class ChangePasswordAfterLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            User user = userDAO.getIntance().selectByID(username);
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            String currentPassword = request.getParameter("currentPassword");
            String newPassword     = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            // ── So khớp mật khẩu hiện tại bằng BCrypt, không equals() trực tiếp ──
            boolean currentPasswordCorrect = PasswordUtil.isBcryptHash(user.getPassword())
                    ? PasswordUtil.verifyPassword(currentPassword, user.getPassword())
                    : user.getPassword().equals(currentPassword); // hỗ trợ user cũ chưa migrate

            if (!currentPasswordCorrect) {
                response.sendRedirect("changePasswordIncorrect.jsp");
                return;
            }

            if (newPassword == null || !newPassword.equals(confirmPassword)) {
                response.sendRedirect("changePasswordMismatched.jsp");
                return;
            }

            // ── Hash mật khẩu MỚI trước khi lưu ──
            String newHashedPassword = PasswordUtil.hashPassword(newPassword);
            userDAO.getIntance().updatePassword(username, newHashedPassword);

            String email = customerDAO.getIntance().selectEmailByUsername(username);
            EmailUtil.sendPasswordChangedEmail(email, username);

            response.sendRedirect("login.jsp");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp");
        }
    }
}
