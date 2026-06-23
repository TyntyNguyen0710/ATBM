<%@page import="model.Customer"%>
<%@page import="dao.customerDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String userRole = (String) session.getAttribute("role");
    if (!"Customer".equals(userRole)) {
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
</head>
<body>

<%
    String username = (String) session.getAttribute("username");
    Customer customer = customerDAO.getIntance().selectByUsername(username);
    session.setAttribute("customer", customer);
%>

<div class="container">
    <header>
        <a href="trangchu.jsp">
            <img class="logo" src="img/logo.png" alt="Logo">
        </a>
        <h2>Thông tin người dùng</h2>
    </header>

    <ul>
        <li><strong>Tên đăng nhập:</strong> <%=username%></li>
        <li><strong>Tên khách hàng:</strong> <%=customer.getName()%></li>
        <li><strong>Email:</strong> <%=customer.getEmail()%></li>
        <li><strong>Số điện thoại:</strong> <%=customer.getPhone()%></li>
        <li><strong>Địa chỉ:</strong> <%=customer.getAddress()%></li>
    </ul>

    <div class="button-group">
        <a class="btn btn-blue" href="changeInformation.jsp">Sửa thông tin</a>
        <a class="btn btn-blue" href="changePasswordAfterLogin.jsp">Đổi mật khẩu</a>
        <a class="btn btn-red" href="KeyManagement">Quản lý Khóa số</a>
    </div>
</div>

</body>
</html>