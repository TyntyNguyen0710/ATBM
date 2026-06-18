<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.ArrayList"%>
<%
    String activePage = "tour";

    /*
       Trang Tour tự truy vấn DB, KHÔNG phụ thuộc vào session "tours"
       được set từ trangchu.jsp (khác với bản gốc) — để trang này có
       thể chạy độc lập nếu người dùng vào trực tiếp /tour.jsp.
    */
    dao.tourDAO td = dao.tourDAO.getIntance();
    ArrayList<model.Tour> tours = new ArrayList<model.Tour>();
    String errorMsg = null;
    try {
        tours = td.selectAll();
    } catch (Exception e) {
        errorMsg = "Không thể tải danh sách tour lúc này. Vui lòng thử lại sau.";
        // Không in stacktrace ra response (an toàn) — chỉ log phía server.
        e.printStackTrace();
    }
    pageContext.setAttribute("tours", tours);
    pageContext.setAttribute("errorMsg", errorMsg);
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Security-Policy"
          content="default-src 'self';
                   script-src 'self' https://cdnjs.cloudflare.com https://code.jquery.com 'unsafe-inline';
                   style-src  'self' https://cdnjs.cloudflare.com https://fonts.googleapis.com 'unsafe-inline';
                   font-src   'self' https://fonts.gstatic.com https://cdnjs.cloudflare.com;
                   img-src    'self' data:;
                   connect-src 'self';">
    <meta http-equiv="X-Content-Type-Options" content="nosniff">
    <meta http-equiv="X-Frame-Options" content="DENY">
    <meta name="referrer" content="strict-origin-when-cross-origin">

    <title>Tất cả Tour – Travel Go</title>

    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/trangchu.css">
    <link rel="stylesheet" href="css/tour.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:wght@300;400;500;600;700&family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
          integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
          crossorigin="anonymous" referrerpolicy="no-referrer">
</head>
<body>

<%@ include file="includes/navbar.jspf" %>

<!-- ===================== PAGE HERO ===================== -->
<section class="page-hero" style="background-image:url('img/sonLa.jpg');">
    <div class="page-hero__overlay"></div>
    <div class="page-hero__content">
        <span class="page-hero__eyebrow">Travel Go</span>
        <h1 class="page-hero__title">Tất cả tour du lịch</h1>
    </div>
</section>

<!-- ===================== TOUR LIST ===================== -->
<!--
    CLASS: .tour-list
        ├── .tour-list__toolbar     → dòng hiển thị tổng số tour
        │     └── .tour-list__count
        ├── .tour-grid              → grid 3 cột (tái dùng từ trangchu.css)
        │     └── .tour-card        → card tour (giống trang chủ)
        │           └── .tour-card__badge--featured  → biến thể badge "Nổi bật"
        └── .tour-list__empty       → trạng thái rỗng / lỗi
              ├── .tour-list__empty-icon
              └── .tour-list__empty-text
-->
<section class="tour-list section">
    <div class="container">

        <c:if test="${empty errorMsg}">
            <div class="tour-list__toolbar">
                <span class="section-label">Khám phá</span>
                <h2 class="section-title" style="margin-bottom:8px;">Tất cả tour hiện có</h2>
                <p class="tour-list__count">${fn:length(tours)} tour đang mở bán</p>
            </div>
        </c:if>

        <c:if test="${not empty errorMsg}">
            <div class="tour-list__empty">
                <div class="tour-list__empty-icon"><i class="fa fa-triangle-exclamation"></i></div>
                <p class="tour-list__empty-text">${fn:escapeXml(errorMsg)}</p>
            </div>
        </c:if>

        <c:if test="${empty errorMsg and fn:length(tours) == 0}">
            <div class="tour-list__empty">
                <div class="tour-list__empty-icon"><i class="fa fa-map"></i></div>
                <p class="tour-list__empty-text">Hiện chưa có tour nào được mở bán.</p>
            </div>
        </c:if>

        <c:if test="${empty errorMsg and fn:length(tours) > 0}">
            <div class="tour-grid">
                <c:forEach var="tour" items="${tours}">
                    <article class="tour-card">
                        <div class="tour-card__img-wrap">
                            <img class="tour-card__img"
                                 src="img/${tour.imagePaths[0]}"
                                 alt="${fn:escapeXml(tour.name)}"
                                 loading="lazy">
                            <c:if test="${tour.id == 1 or tour.id == 5 or tour.id == 7 or tour.id == 9}">
                                <span class="tour-card__badge">Nổi bật</span>
                            </c:if>
                        </div>
                        <div class="tour-card__body">
                            <a class="tour-card__name"
                               href="showTour.jsp?tourId=${tour.id}">
                                ${fn:escapeXml(tour.name)}
                            </a>
                            <ul class="tour-card__meta">
                                <li class="tour-card__meta-item">
                                    <i class="fa fa-clock"></i>
                                    <span>${fn:escapeXml(tour.duration)}</span>
                                </li>
                                <li class="tour-card__meta-item">
                                    <i class="fa fa-calendar-days"></i>
                                    <span>${fn:escapeXml(tour.schedule)}</span>
                                </li>
                            </ul>
                            <div class="tour-card__price">
                                <fmt:setLocale value="vi_VN"/>
                                <fmt:formatNumber value="${tour.price}"/> VNĐ
                            </div>
                            <a class="tour-card__cta"
                               href="showTour.jsp?tourId=${tour.id}">
                                Xem chi tiết <i class="fa fa-arrow-right"></i>
                            </a>
                        </div>
                    </article>
                </c:forEach>
            </div>
        </c:if>

    </div>
</section>

<%@ include file="includes/footer.jspf" %>
</body>
</html>
