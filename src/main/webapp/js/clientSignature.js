/* ================================================================
   FILE: src/main/webapp/js/clientSignature.js
   Mục đích: Tạo cặp khóa RSA + ký số NGAY TRÊN TRÌNH DUYỆT bằng
   Web Crypto API — private key sinh ra chỉ tồn tại trong phiên làm
   việc của trình duyệt (qua CryptoKey không thể export ngược lại
   thành chuỗi nếu extractable=false; ở đây để extractable=true vì
   ứng dụng cần cho phép khách TẢI private key về máy để dùng lại
   lần sau — nhưng việc export chỉ diễn ra cục bộ trong trình duyệt,
   KHÔNG bao giờ gửi qua mạng).

   QUY TRÌNH:
     1) generateKeyPair() — tạo cặp khóa RSA-2048
     2) downloadPrivateKey() — cho khách tải file .pem về máy
        (chỉ 1 lần, ngay lúc tạo — nếu mất phải báo thu hồi & tạo mới)
     3) registerPublicKey() — gửi PUBLIC key (an toàn để gửi) lên
        server lưu vào CustomerPublicKeyDAO
     4) signInvoice() — khi đặt tour xong, lấy invoiceHash server trả
        về, ký bằng private key đang có trong session trình duyệt,
        gửi signatureBase64 lên GenerateSignatureServlet
   ================================================================ */

const ClientSignature = (() => {

    let currentKeyPair = null; // { publicKey, privateKey } - chỉ sống trong bộ nhớ JS

    /** Bước 1: Tạo cặp khóa RSA-2048 dùng cho ký số (RSASSA-PKCS1-v1_5 + SHA-256). */
    async function generateKeyPair() {
        currentKeyPair = await window.crypto.subtle.generateKey(
            {
                name: "RSASSA-PKCS1-v1_5",
                modulusLength: 2048,
                publicExponent: new Uint8Array([1, 0, 1]),
                hash: "SHA-256",
            },
            true, // extractable — cho phép xuất ra để khách tải về / gửi public key
            ["sign", "verify"]
        );
        return currentKeyPair;
    }

    /** Xuất public key ra Base64 (định dạng X.509/SPKI) — an toàn để gửi lên server. */
    async function exportPublicKeyBase64() {
        const exported = await window.crypto.subtle.exportKey("spki", currentKeyPair.publicKey);
        return arrayBufferToBase64(exported);
    }

    /** Xuất private key ra Base64 (định dạng PKCS8) — CHỈ dùng để tải về máy khách, không gửi server. */
    async function exportPrivateKeyBase64() {
        const exported = await window.crypto.subtle.exportKey("pkcs8", currentKeyPair.privateKey);
        return arrayBufferToBase64(exported);
    }

    /** Bước 2: Cho khách tải private key về máy dưới dạng file .pem. */
    async function downloadPrivateKeyAsFile(filename = "private_key.pem") {
        const base64 = await exportPrivateKeyBase64();
        const pem = `-----BEGIN PRIVATE KEY-----\n${chunk(base64, 64)}\n-----END PRIVATE KEY-----`;

        const blob = new Blob([pem], { type: "application/x-pem-file" });
        const url = URL.createObjectURL(blob);
        const a = document.createElement("a");
        a.href = url;
        a.download = filename;
        a.click();
        URL.revokeObjectURL(url);
    }

    /** Nạp lại private key từ file .pem khách đã tải trước đó (khi họ quay lại ký lần sau). */
    async function importPrivateKeyFromPem(pemText) {
        const base64 = pemText
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replace(/\s+/g, "");

        const keyBuffer = base64ToArrayBuffer(base64);

        const privateKey = await window.crypto.subtle.importKey(
            "pkcs8",
            keyBuffer,
            { name: "RSASSA-PKCS1-v1_5", hash: "SHA-256" },
            false, // không cần export lại sau khi import
            ["sign"]
        );

        currentKeyPair = currentKeyPair || {};
        currentKeyPair.privateKey = privateKey;
        return privateKey;
    }

    /** Bước 3: Gửi public key lên server để đăng ký (qua fetch tới Servlet riêng). */
    async function registerPublicKey(customerId, csrfToken) {
        const publicKeyBase64 = await exportPublicKeyBase64();

        const res = await fetch("RegisterPublicKeyServlet", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: new URLSearchParams({
                customerId: customerId,
                publicKeyBase64: publicKeyBase64,
                csrfToken: csrfToken,
            }),
        });

        if (!res.ok) {
            throw new Error("Đăng ký public key thất bại: " + res.status);
        }
        return res.json();
    }

    /**
     * Bước 4: Ký 1 hash hóa đơn bằng private key hiện có trong bộ nhớ trình duyệt.
     * @param {string} invoiceHash - chuỗi hash hex (64 ký tự) server trả về
     * @returns {string} signatureBase64
     */
    async function signInvoiceHash(invoiceHash) {
        if (!currentKeyPair || !currentKeyPair.privateKey) {
            throw new Error("Chưa có private key trong phiên này. Vui lòng nạp lại file private key trước khi ký.");
        }

        const encoder = new TextEncoder();
        const data = encoder.encode(invoiceHash);

        const signatureBuffer = await window.crypto.subtle.sign(
            { name: "RSASSA-PKCS1-v1_5" },
            currentKeyPair.privateKey,
            data
        );

        return arrayBufferToBase64(signatureBuffer);
    }

    /** Gửi chữ ký lên server — KHÔNG gửi private key, chỉ gửi signatureBase64 + bookingId. */
    async function submitSignature(bookingId, signatureBase64, csrfToken) {
        const res = await fetch("GenerateSignatureServlet", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: new URLSearchParams({
                bookingId: bookingId,
                signatureBase64: signatureBase64,
                csrfToken: csrfToken,
            }),
        });
        return res; // caller tự xử lý redirect / đọc response theo nhu cầu UI
    }

    /* ── Helpers ── */
    function arrayBufferToBase64(buffer) {
        const bytes = new Uint8Array(buffer);
        let binary = "";
        for (let i = 0; i < bytes.byteLength; i++) {
            binary += String.fromCharCode(bytes[i]);
        }
        return window.btoa(binary);
    }

    function base64ToArrayBuffer(base64) {
        const binary = window.atob(base64);
        const bytes = new Uint8Array(binary.length);
        for (let i = 0; i < binary.length; i++) {
            bytes[i] = binary.charCodeAt(i);
        }
        return bytes.buffer;
    }

    function chunk(str, size) {
        const lines = [];
        for (let i = 0; i < str.length; i += size) {
            lines.push(str.slice(i, i + size));
        }
        return lines.join("\n");
    }

    return {
        generateKeyPair,
        exportPublicKeyBase64,
        downloadPrivateKeyAsFile,
        importPrivateKeyFromPem,
        registerPublicKey,
        signInvoiceHash,
        submitSignature,
    };
})();
