package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

import model.Booking;
import model.Customer;
import model.Tour;

/**
 * Sinh ra "chuỗi đại diện" (canonical string) cho 1 hóa đơn booking, rồi
 * băm SHA-256 ra hash.
 *
 * ĐÂY LÀ PHẦN QUAN TRỌNG NHẤT CỦA CƠ CHẾ VERIFY-KHI-F5:
 *
 *   - Lúc KÝ (GenerateSignatureServlet) gọi buildCanonicalInvoice() để
 *     lấy chuỗi, băm ra hash, rồi ký lên hash đó.
 *   - Lúc XEM LẠI / F5 (VerifySignatureServlet) gọi LẠI ĐÚNG HÀM NÀY
 *     với dữ liệu đang có trong DB hiện tại, ra hash mới.
 *   - Nếu admin/nhân viên sửa bất kỳ trường nào nằm trong chuỗi này
 *     (giá tour, ngày khởi hành, số lượng khách...), hash mới sẽ KHÁC
 *     hash cũ đã lưu => verify thất bại => báo "Dữ liệu đã bị thay đổi".
 *
 * QUY TẮC BẮT BUỘC khi sửa hàm này:
 *   1) Định dạng số/ngày tháng phải CỐ ĐỊNH, không phụ thuộc Locale của
 *      server (vì Locale có thể khác nhau giữa các lần chạy) — luôn ép
 *      kiểu, dùng SimpleDateFormat với Locale cố định.
 *   2) Thứ tự các trường trong chuỗi PHẢI giữ nguyên, không bao giờ đổi
 *      thứ tự hay thêm bớt field cho dữ liệu cũ — nếu cần thêm field
 *      mới, chỉ áp dụng cho các booking ký SAU thời điểm thay đổi
 *      (tăng version, xem ghi chú "INVOICE_FORMAT_VERSION" bên dưới).
 *   3. Dùng separator rõ ràng ("|") giữa các field để tránh trường hợp
 *      ghép 2 field liền nhau gây nhầm lẫn (vd "12" + "3" vs "1" + "23").
 */
public final class InvoiceHashUtil {

    /**
     * Đánh version cho định dạng chuỗi hóa đơn. Nếu sau này cần thêm
     * trường mới vào hash, tăng version lên và xử lý rẽ nhánh theo
     * version đã lưu cùng InvoiceSignature (cần thêm cột formatVersion
     * nếu áp dụng) — KHÔNG sửa trực tiếp logic cũ vì sẽ làm sai lệch
     * toàn bộ chữ ký đã ký trước đó.
     */
    public static final int INVOICE_FORMAT_VERSION = 1;

    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd");

    private InvoiceHashUtil() {
    }

    /**
     * Tạo chuỗi đại diện (canonical string) cho hóa đơn từ Booking + Tour
     * + Customer hiện tại. Đây là dữ liệu "nguồn sự thật" — không lấy
     * dữ liệu từ request/form gửi lên, mà luôn LẤY TRỰC TIẾP TỪ DATABASE
     * để đảm bảo verify dựa trên dữ liệu thật, không phải dữ liệu người
     * dùng tự khai.
     */
    public static String buildCanonicalInvoice(Booking booking, Tour tour, Customer customer) {
        StringBuilder sb = new StringBuilder();
        sb.append("v").append(INVOICE_FORMAT_VERSION).append('|');
        sb.append("bookingId=").append(booking.getId()).append('|');
        sb.append("customerId=").append(booking.getCustomerID()).append('|');
        sb.append("customerEmail=").append(safe(customer.getEmail())).append('|');
        sb.append("tourId=").append(booking.getTourID()).append('|');
        sb.append("tourName=").append(safe(tour.getName())).append('|');
        sb.append("tourPrice=").append(formatPrice(tour.getPrice())).append('|');
        sb.append("departureDate=").append(formatDate(booking.getDepartureDate())).append('|');
        sb.append("noAdults=").append(booking.getNoAdults()).append('|');
        sb.append("noChildren=").append(booking.getNoChildren());
        return sb.toString();
    }

    /** Băm SHA-256, trả về dạng hex thường (64 ký tự) — dễ lưu / so sánh / debug hơn Base64. */
    public static String sha256Hex(String canonicalText) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(canonicalText.getBytes(StandardCharsets.UTF_8));

            StringBuilder hex = new StringBuilder(hashBytes.length * 2);
            for (byte b : hashBytes) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            // SHA-256 luôn có sẵn trong JDK chuẩn — không thể xảy ra thực tế.
            throw new IllegalStateException("Thuật toán SHA-256 không khả dụng", e);
        }
    }

    /** Tiện ích gọi 1 bước: từ dữ liệu -> ra thẳng hash hex. */
    public static String hashInvoice(Booking booking, Tour tour, Customer customer) {
        return sha256Hex(buildCanonicalInvoice(booking, tour, customer));
    }

    private static String safe(String value) {
        return (value == null) ? "" : value.trim();
    }

    private static String formatPrice(float price) {
        // Ép về long phần nguyên + giữ 2 chữ số thập phân để tránh sai số float
        // gây ra hash khác nhau giữa các lần build trên cùng 1 giá trị logic.
        return String.format(java.util.Locale.ROOT, "%.2f", price);
    }

    private static synchronized String formatDate(java.util.Date date) {
        if (date == null) return "null";
        return DATE_FORMAT.format(date);
    }
}
