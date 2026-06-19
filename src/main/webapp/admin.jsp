<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="model.Customer"%>
<%@page import="model.Booking"%>
<%@page import="dao.bookingDAO"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/admin.css">
<title>Admin Panel</title>
</head>
<body>
	<header>
		<div>
			<a href="trangchu.jsp"><img id="logo" src="img/logo.png"
				alt="Logo"></a>
		</div>

		<h1>Admin Panel</h1>

		<div id="logout-container">
			<form action="logout" method="post">
				<button id="logout-btn" type="submit">Đăng xuất</button>
			</form>
		</div>
	</header>

	<div id="container">
		<div id="leftMenu">
			<ul>
				<li><a href="javascript:void(0);"
					onclick="showContent('viewCustomers.jsp')">Xem danh sách khách
						hàng</a></li>
				<li><a href="javascript:void(0);"
					onclick="showContent('viewBookings.jsp')">Xem Danh Sách Đặt
						Tour</a></li>
			</ul>
		</div>

		<div id="content"></div>
	</div>
	<script>
		// Function to handle content loading
		function showContent(contentPage) {
			// Use AJAX or other methods to load content dynamically
			// For simplicity, we will use a basic approach here
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					document.getElementById("content").innerHTML = this.responseText;
				}
			};
			xhttp.open("GET", contentPage, true);
			xhttp.send();
		}
	</script>
</body>

</html>
