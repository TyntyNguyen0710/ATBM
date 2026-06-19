<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Kết quả chữ ký số</title>
    <style>
        body { font-family: Arial; margin: 40px; }
        .result { background: #e8f5e9; padding: 20px; border-radius: 8px; }
        pre { background: #f5f5f5; padding: 15px; word-break: break-all; }
    </style>
</head>
<body>
    <h2>Chữ ký số đã được tạo thành công!</h2>

    <div class="result">
        <h3>Hash của hóa đơn:</h3>
        <pre>${invoiceHash}</pre>
        <h3>Public Key (dùng để xác minh chữ ký):</h3>
        <pre style="word-break: break-all; background: #f0f0f0; padding: 15px;">${publicKeyBase64}</pre>
        <h3>Chữ ký số (Base64 - SHA256withRSA):</h3>
        <pre>${digitalSignature}</pre>
    </div>

    <p><strong>Hướng dẫn:</strong> Hãy lưu lại chữ ký số này cùng với hóa đơn để có thể xác minh sau này.</p>

    <a href="trangchu.jsp">Quay về trang chủ</a>
</body>
</html>