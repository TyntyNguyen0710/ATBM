<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Đặt tour thành công</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f8f9fa; padding: 40px; text-align: center; }
        .success-box {
            max-width: 520px; margin: 0 auto; background: white;
            padding: 40px; border-radius: 16px; box-shadow: 0 4px 20px rgba(0,0,0,0.08);
        }
        h2 { color: #27ae60; margin-bottom: 25px; }
        .info { text-align: left; background: #f8f9fa; padding: 20px; border-radius: 10px; margin: 20px 0; }
        .info p { margin: 8px 0; }
        .signed { color: #e74c3c; font-weight: bold; font-size: 17px; margin: 25px 0; }
        .btn {
            display: inline-block; padding: 12px 35px; background: #3498db; color: white;
            text-decoration: none; border-radius: 8px; font-weight: bold;
        }
    </style>
</head>
<body>

<div class="success-box">
    <h2>🎉 Đặt tour thành công!</h2>

    <div class="info">
        <p><strong>Tour:</strong> ${tour.name}</p>
        <p><strong>Ngày khởi hành:</strong> ${booking.departureDate}</p>
        <p><strong>Số người lớn:</strong> ${booking.noAdults}</p>
        <p><strong>Số trẻ em:</strong> ${booking.noChildren}</p>
    </div>

    <div class="signed">✅ Đã ký chữ ký số thành công</div>

    <a href="trangchu.jsp" class="btn">Quay về trang chủ</a>
</div>

</body>
</html>