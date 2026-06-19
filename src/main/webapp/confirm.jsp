<%@page import="model.Customer"%>
<%@page import="model.Booking"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import=" model.Tour"%>
<%@page import=" dao.*"%>
<%@page import=" DataBase.*"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/confirm.css">
<title>Confirm</title>
</head>
<body>
	<h2>Xác nhận đặt tour</h2>

	<div>
		<form action="SaveBooking" method="post">
			<!-- Existing form inputs -->
			<input type="hidden" name="tourID" value="${sessionScope.tourID}">
			<input type="hidden" name="fullName" value="${param.fullName}">
			<input type="hidden" name="address" value="${param.address}">
			<input type="hidden" name="email" value="${param.email}"> <input
				type="hidden" name="phone" value="${param.phone}"> <input
				type="hidden" name="departureDate" value="${param.departureDate}">
			<input type="hidden" name="adults" value="${param.adults}"> <input
				type="hidden" name="childs" value="${param.childs}">

			<p>Thông tin đặt tour:</p>
			<!-- Displaying the hidden input values -->
			<p>
				<strong>Họ và tên: </strong>${param.fullName}</p>
			<p>
				<strong>Địa chỉ: </strong> ${param.address}
			</p>
			<p>
				<strong>E-mail: </strong> ${param.email}
			</p>
			<p>
				<strong>Điện thoại: </strong> ${param.phone}
			</p>
			<p>
				<strong>Ngày khởi hành: </strong>${param.departureDate}</p>
			<p>
				<strong>Số người lớn: </strong> ${param.adults}
			</p>
			<p>
				<strong>Số trẻ em: </strong>${param.childs}</p>

			<input type="submit" value="Xác nhận"
				onclick="location.href='complete.jsp'"> 
			<input type="button" value="Hủy" onclick="location.href='showTour.jsp?tourId=${tour.id}'">
		</form>
	</div>
</body>
</html>
