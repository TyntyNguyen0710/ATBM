<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="DataBase.JDBCUltil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="model.Booking"%>
<%@ page import="dao.bookingDAO"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.Locale"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String userRole = (String) session.getAttribute("role");
if (!"Admin".equals(userRole)) {
	response.sendRedirect(request.getContextPath() + "/trangchu.jsp");
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Danh sách đặt Tours</title>
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/viewBookings.css">
</head>
<body>
	<c:set var="bookings" value="<%=bookingDAO.getIntance().selectAll()%>"
		scope="session" />

	<header>
		<h1>Danh sách đặt Tour</h1>
	</header>

	<div id="content">
		<table>
			<tr>
				<th>ID</th>
				<th>Ngày đi</th>
				<th>Số lượng người lớn</th>
				<th>Số lượng trẻ em</th>
				<th>Email</th>
				<th>Tour ID</th>
				<th>Xóa</th>
			</tr>

			<c:forEach var="booking" items="${bookings}">
				<tr>
					<td>${booking.id}</td>
					<td><fmt:formatDate value="${booking.departureDate}"
							pattern="dd/MM/yyyy" /></td>
					<td>${booking.noAdults}</td>
					<td>${booking.noChildren}</td>
					<td>${booking.email}</td>
					<td>${booking.tourID}</td>
					<td>
						<form action="DeleteBookingServlet" method="post">
							<input type="hidden" name="bookingId" value="${booking.id}">
							<input type="hidden" name="email" value="${booking.email}">

							<input type="submit" value="Xóa">
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>
