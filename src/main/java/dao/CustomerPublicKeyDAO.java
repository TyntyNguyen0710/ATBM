package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import DataBase.JDBCUltil;
import model.CustomerPublicKey;

/**
 * DAO quản lý CustomerPublicKey.
 *
 * Nguyên tắc thiết kế quan trọng: KHÔNG có method nào update/xóa
 * publicKey cũ. Khi khách "đổi khóa" hoặc "báo mất khóa", ta CHỈ
 * revoke (set revokedAt) dòng cũ rồi insert dòng mới — không bao giờ
 * ghi đè hay xóa, để giữ khả năng verify các chữ ký đã ký bằng khóa cũ.
 */
public class CustomerPublicKeyDAO {

    public static CustomerPublicKeyDAO getInstance() {
        return new CustomerPublicKeyDAO();
    }

    /** Đăng ký 1 public key mới cho khách hàng. */
    public int insert(int customerId, String publicKeyBase64) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO CustomerPublicKey(customerId, publicKey, createdAt) VALUES (?, ?, GETDATE())";

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet keys = null;
        try {
            con = JDBCUltil.getConnection();
            pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setInt(1, customerId);
            pst.setString(2, publicKeyBase64);
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

    /** Lấy public key đang HOẠT ĐỘNG (chưa bị revoke) của 1 khách hàng. */
    public CustomerPublicKey selectActiveByCustomerId(int customerId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT TOP 1 * FROM CustomerPublicKey "
                   + "WHERE customerId = ? AND revokedAt IS NULL "
                   + "ORDER BY createdAt DESC";

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = JDBCUltil.getConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, customerId);
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

    /** Lấy 1 public key theo id — dùng khi verify chữ ký 1 hợp đồng cụ thể. */
    public CustomerPublicKey selectById(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM CustomerPublicKey WHERE id = ?";

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = JDBCUltil.getConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
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
     * Báo mất khóa / thu hồi khóa hiện tại.
     * QUAN TRỌNG: hàm này KHÔNG xóa dòng — chỉ set revokedAt, để các
     * chữ ký cũ (ký trước thời điểm revoke) vẫn còn public key để verify.
     */
    public boolean revoke(int publicKeyId, String reason) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE CustomerPublicKey SET revokedAt = GETDATE(), revokeReason = ? "
                   + "WHERE id = ? AND revokedAt IS NULL";

        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = JDBCUltil.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, reason);
            pst.setInt(2, publicKeyId);
            return pst.executeUpdate() > 0;
        } finally {
            JDBCUltil.closePreparedStatement(pst);
            JDBCUltil.closeConnection(con);
        }
    }

    private CustomerPublicKey mapRow(ResultSet rs) throws SQLException {
        CustomerPublicKey k = new CustomerPublicKey();
        k.setId(rs.getInt("id"));
        k.setCustomerId(rs.getInt("customerId"));
        k.setPublicKey(rs.getString("publicKey"));

        Timestamp created = rs.getTimestamp("createdAt");
        k.setCreatedAt(created);

        Timestamp revoked = rs.getTimestamp("revokedAt");
        k.setRevokedAt(revoked); // null nếu chưa revoke

        k.setRevokeReason(rs.getString("revokeReason"));
        return k;
    }
}
