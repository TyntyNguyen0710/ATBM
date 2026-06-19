<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="model.Tour"%>
<%@page import="dao.*"%>
<%@page import="DataBase.*"%>
<%@page import="java.util.ArrayList"%>
<%
// Lấy thông tin về vai trò từ session
String userRole = (String) session.getAttribute("role");
if (!"Customer".equals(userRole)) {
	response.sendRedirect(request.getContextPath() + "/bookingTour.jsp");
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/bookingTourAfterLogin.css">
<title>Booking Tour</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

	<div>
		<c:set var="tourService" value="${tourDAO.getIntance()}" />
		<c:set var="tour"
			value="${tourService.selectByID(sessionScope.tourID)}" />
		<c:set var="customerDAO" value="${customerDAO.getIntance()}" />
		<c:set var="user"
			value="${customerDAO.selectByUsername(sessionScope.username)}" />

		<p>
			Đặt tour: <strong>${tour.name}</strong>
		</p>
		<form action="confirm.jsp" method="post"
			onsubmit="return validateForm()">
			<h2>Thông tin chuyến đi</h2>
			<input type="hidden" name="tourID" value="${sessionScope.tourID}">
			<input type="hidden" name="fullName" value="${user.getName()}">
			<input type="hidden" name="address" value="${user.getAddress()}">
			<input type="hidden" name="email" value="${user.getEmail()}">
			<input type="hidden" name="phone" value="${user.getPhone()}">
			<label for="departureDate">Ngày khởi hành(*):</label> <input
				type="date" id="departureDate" name="departureDate" required><br>
			<label for="adults">Số lượng người lớn:</label> <input type="number"
				id="adults" name="adults" min="0" value="0"><br> <label
				for="childs">Số lượng trẻ em:</label> <input type="number"
				id="childs" name="childs" min="0" value="0"><br> <input
				type="submit" value="Gửi"> <input type="button" value="Hủy"
				onclick="goBack()">
		</form>
	</div>

	<script>
		function validateForm() {
			var departureDate = new Date(document
					.getElementById("departureDate").value);
			var currentDate = new Date();

			// Compare departureDate with currentDate
			if (departureDate <= currentDate) {
				alert("Ngày khởi hành phải lớn hơn ngày hiện tại.");
				return false; // Prevent form submission
			}

			// Validate phone number
			var phoneInput = document.getElementById("phone");
			var phoneValue = phoneInput.value.trim();

			if (!/^\d{10}$/.test(phoneValue)) {
				alert("Điện thoại phải có đúng 10 chữ số.");
				return false; // Prevent form submission
			}

			return true; // Allow form submission
		}

		function goBack() {
			window.location.href = "showTour.jsp?tourId=${tour.id}";
		}
	</script>

</body>
</html>
