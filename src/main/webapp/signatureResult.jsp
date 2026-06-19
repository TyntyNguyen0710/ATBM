<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Kết quả chữ ký số / Mã hóa</title>
    <style>
        body { font-family: Arial; margin: 40px; background: #f5f5f5; }
        .result { background: #e8f5e9; padding: 25px; border-radius: 10px; max-width: 900px; margin: auto; }
        pre { 
            background: #f8f9fa; 
            padding: 15px; 
            word-break: break-all; 
            border: 1px solid #ddd; 
            border-radius: 5px;
            font-size: 14px;
        }
        h3 { color: #2c3e50; }
    </style>
</head>
<body>
    <div class="result">
        <h2 style="color: #27ae60;">✅ Chữ ký số / Dữ liệu đã mã hóa thành công!</h2>
        <hr>

        <h3>Hash của hóa đơn (SHA-256):</h3>
        <pre>${invoiceHash}</pre>

        <h3>Dữ liệu đã mã hóa (Hybrid RSA + AES - Tương thích với RSA.java):</h3>
        <pre style="background: #fff3cd; border: 2px solid #f39c12;">${encryptedResult}</pre>

        <p style="margin-top: 20px; color: #555;">
            <strong>Hướng dẫn:</strong> Bạn có thể copy đoạn "Dữ liệu đã mã hóa" ở trên và giải mã bằng class 
            <code>RSA.java</code> của bạn (dùng Public Key tương ứng).
        </p>
    </div>

    <div style="text-align: center; margin-top: 30px;">
        <a href="trangchu.jsp" style="padding: 10px 20px; background: #3498db; color: white; text-decoration: none; border-radius: 5px;">
            Quay về trang chủ
        </a>
    </div>
</body>
</html>