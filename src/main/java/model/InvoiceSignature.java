package model;

import java.util.Date;

/**
 * Đại diện cho 1 lần ký số hóa đơn/booking.
 *
 * Lưu ý quan trọng: trường "status" trong DB chỉ là CACHE của lần verify
 * gần nhất — không phải nguồn sự thật. Mỗi lần người dùng xem lại hợp
 * đồng, hệ thống PHẢI verify lại từ đầu (băm dữ liệu hiện tại + so khớp
 * + Signature.verify), không được tin vào giá trị "status" cũ để tránh
 * trường hợp ai đó sửa thẳng cột status trong DB để giả mạo kết quả.
 */
public class InvoiceSignature {

    public static final String STATUS_VALID        = "VALID";
    public static final String STATUS_TAMPERED      = "TAMPERED";
    public static final String STATUS_KEY_REVOKED   = "KEY_REVOKED";

    private int id;
    private long bookingId;
    private String invoiceHash;
    private String signature;
    private int publicKeyId;
    private Date signedAt;
    private String status;
    private Date lastCheckedAt;

    public InvoiceSignature() {
    }

    public InvoiceSignature(int id, long bookingId, String invoiceHash, String signature,
                             int publicKeyId, Date signedAt, String status, Date lastCheckedAt) {
        this.id = id;
        this.bookingId = bookingId;
        this.invoiceHash = invoiceHash;
        this.signature = signature;
        this.publicKeyId = publicKeyId;
        this.signedAt = signedAt;
        this.status = status;
        this.lastCheckedAt = lastCheckedAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public long getBookingId() { return bookingId; }
    public void setBookingId(long bookingId) { this.bookingId = bookingId; }

    public String getInvoiceHash() { return invoiceHash; }
    public void setInvoiceHash(String invoiceHash) { this.invoiceHash = invoiceHash; }

    public String getSignature() { return signature; }
    public void setSignature(String signature) { this.signature = signature; }

    public int getPublicKeyId() { return publicKeyId; }
    public void setPublicKeyId(int publicKeyId) { this.publicKeyId = publicKeyId; }

    public Date getSignedAt() { return signedAt; }
    public void setSignedAt(Date signedAt) { this.signedAt = signedAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getLastCheckedAt() { return lastCheckedAt; }
    public void setLastCheckedAt(Date lastCheckedAt) { this.lastCheckedAt = lastCheckedAt; }
}
