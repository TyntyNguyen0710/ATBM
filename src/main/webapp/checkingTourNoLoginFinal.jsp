<%@page import="java.util.List"%>
<%@page import="model.Booking"%>
<%@page import="model.Customer"%>
<%@page import="dao.customerDAO"%>
<%@page import="dao.bookingDAO"%>
<%@page import="dao.tourDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/checkingTourNoLoginFinal.css">
<title>Thông tin Booking</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<header>
	<div>
		<a href="trangchu.jsp"> <img id="logo" src="img/logo.png"
			alt="Logo">
		</a>
	</div>
</header>
<body>


	<div class="container">
		<div class="card">
			<div class="card-header">Thông tin Booking</div>
			<div class="card-body">

				<%-- Lấy email từ session --%>

				<%
				String email = (String) session.getAttribute("email");
				System.out.println("Email: " + email);

				Customer customer = customerDAO.getIntance().selectByEmail(email);
				System.out.println("Customer: " + customer);

				List<Booking> bookings = bookingDAO.getIntance().selectBookingsByEmail(email);
				System.out.println("Bookings: " + bookings);
				%>
				<%
				if (!bookings.isEmpty()) {
				%>


				<%
				for (Booking booking : bookings) {
				%>
				<div class="info-label">Tên khách hàng:</div>
				<div class="info-value"><%=customer.getName()%></div>
				<div class="card">
					<div class="card-body">
						<div class="info-label">Tên tour:</div>
						<div class="info-value"><%=tourDAO.getIntance().selectByID(String.valueOf(booking.getTourID())).getName()%></div>

						<div class="info-label">Ngày đi:</div>
						<div class="info-value"><%=booking.getDepartureDate()%></div>

						<div class="info-label">Số lượng người lớn:</div>
						<div class="info-value"><%=booking.getNoAdults()%></div>

						<div class="info-label">Số lượng trẻ em:</div>
						<div class="info-value"><%=booking.getNoChildren()%></div>

						<div class="info-label">Tour ID:</div>
						<div class="info-value"><%=booking.getTourID()%></div>
					</div>
				</div>
				<%
				}
				%>
				<div class="cancel-info">Nếu bạn muốn hủy tour, hãy liên hệ
					vào hotline 0366234053 để được hỗ trợ</div>
				<%
				} else {
				%>
				<div class="no-booking-message">
					Không có thông tin Booking cho khách hàng có email:
					<%=email%></div>
				<%
				}
				%>

			</div>
		</div>
	</div>


</body>
</html>