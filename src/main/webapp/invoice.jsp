<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.security.MessageDigest" %>
<%@ page import="java.util.Base64" %>
<%@ page import="model.Booking, model.Customer, model.Tour" %>

<%
    Booking booking = (Booking) request.getAttribute("booking");
    Customer customer = (Customer) request.getAttribute("customer");
    Tour tour = (Tour) request.getAttribute("tour");

    // Tạo nội dung hóa đơn để băm
    StringBuilder sb = new StringBuilder();
    sb.append("Mã Booking: ").append(booking != null ? booking.getId() : "N/A").append("\n");
    sb.append("Khách hàng: ").append(customer.getName()).append("\n");
    sb.append("Email: ").append(customer.getEmail()).append("\n");
    sb.append("Tour: ").append(tour.getName()).append("\n");
    sb.append("Ngày khởi hành: ").append(booking.getDepartureDate()).append("\n");
    sb.append("Số người lớn: ").append(booking.getNoAdults()).append("\n");
    sb.append("Số trẻ em: ").append(booking.getNoChildren()).append("\n");
    sb.append("Giá tour: ").append(tour.getPrice()).append(" VND");

    // Tính SHA-256
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hashBytes = digest.digest(sb.toString().getBytes("UTF-8"));
    String invoiceHash = Base64.getEncoder().encodeToString(hashBytes);

    request.setAttribute("invoiceContent", sb.toString());
    request.setAttribute("invoiceHash", invoiceHash);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hóa đơn & Chữ ký số</title>
    <link rel="stylesheet" href="css/reset.css">
    <style>
        body { font-family: Arial; margin: 40px; background: #f5f5f5; }
        .container { max-width: 850px; margin: auto; background: white; padding: 30px; border-radius: 10px; }
        .hash-box { background: #f1f1f1; padding: 15px; font-family: monospace; word-break: break-all; border: 1px solid #ddd; }
        textarea { width: 100%; height: 160px; font-family: monospace; }
        button { background: #3498db; color: white; padding: 12px 25px; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; }
    </style>
</head>
<body>
<div class="container">
    <h2>HÓA ĐƠN ĐẶT TOUR - CHỮ KÝ SỐ</h2>
    <hr>

    <h3>Thông tin hóa đơn</h3>
    <p><strong>Khách hàng:</strong> ${customer.name}</p>
    <p><strong>Email:</strong> ${customer.email}</p>
    <p><strong>Tour:</strong> ${tour.name}</p>
    <p><strong>Ngày khởi hành:</strong> ${booking.departureDate}</p>
    <p><strong>Số người lớn:</strong> ${booking.noAdults} | <strong>Trẻ em:</strong> ${booking.noChildren}</p>
    <p><strong>Giá tour:</strong> <fmt:formatNumber value="${tour.price}" type="currency" currencyCode="VND"/></p>

    <h3>Nội dung hóa đơn (để băm)</h3>
    <pre style="background:#f8f9fa; padding:15px; white-space: pre-wrap; border:1px solid #ddd;">${invoiceContent}</pre>

    <h3>Băm hóa đơn (SHA-256)</h3>
    <div class="hash-box">${invoiceHash}</div>

    <div style="margin-top: 30px; padding: 20px; background: #fff3cd; border-radius: 8px; border: 1px solid #ffc107;">
        <h3>Ký hóa đơn bằng Private Key</h3>
        <form action="GenerateSignatureServlet" method="post">
            <input type="hidden" name="invoiceHash" value="${invoiceHash}">
            <input type="hidden" name="bookingId" value="${booking.id}">

            <label><strong>Dán Private Key (PEM format):</strong></label><br>
            <textarea name="privateKeyPem" placeholder="-----BEGIN PRIVATE KEY-----&#10;MIIE...&#10;-----END PRIVATE KEY-----" required></textarea>

            <br><br>
            <button type="submit">Ký hóa đơn & Gửi email</button>
        </form>
    </div>
</div>
</body>
</html>