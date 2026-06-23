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

    <!-- Public Key đang sử dụng -->
    <h3>Public Key đang sử dụng:</h3>
    <div class="current-key">
        ${activePublicKey}
    </div>

    <!-- Nút Báo mất khóa -->
    <button type="button" class="btn btn-red" onclick="showKeyForm()">
        Báo mất khóa
    </button>

    <!-- Form nhập Public Key mới (ẩn mặc định) -->
    <div id="keyForm" style="display: none; margin-top: 25px;">
        <form action="KeyManagement" method="post">
            <div class="form-group">
                <label><strong>Nhập Public Key mới:</strong></label>
                <textarea name="publicKey" rows="5" placeholder="Dán Public Key mới vào đây..." required></textarea>
            </div>
            <button type="submit" class="btn btn-red">Lưu Public Key Mới</button>
        </form>
    </div>

    <!-- Lịch sử Public Key -->
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