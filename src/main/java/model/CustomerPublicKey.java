package model;

import java.util.Date;

/**
 * Đại diện cho 1 public key đã đăng ký của khách hàng.
 * KHÔNG chứa trường nào lưu private key — đúng nguyên tắc: server
 * không bao giờ được giữ private key của khách.
 */
public class CustomerPublicKey {

    private int id;
    private int customerId;
    private String publicKey;   // Base64, định dạng X.509
    private Date createdAt;
    private Date revokedAt;     // null = đang hoạt động
    private String revokeReason;

    public CustomerPublicKey() {
    }

    public CustomerPublicKey(int id, int customerId, String publicKey,
                              Date createdAt, Date revokedAt, String revokeReason) {
        this.id = id;
        this.customerId = customerId;
        this.publicKey = publicKey;
        this.createdAt = createdAt;
        this.revokedAt = revokedAt;
        this.revokeReason = revokeReason;
    }

    public boolean isActive() {
        return revokedAt == null;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getPublicKey() { return publicKey; }
    public void setPublicKey(String publicKey) { this.publicKey = publicKey; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getRevokedAt() { return revokedAt; }
    public void setRevokedAt(Date revokedAt) { this.revokedAt = revokedAt; }

    public String getRevokeReason() { return revokeReason; }
    public void setRevokeReason(String revokeReason) { this.revokeReason = revokeReason; }
}
