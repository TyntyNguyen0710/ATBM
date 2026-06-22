package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataBase.JDBCUltil;

/**
 * Công cụ migrate 1 LẦN DUY NHẤT: quét toàn bộ bảng Users, với những
 * dòng password còn ở dạng PLAIN TEXT (chưa phải BCrypt hash) thì băm
 * lại và UPDATE vào DB.
 *
 * CÁCH CHẠY:
 *   Đây là 1 class có hàm main() độc lập, chạy 1 lần từ command line
 *   sau khi đã set biến môi trường DB_URL/DB_USER/DB_PASSWORD, KHÔNG
 *   chạy qua web (không có URL servlet nào trigger việc này) — tránh
 *   rủi ro chạy migrate nhiều lần ngoài ý muốn qua HTTP request.
 *
 *   Trong terminal (đã set $env:DB_URL, DB_USER, DB_PASSWORD):
 *     mvn compile exec:java -Dexec.mainClass="util.PasswordMigrationUtil"
 *
 *   Hoặc chạy trực tiếp file .class đã compile bằng IDE (Run As Java Application).
 *
 * AN TOÀN: chỉ update các dòng CHƯA phải hash (PasswordUtil.isBcryptHash
 * == false) — chạy lại nhiều lần cũng không hash chồng hash.
 */
public final class PasswordMigrationUtil {

    private PasswordMigrationUtil() {
    }

    public static void main(String[] args) {
        System.out.println("=== Bắt đầu migrate mật khẩu sang BCrypt ===");

        List<String[]> plainTextUsers = findPlainTextPasswords();
        System.out.println("Tìm thấy " + plainTextUsers.size() + " tài khoản còn mật khẩu dạng plain text.");

        int success = 0;
        for (String[] row : plainTextUsers) {
            String username = row[0];
            String plainPassword = row[1];

            try {
                String newHash = PasswordUtil.hashPassword(plainPassword);
                boolean updated = updatePasswordHash(username, newHash);
                if (updated) {
                    success++;
                    System.out.println("  -> Đã hash mật khẩu cho user: " + username);
                }
            } catch (Exception e) {
                System.err.println("  -> Lỗi khi xử lý user " + username + ": " + e.getMessage());
            }
        }

        System.out.println("=== Hoàn tất: " + success + "/" + plainTextUsers.size() + " tài khoản đã được hash ===");
    }

    private static List<String[]> findPlainTextPasswords() {
        List<String[]> result = new ArrayList<>();
        String sql = "SELECT username, password FROM Users";

        try (Connection con = JDBCUltil.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");

                if (!PasswordUtil.isBcryptHash(password)) {
                    result.add(new String[]{username, password});
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Lỗi khi đọc bảng Users: " + e.getMessage());
        }

        return result;
    }

    private static boolean updatePasswordHash(String username, String newHash) {
        String sql = "UPDATE Users SET password = ? WHERE username = ?";

        try (Connection con = JDBCUltil.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, newHash);
            pst.setString(2, username);
            return pst.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Lỗi khi update user " + username + ": " + e.getMessage());
            return false;
        }
    }
}
