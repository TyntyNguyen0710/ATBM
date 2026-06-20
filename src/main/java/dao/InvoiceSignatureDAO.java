package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import DataBase.JDBCUltil;
import model.InvoiceSignature;

/**
 * DAO quản lý InvoiceSignature.
 *
 * Lưu ý: cố ý KHÔNG có method "update signature/hash" — một chữ ký đã
 * lưu thì không bao giờ được sửa nội dung hash/signature, vì làm vậy
 * sẽ vô hiệu hóa hoàn toàn mục đích chống giả mạo. Nếu hóa đơn cần
 * thay đổi (giá, ngày khởi hành...), nghiệp vụ đúng là tạo BẢN GHI KÝ
 * MỚI (khách ký lại), giữ nguyên các bản ghi cũ làm lịch sử.
 */
public class InvoiceSignatureDAO {

    public static InvoiceSignatureDAO getInstance() {
        return new InvoiceSignatureDAO();
    }

    /** Lưu 1 chữ ký mới — gọi ngay sau khi xác thực ký thành công ở GenerateSignatureServlet. */
    public int insert(InvoiceSignature sig) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO InvoiceSignature "
                   + "(bookingId, invoiceHash, signature, publicKeyId, signedAt, status, lastCheckedAt) "
                   + "VALUES (?, ?, ?, ?, GETDATE(), ?, GETDATE())";

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet keys = null;
        try {
            con = JDBCUltil.getConnection();
            pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setLong(1, sig.getBookingId());
            pst.setString(2, sig.getInvoiceHash());
            pst.setString(3, sig.getSignature());
            pst.setInt(4, sig.getPublicKeyId());
            pst.setString(5, InvoiceSignature.STATUS_VALID);
            pst.executeUpdate();

            keys = pst.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
            return -1;
        } finally {
            if (keys != null) keys.close();
            JDBCUltil.closePreparedStatement(pst);
            JDBCUltil.closeConnection(con);
        }
    }

    /** Lấy chữ ký GẦN NHẤT của 1 booking (1 booking có thể có nhiều lần ký nếu sửa/ký lại). */
    public InvoiceSignature selectLatestByBookingId(long bookingId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT TOP 1 * FROM InvoiceSignature WHERE bookingId = ? ORDER BY signedAt DESC";

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = JDBCUltil.getConnection();
            pst = con.prepareStatement(sql);
            pst.setLong(1, bookingId);
            rs = pst.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            JDBCUltil.closePreparedStatement(pst);
            JDBCUltil.closeConnection(con);
        }
    }

    /**
     * Cập nhật cache "status" + "lastCheckedAt" sau mỗi lần verify.
     * KHÔNG bao giờ đụng tới invoiceHash/signature — chỉ ghi lại kết quả
     * kiểm tra gần nhất để admin tra cứu nhanh / phục vụ audit log.
     */
    public boolean updateStatusAfterCheck(int signatureId, String newStatus) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE InvoiceSignature SET status = ?, lastCheckedAt = GETDATE() WHERE id = ?";

        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = JDBCUltil.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, newStatus);
            pst.setInt(2, signatureId);
            return pst.executeUpdate() > 0;
        } finally {
            JDBCUltil.closePreparedStatement(pst);
            JDBCUltil.closeConnection(con);
        }
    }

    private InvoiceSignature mapRow(ResultSet rs) throws SQLException {
        InvoiceSignature s = new InvoiceSignature();
        s.setId(rs.getInt("id"));
        s.setBookingId(rs.getLong("bookingId"));
        s.setInvoiceHash(rs.getString("invoiceHash"));
        s.setSignature(rs.getString("signature"));
        s.setPublicKeyId(rs.getInt("publicKeyId"));

        Timestamp signedAt = rs.getTimestamp("signedAt");
        s.setSignedAt(signedAt);

        s.setStatus(rs.getString("status"));

        Timestamp lastChecked = rs.getTimestamp("lastCheckedAt");
        s.setLastCheckedAt(lastChecked);

        return s;
    }
}
