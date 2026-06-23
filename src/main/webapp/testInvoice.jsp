<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.bookingDAO" %>
<%@ page import="dao.customerDAO" %>
<%@ page import="dao.tourDAO" %>
<%@ page import="model.Booking" %>
<%@ page import="model.Customer" %>
<%@ page import="model.Tour" %>
<%@ page import="java.security.MessageDigest" %>
<%@ page import="java.util.Base64" %>

<%
    String bookingIdStr = request.getParameter("bookingId");
    String status = "";
    Booking booking = null;
    Customer customer = null;
    Tour tour = null;
    String invoiceHash = "";
    String signatureStatus = "";

    if (bookingIdStr != null && !bookingIdStr.isEmpty()) {
        try {
            long bookingId = Long.parseLong(bookingIdStr);
            booking = bookingDAO.getIntance().selectByBookingId(bookingId);

            if (booking != null) {
                customer = customerDAO.getIntance().selectByIDCustomer(booking.getCustomerID());
                tour = tourDAO.getIntance().selectByID(String.valueOf(booking.getTourID()));

                // Tính hash hóa đơn
                if (customer != null && tour != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("BookingID:").append(booking.getId()).append("|");
                    sb.append("Customer:").append(customer.getName()).append("-").append(customer.getEmail()).append("|");
                    sb.append("Tour:").append(tour.getName()).append("|");
                    sb.append("Date:").append(booking.getDepartureDate()).append("|");
                    sb.append("People:").append(booking.getNoAdults()).append("-").append(booking.getNoChildren()).append("|");
                    sb.append("Price:").append(tour.getPrice());

                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                    byte[] hashBytes = digest.digest(sb.toString().getBytes("UTF-8"));
                    invoiceHash = Base64.getEncoder().encodeToString(hashBytes);

                    // Lấy trạng thái chữ ký
                    signatureStatus = bookingDAO.getIntance().getSignatureVerificationStatus(bookingId);
                }
            }
        } catch (Exception e) {
            status = "Lỗi: " + e.getMessage();
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Test Hóa đơn & Chữ ký số</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background: #f5f5f5; }
        .container { max-width: 900px; margin: auto; background: white; padding: 30px; border-radius: 12px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h2 { color: #2c3e50; }
        .form-group { margin-bottom: 20px; }
        input[type="text"] { padding: 10px; width: 300px; font-size: 16px; }
        button { padding: 12px 25px; background: #3498db; color: white; border: none; border-radius: 6px; cursor: pointer; font-size: 16px; }
        .info-box { background: #f8f9fa; padding: 20px; border-radius: 8px; margin-top: 25px; }
        .label { font-weight: bold; color: #555; }
        .value { margin-bottom: 12px; }
        .hash { background: #fff3cd; padding: 15px; border-radius: 6px; font-family: monospace; word-break: break-all; }
        .status { font-weight: bold; font-size: 18px; padding: 8px 15px; border-radius: 6px; display: inline-block; }
        .valid { background: #d4edda; color: #155724; }
        .invalid { background: #f8d7da; color: #721c24; }
    </style>
</head>
<body>

<div class="container">
    <h2>🔍 Test Hóa đơn & Chữ ký số</h2>

    <form method="get" action="testInvoice.jsp">
        <div class="form-group">
            <label><strong>Nhập Mã Booking (ID):</strong></label><br>
            <input type="text" name="bookingId" placeholder="Ví dụ: 1" value="<%= bookingIdStr != null ? bookingIdStr : "" %>" required>
            <button type="submit">Xem hóa đơn</button>
        </div>
    </form>

    <% if (booking != null) { %>
        <div class="info-box">
            <h3>Thông tin Hóa đơn</h3>
            
            <div class="value"><span class="label">Mã Booking:</span> <%= booking.getId() %></div>
            <div class="value"><span class="label">Khách hàng:</span> <%= customer != null ? customer.getName() : "N/A" %></div>
            <div class="value"><span class="label">Email:</span> <%= customer != null ? customer.getEmail() : "N/A" %></div>
            <div class="value"><span class="label">Tour:</span> <%= tour != null ? tour.getName() : "N/A" %></div>
            <div class="value"><span class="label">Ngày khởi hành:</span> <%= booking.getDepartureDate() %></div>
            <div class="value"><span class="label">Số người lớn:</span> <%= booking.getNoAdults() %></div>
            <div class="value"><span class="label">Số trẻ em:</span> <%= booking.getNoChildren() %></div>
            <div class="value"><span class="label">Giá tour:</span> <%= tour != null ? tour.getPrice() : "N/A" %> VND</div>

            <hr>
            <h4>Hash hóa đơn (SHA-256)</h4>
            <div class="hash"><%= invoiceHash %></div>

            <hr>
            <h4>Chữ ký số</h4>
            <div class="value"><span class="label">Chữ ký (Base64):</span><br>
                <textarea rows="4" style="width:100%; font-family: monospace;"><%= booking.getSignature() != null ? booking.getSignature() : "Chưa có chữ ký" %></textarea>
            </div>

            <div style="margin-top: 15px;">
                <span class="label">Trạng thái:</span>
                <span class="status <%= signatureStatus.equals("Đã ký") ? "valid" : "invalid" %>">
                    <%= signatureStatus %>
                </span>
            </div>
        </div>
    <% } else if (bookingIdStr != null) { %>
        <p style="color: red; font-weight: bold;">Không tìm thấy Booking với ID = <%= bookingIdStr %></p>
    <% } %>

</div>

</body>
</html>