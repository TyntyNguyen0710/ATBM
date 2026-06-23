<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/keyManagement.css">
    <title>Quản lý Khóa số</title>
</head>
<body>

<div class="container">
    <h2>Quản lý Khóa số</h2>

    <c:if test="${not empty message}">
        <div class="message">${message}</div>
    </c:if>
    <div style="margin-top: 30px; text-align: center;">
        <h3>Tải phần mềm hỗ trợ chữ ký số</h3>
        <p style="color: #555; margin-bottom: 15px;">
            Tải phần mềm để tạo cặp khóa.
        </p>

        <a href="DownloadKeyTool" 
        style="display: inline-block; 
                background-color: #27ae60; 
                color: white; 
                padding: 14px 30px; 
                text-decoration: none; 
                border-radius: 8px; 
                font-weight: bold;
                font-size: 16px;">
            Tải phần mềm chữ ký số
        </a>
    </div>
    <h3>Public Key đang sử dụng:</h3>
    <div class="current-key">
        ${activePublicKey}
    </div>

    <button type="button" class="btn btn-red" onclick="showKeyForm()">
        Báo mất khóa
    </button>

    <div id="keyForm" style="display: none; margin-top: 25px;">
        <form action="KeyManagement" method="post">
            <div class="form-group">
                <label><strong>Nhập Public Key mới:</strong></label>
                <textarea name="publicKey" rows="5" placeholder="Dán Public Key mới vào đây..." required></textarea>
            </div>
            <button type="submit" class="btn btn-red">Lưu Public Key Mới</button>
        </form>
    </div>

    <h3 style="margin-top: 40px;">Lịch sử Public Key đã từng sử dụng:</h3>
    <c:forEach var="key" items="${allKeys}">
        <div class="history-item">${key}</div>
    </c:forEach>
</div>

<script>
    function showKeyForm() {
        document.getElementById('keyForm').style.display = 'block';
    }
</script>

</body>
</html>