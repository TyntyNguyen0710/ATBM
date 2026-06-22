package util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Quản lý hash mật khẩu bằng BCrypt.
 *
 * TẠI SAO DÙNG BCrypt THAY VÌ MD5/SHA256 THƯỜNG:
 *  1) BCrypt tự sinh "salt" ngẫu nhiên cho MỖI mật khẩu, nên 2 user
 *     dùng chung 1 mật khẩu "123456" sẽ ra 2 chuỗi hash HOÀN TOÀN
 *     KHÁC NHAU trong DB — chống tấn công kiểu "rainbow table"
 *     (bảng tra cứu sẵn hash -> mật khẩu thường gặp).
 *  2) BCrypt có "work factor" (độ khó tính toán) có thể tăng dần theo
 *     thời gian khi phần cứng máy tính mạnh hơn, làm chậm tấn công
 *     brute-force / dò mật khẩu hàng loạt.
 *  3) MD5/SHA256 thường KHÔNG có salt tự động, băm rất nhanh -> dễ bị
 *     brute-force hoặc tra rainbow table có sẵn trên mạng.
 *
 * LƯU Ý KHI DÙNG:
 *  - Cột "password" trong bảng Users cần đổi kiểu đủ dài để chứa hash
 *    (BCrypt hash dài 60 ký tự) -> NVARCHAR(255) hiện tại của project
 *    là ĐỦ, không cần đổi schema.
 *  - KHÔNG BAO GIỜ implement hàm "decrypt" / "giải mã" ngược mật khẩu
 *    — về bản chất hash không thể đảo ngược, nếu cần "quên mật khẩu"
 *    thì luồng đúng là tạo mật khẩu MỚI (qua OTP email), không phải
 *    khôi phục mật khẩu cũ.
 */
public final class PasswordUtil {

    /** Work factor — số vòng lặp băm (2^cost). 12 là mức cân bằng tốt giữa an toàn và tốc độ năm 2026. */
    private static final int BCRYPT_COST = 12;

    private PasswordUtil() {
    }

    /** Băm mật khẩu thô (plain text) thành chuỗi hash để lưu vào DB. */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được rỗng");
        }
        String salt = BCrypt.gensalt(BCRYPT_COST);
        return BCrypt.hashpw(plainPassword, salt);
    }

    /**
     * So khớp mật khẩu người dùng vừa nhập (lúc đăng nhập) với hash đã lưu trong DB.
     * Đây là cách DUY NHẤT để "xác thực" mật khẩu — không có hàm decrypt.
     */
    public static boolean verifyPassword(String plainPassword, String storedHash) {
        if (plainPassword == null || storedHash == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(plainPassword, storedHash);
        } catch (IllegalArgumentException e) {
            // storedHash không đúng định dạng BCrypt (vd: dữ liệu cũ còn plain text)
            return false;
        }
    }

    /**
     * Kiểm tra nhanh xem 1 chuỗi trong DB đã là BCrypt hash hay vẫn còn
     * plain text (dữ liệu cũ trước khi áp dụng hash) — hữu ích cho việc
     * migrate dữ liệu cũ, xem migrateOldPlainTextPasswords() ở DAO.
     */
    public static boolean isBcryptHash(String value) {
        return value != null && (value.startsWith("$2a$") || value.startsWith("$2b$") || value.startsWith("$2y$"));
    }
}
