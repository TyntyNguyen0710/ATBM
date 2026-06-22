<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/confirm.css">
    <title>Xác nhận đặt tour</title>
</head>
<body>

    <h2>Xác nhận thông tin đặt tour</h2>

    <form action="SaveBooking" method="post">
        <!-- Hidden fields -->
        <input type="hidden" name="tourID" value="${sessionScope.tourID}">
        <input type="hidden" name="fullName" value="${param.fullName}">
        <input type="hidden" name="address" value="${param.address}">
        <input type="hidden" name="email" value="${param.email}">
        <input type="hidden" name="phone" value="${param.phone}">
        <input type="hidden" name="departureDate" value="${param.departureDate}">
        <input type="hidden" name="adults" value="${param.adults}">
        <input type="hidden" name="childs" value="${param.childs}">

        <div class="info-container">
            <p class="info-text"><strong>Họ và tên:</strong> ${param.fullName}</p>
            <p class="info-text"><strong>Địa chỉ:</strong> ${param.address}</p>
            <p class="info-text"><strong>Email:</strong> ${param.email}</p>
            <p class="info-text"><strong>Số điện thoại:</strong> ${param.phone}</p>
            <p class="info-text"><strong>Ngày khởi hành:</strong> ${param.departureDate}</p>
            <p class="info-text"><strong>Số người lớn:</strong> ${param.adults}</p>
            <p class="info-text"><strong>Số trẻ em:</strong> ${param.childs}</p>
        </div>

        <br>
        <input type="submit" value="Xác nhận đặt tour">
        <input type="button" value="Hủy" 
               onclick="location.href='showTour.jsp?tourId=${sessionScope.tourID}'">
    </form>

</body>
</html>