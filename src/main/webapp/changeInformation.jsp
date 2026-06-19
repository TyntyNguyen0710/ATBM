<%@page import="model.Customer"%>
<%@page import="dao.customerDAO"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/changeInformation.css">
<title>Thay đổi thông tin người dùng</title>
</head>
<body>
	<%
	String username = (String) session.getAttribute("username");
	Customer customer = customerDAO.getIntance().selectByUsername(username);
	%>
	<div class="container">
		<header>
			<a href="trangchu.jsp"> <img class="logo" src="img/logo.png"
				alt="Logo"></a>
			<h2>Thay đổi thông tin người dùng</h2>
		</header>
		<form action="ChangeInformation" method="post">
			<label for="name">Họ và tên:</label> <input type="text" id="name"
				name="name" value="<%=customer.getName()%>"> <label
				for="phone">Số điện thoại:</label> <input type="text" id="phone"
				name="phone" value="<%=customer.getPhone()%>"> <label
				for="address">Địa chỉ:</label> <input type="text" id="address"
				name="address" value="<%=customer.getAddress()%>">

			<!-- Thêm các trường khác nếu cần -->

			<input type="submit" value="Xác nhận">
		</form>
	</div>
</body>
</html>
