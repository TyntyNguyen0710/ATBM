package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.userDAO;
import dao.customerDAO;
import model.User;
import util.PasswordUtil;

/**
 * THAY ĐỔI BẢO MẬT QUAN TRỌNG so với bản gốc:
 *
 *  1) LỖI NGHIÊM TRỌNG ĐÃ SỬA: hàm validateCredentials() bản gốc luôn
 *     "return true" — nghĩa là BẤT KỲ username/password nào cũng đăng
 *     nhập được, kể cả gõ sai hoàn toàn. Đây là lỗ hổng nghiêm trọng
 *     hơn cả việc lưu mật khẩu plain text. Đã sửa lại để thực sự lấy
 *     user từ DB và so khớp mật khẩu.
 *
 *  2) So khớp mật khẩu bằng PasswordUtil.verifyPassword() (BCrypt),
 *     KHÔNG so sánh chuỗi "==" / "equals" trực tiếp với plain text
 *     trong DB nữa.
 *
 *  3) Thông báo lỗi đăng nhập dùng chung 1 câu "Sai tài khoản hoặc
 *     mật khẩu" cho cả 2 trường hợp (username không tồn tại / mật
 *     khẩu sai) — tránh lộ thông tin cho kẻ tấn công biết username
 *     nào đang tồn tại trong hệ thống (User Enumeration).
 */
@WebServlet("/checkingLogin")
public class CheckingLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null || username.isBlank() || password.isBlank()) {
            request.setAttribute("errorMessage", "Vui lòng nhập đầy đủ tài khoản và mật khẩu.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        try {
            boolean valid = validateCredentials(username, password);

            if (valid) {
                String role = getUserRole(username);

                // Đổi session ID sau khi đăng nhập thành công — chống Session
                // Fixation Attack (kẻ tấn công gài sẵn session ID trước khi
                // nạn nhân đăng nhập, rồi chiếm session sau khi nạn nhân login).
                request.changeSessionId();

                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("role", role);

                if ("Admin".equals(role)) {
                    response.sendRedirect("admin.jsp");
                } else if ("Customer".equals(role)) {
                    response.sendRedirect("checkingTourAfterLogin.jsp");
                } else {
                    response.sendRedirect("trangchu.jsp");
                }
            } else {
                // Thông báo chung chung — không tiết lộ "sai username" hay "sai password"
                request.setAttribute("errorMessage", "Sai tài khoản hoặc mật khẩu.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Có lỗi xảy ra, vui lòng thử lại sau.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    /**
     * Validate thật — lấy user từ DB rồi so khớp mật khẩu bằng BCrypt.
     * Tự động hỗ trợ cả dữ liệu CŨ (chưa migrate, còn plain text trong DB)
     * để không làm gãy đăng nhập của user có sẵn trước khi chạy migrate
     * (xem PasswordMigrationUtil).
     */
    private boolean validateCredentials(String username, String password) throws ClassNotFoundException {
        User user = userDAO.getIntance().selectByID(username);
        if (user == null) {
            return false;
        }

        String storedPassword = user.getPassword();

        if (PasswordUtil.isBcryptHash(storedPassword)) {
            return PasswordUtil.verifyPassword(password, storedPassword);
        }

        // Dữ liệu cũ còn plain text (trước khi áp dụng hash) — so sánh tạm
        // thời rồi NÂNG CẤP NGAY thành hash để lần sau không còn plain text.
        boolean legacyMatch = storedPassword != null && storedPassword.equals(password);
        if (legacyMatch) {
            String newHash = PasswordUtil.hashPassword(password);
            userDAO.getIntance().updatePassword(username, newHash);
        }
        return legacyMatch;
    }

    private String getUserRole(String username) throws ClassNotFoundException {
        // Dùng hàm có sẵn trong customerDAO — Customer model hiện chưa có
        // field "role" (dù bảng Customer trong DB đã có cột này), nên
        // không gọi customer.getRole(). Nếu sau này bổ sung field role
        // vào Customer.java, có thể đổi lại dùng selectByUsername() để
        // tránh phải query DB 2 lần (1 lần lấy Customer, 1 lần lấy role).
        return customerDAO.getIntance().getUserRole(username);
    }
}
