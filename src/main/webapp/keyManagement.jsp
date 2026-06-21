<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quản lý Khóa số</title>
</head>
<body>
    <h2>Quản lý Khóa số (Digital Signature)</h2>

    <c:if test="${not empty message}">
        <p style="color: green;">${message}</p>
    </c:if>

    <h3>Public Key hiện tại của bạn:</h3>
    <pre style="background:#f0f0f0; padding:15px; word-break:break-all;">${publicKey}</pre>

    <form action="KeyManagement" method="post">
        <input type="hidden" name="action" value="generateNewKey">
        <button type="submit" style="background:red; color:white; padding:10px 20px;">
            Báo mất khóa - Tạo cặp khóa mới
        </button>
    </form>

    <c:if test="${not empty newPrivateKey}">
        <h3 style="color:red;">⚠️ Private Key MỚI (Chỉ hiển thị 1 lần - Hãy lưu ngay!)</h3>
        <pre style="background:#fff3cd; padding:15px; word-break:break-all; border:2px solid red;">${newPrivateKey}</pre>
        
        <h3>Public Key mới:</h3>
        <pre>${newPublicKey}</pre>
    </c:if>
</body>
</html>