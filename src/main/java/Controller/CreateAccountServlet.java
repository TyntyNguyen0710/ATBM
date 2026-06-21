package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataBase.JDBCUltil;
import dao.customerDAO;
import dao.userDAO;
import model.Customer;
import model.User;
import util.EmailUtil;
import util.PasswordUtil;

/**
 * THAY ĐỔI BẢO MẬT so với bản gốc — ĐÂY LÀ NƠI NGĂN PLAIN TEXT MỚI SINH RA:
 *
 *  1) Mật khẩu được HASH bằng PasswordUtil.hashPassword() NGAY TRƯỚC KHI
 *     lưu vào DB (cả bảng Users lẫn object User gắn trong Customer).
 *     Đây chính là lý do bạn không cần chạy lại PasswordMigrationUtil
 *     nữa — vì từ giờ không còn chỗ nào ghi plain text vào DB cả.
 *
 *  2) isValidUser() đổi tên thành isUsernameTaken() — tên cũ gây hiểu
 *     lầm logic (đang dùng để check "username đã tồn tại chưa" chứ
 *     không phải kiểm tra mật khẩu hợp lệ), giữ lại cùng mục đích
 *     nhưng không còn so sánh password ở bước đăng ký (không cần thiết).
 *
 *  3) Credential email chuyển sang đọc từ biến môi trường (EmailUtil),
 *     bỏ hardcode "philong2m@gmail.com" / app password trong code.
 */
@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String fullName = request.getParameter("fullName");
        String address  = request.getParameter("address");
        String phone    = request.getParameter("phone");
        String email    = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (isBlank(fullName) || isBlank(email) || isBlank(username) || isBlank(password)) {
            request.setAttribute("errorMessage", "Vui lòng nhập đầy đủ thông tin bắt buộc.");
            request.getRequestDispatcher("createAccount.jsp").forward(request, response);
            return;
        }

        try {
            if (isUsernameTaken(username)) {
                request.setAttribute("errorMessage", "Tên đăng nhập đã tồn tại, vui lòng chọn tên khác.");
                request.getRequestDispatcher("createAccount.jsp").forward(request, response);
                return;
            }

            // ── Hash mật khẩu TRƯỚC KHI lưu — KHÔNG bao giờ lưu plain text ──
            String hashedPassword = PasswordUtil.hashPassword(password);

            userDAO.getIntance().insert(new User(username, hashedPassword));
            customerDAO.getIntance().insert(
                    new Customer(fullName, address, email, phone, new User(username, hashedPassword)));

            EmailUtil.sendAccountCreatedEmail(email, fullName);

            response.sendRedirect("login.jsp");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Có lỗi xảy ra, vui lòng thử lại sau.");
            request.getRequestDispatcher("createAccount.jsp").forward(request, response);
        }
    }

    /** Kiểm tra username đã tồn tại trong bảng Users chưa (trước khi tạo mới). */
    private boolean isUsernameTaken(String username) throws ClassNotFoundException {
        String sql = "SELECT 1 FROM Users WHERE username = ?";

        try (Connection con = JDBCUltil.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, username);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // An toàn hơn khi có lỗi: coi như đã tồn tại để chặn insert trùng,
            // thay vì cho qua và có thể gây lỗi DB constraint phía sau.
            return true;
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
