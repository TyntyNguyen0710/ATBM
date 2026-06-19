<%@page import="model.Customer"%>
<%@page import="dao.customerDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
// Lấy thông tin về vai trò từ session
String userRole = (String) session.getAttribute("role");

// Kiểm tra xem vai trò có phải là "admin" không
if (!"Customer".equals(userRole)) {
	// Nếu không phải là "admin", chuyển hướng về trang trangchu.jsp hoặc trang không có quyền
	response.sendRedirect(request.getContextPath() + "/trangchu.jsp");
}
%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/information.css">
<title>User Information</title>
<!-- Thêm các phần khác của head nếu cần -->
</head>
<body>

	<%-- Lấy thông tin người dùng từ session hoặc database --%>
	<%-- Ví dụ: --%>
	<%
	// Lấy thông tin người dùng từ session (giả sử đã lưu thông tin người dùng vào session)
	String username = (String) session.getAttribute("username");
	Customer customer = customerDAO.getIntance().selectByUsername(username);
	session.setAttribute("customer", customer);
	%>

	<div class="container">
		<header>
			<a href="trangchu.jsp"> <img class="logo" src="img/logo.png"
				alt="Logo"></a>
			<h2>Thông tin người dùng</h2>
		</header>
		<ul>
			<li><strong>Tên đăng nhập:</strong> <%=username%></li>
			<li><strong>Tên khách hàng:</strong> <%=customer.getName()%></li>
			<li><strong>Email:</strong> <%=customer.getEmail()%></li>
			<li><strong>Số điện thoại:</strong> <%=customer.getPhone()%></li>
			<li><strong>Địa chỉ:</strong> <%=customer.getAddress()%></li>
		</ul>
		<a class="suaThongTin" href="changeInformation.jsp">Sửa thông tin</a>
		<a class="doiMK" href="changePasswordAfterLogin.jsp">Đổi mật khẩu</a>
		<!-- Additional content here -->
	</div>
</body>
</html>
