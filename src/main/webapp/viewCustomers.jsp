<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="model.Customer"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="dao.customerDAO"%>
<%
// Lấy thông tin về vai trò từ session
String userRole = (String) session.getAttribute("role");

// Kiểm tra xem vai trò có phải là "admin" không
if (!"Admin".equals(userRole)) {
	// Nếu không phải là "admin", chuyển hướng về trang trangchu.jsp hoặc trang không có quyền
	response.sendRedirect(request.getContextPath() + "/trangchu.jsp");
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/viewCustomers.css">
<title>Tất cả khách hàng</title>
</head>
<body>
	<%
	customerDAO cd = customerDAO.getIntance();
	ArrayList<Customer> customers = cd.selectAll();
	HttpSession Mysession = request.getSession();
	Mysession.setAttribute("customers", customers);
	%>
	<header>
		<h1>Danh Sách khách hàng</h1>
	</header>

	<div id="content">
		<table>
			<tr>
				<th>Tên khách hàng</th>
				<th>Email</th>
				<th>Địa chỉ</th>
				<th>Số điện thoại</th>
			</tr>

			<c:forEach var="customer" items="${customers}">
				<tr>
					<td>${customer.name}</td>
					<td>${customer.email}</td>
					<td>${customer.address}</td>
					<td>${customer.phone}</td>
				</tr>
			</c:forEach>
		</table>
	</div>

</body>
</html>