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

    // Tính hash mới (đảm bảo tính duy nhất)
    StringBuilder sb = new StringBuilder();
    sb.append("BookingID:").append(booking != null ? booking.getId() : "N/A").append("|");
    sb.append("Customer:").append(customer.getName()).append("-").append(customer.getEmail()).append("|");
    sb.append("Tour:").append(tour.getName()).append("|");
    sb.append("Date:").append(booking.getDepartureDate()).append("|");
    sb.append("People:").append(booking.getNoAdults()).append("-").append(booking.getNoChildren()).append("|");
    sb.append("Price:").append(tour.getPrice());

    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hashBytes = digest.digest(sb.toString().getBytes("UTF-8"));
    String invoiceHash = Base64.getEncoder().encodeToString(hashBytes);

    request.setAttribute("invoiceHash", invoiceHash);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hóa đơn & Chữ ký số</title>
    <link rel="stylesheet" href="css/reset.css">
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background: #f5f5f5; }
        .container { max-width: 820px; margin: auto; background: white; padding: 30px; border-radius: 12px; box-shadow: 0 2px 12px rgba(0,0,0,0.1); }
        .section { margin-bottom: 25px; }
        .info-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 12px 30px; }
        .info-item { margin-bottom: 8px; }
        .info-label { font-weight: bold; color: #555; }
        textarea { width: 100%; padding: 12px; font-family: monospace; border: 1px solid #ccc; border-radius: 6px; }
        button { padding: 12px 30px; border: none; border-radius: 6px; color: white; font-weight: bold; cursor: pointer; }
        .error-box { background: #f8d7da; color: #721c24; padding: 10px 15px; border-radius: 6px; margin-bottom: 15px; border: 1px solid #f5c6cb; font-weight: bold; }
        .public-key-box { background: #e8f5e9; padding: 12px; border-radius: 6px; word-break: break-all; font-family: monospace; border: 1px solid #27ae60; margin-bottom: 20px; min-height: 50px; }
    </style>
</head>
<body>

<div class="container">
    <h2 style="text-align:center; margin-bottom: 25px;">HÓA ĐƠN ĐẶT TOUR - CHỮ KÝ SỐ</h2>

    <!-- Thông tin hóa đơn -->
    <div class="section">
        <h3>Thông tin hóa đơn</h3>
        <div class="info-grid">
            <div class="info-item"><span class="info-label">Mã Booking:</span> ${booking.id}</div>
            <div class="info-item"><span class="info-label">Khách hàng:</span> ${customer.name}</div>
            <div class="info-item"><span class="info-label">Email:</span> ${customer.email}</div>
            <div class="info-item"><span class="info-label">Tour:</span> ${tour.name}</div>
            <div class="info-item"><span class="info-label">Ngày khởi hành:</span> ${booking.departureDate}</div>
            <div class="info-item"><span class="info-label">Số người:</span> ${booking.noAdults} người lớn, ${booking.noChildren} trẻ em</div>
            <div class="info-item"><span class="info-label">Giá tour:</span> 
                <fmt:formatNumber value="${tour.price}" type="number" groupingUsed="true"/> VND
            </div>
        </div>
    </div>

    <!-- PHẦN CHỮ KÝ SỐ -->
    <div style="margin-top: 30px; padding: 20px; background: #fff3cd; border-radius: 10px; border: 1px solid #ffc107;">

        <h3>Chữ ký số</h3>

        <!-- Thông báo lỗi -->
        <c:if test="${not empty signatureError}">
            <div class="error-box">${signatureError}</div>
        </c:if>

        <c:if test="${not empty activePublicKey}">
            <label><strong>Public Key của bạn:</strong></label>
            <div class="public-key-box">${activePublicKey}</div>
        </c:if>

        <!-- Form ký -->
        <form action="GenerateSignatureServlet" method="post">
            <input type="hidden" name="invoiceHash" value="${invoiceHash}">
            <input type="hidden" name="bookingId" value="${booking.id}">

            <label><strong>Private Key:</strong></label><br>
            <textarea name="privateKeyPem" rows="6" placeholder="Private Key" required></textarea>

            <br><br>
            <button type="submit" style="background: #e74c3c;">Ký hóa đơn & Gửi email</button>
        </form>
    </div>

</div>

</body>
</html>