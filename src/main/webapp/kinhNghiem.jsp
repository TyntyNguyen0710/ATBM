<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String activePage = "tips";
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

    <title>Kinh nghiệm du lịch – Travel Go</title>

    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/trangchu.css">
    <link rel="stylesheet" href="css/tour.css">
    <link rel="stylesheet" href="css/kinhNghiem.css">
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
<section class="page-hero" style="background-image:url('img/kinhNghiemTB.jpg');">
    <div class="page-hero__overlay"></div>
    <div class="page-hero__content">
        <span class="page-hero__eyebrow">Travel Go</span>
        <h1 class="page-hero__title">Cẩm nang &amp; kinh nghiệm du lịch</h1>
    </div>
</section>

<!-- ===================== TIPS LIST ===================== -->
<!--
    CLASS: .tips-list
        └── .tips-list__grid       → grid 3 cột đều nhau (khác layout mosaic ở trang chủ)
              └── .tips-article    → card bài viết
                    ├── .tips-article__img
                    ├── .tips-article__body
                    │     ├── .tips-article__date
                    │     ├── .tips-article__title (a)
                    │     ├── .tips-article__excerpt
                    │     └── .tips-article__more (a)
-->
<section class="tips-list section">
    <div class="container">
        <span class="section-label">Cẩm nang</span>
        <h2 class="section-title">Tất cả bài viết kinh nghiệm</h2>

        <div class="tips-list__grid">

            <article class="tips-article">
                <img class="tips-article__img" src="img/kinhNghiemTB.jpg" alt="Tây Bắc">
                <div class="tips-article__body">
                    <span class="tips-article__date">02/11/2019</span>
                    <a href="tay_bac.jsp" class="tips-article__title">
                        Kinh nghiệm phượt Tây Bắc mùa đông
                    </a>
                    <p class="tips-article__excerpt">
                        Chia sẻ lại những kỉ niệm đẹp trong chuyến du lịch Mộc Châu 5 ngày,
                        từ cung đường đèo đến những điểm dừng chân không thể bỏ qua.
                    </p>
                    <a href="tay_bac.jsp" class="tips-article__more">Đọc thêm <i class="fa fa-arrow-right"></i></a>
                </div>
            </article>

            <article class="tips-article">
                <img class="tips-article__img" src="img/kinhNghiemDL.jpg" alt="Đà Lạt">
                <div class="tips-article__body">
                    <span class="tips-article__date">02/11/2019</span>
                    <a href="da_lat.jsp" class="tips-article__title">
                        Kinh nghiệm Đà Lạt mùa đông
                    </a>
                    <p class="tips-article__excerpt">
                        Tổng hợp lịch trình 5 ngày khám phá Đà Lạt: nơi ở, ăn uống và
                        những góc check-in không thể thiếu khi se lạnh về.
                    </p>
                    <a href="da_lat.jsp" class="tips-article__more">Đọc thêm <i class="fa fa-arrow-right"></i></a>
                </div>
            </article>

            <article class="tips-article">
                <img class="tips-article__img" src="img/kinhNghiemSP.jpg" alt="Sapa">
                <div class="tips-article__body">
                    <span class="tips-article__date">02/11/2019</span>
                    <a href="sapa.jsp" class="tips-article__title">
                        Kinh nghiệm du lịch tự túc Sapa
                    </a>
                    <p class="tips-article__excerpt">
                        Hướng dẫn chi tiết cách di chuyển, lưu trú và các điểm trekking
                        đẹp nhất cho chuyến đi Sapa tự túc 5 ngày.
                    </p>
                    <a href="sapa.jsp" class="tips-article__more">Đọc thêm <i class="fa fa-arrow-right"></i></a>
                </div>
            </article>

            <article class="tips-article">
                <img class="tips-article__img" src="img/kinhNghiemVH.jpg" alt="Vĩnh Hy">
                <div class="tips-article__body">
                    <span class="tips-article__date">02/11/2019</span>
                    <a href="vinh_hy.jsp" class="tips-article__title">
                        Kinh nghiệm Vĩnh Hy mùa xuân
                    </a>
                    <p class="tips-article__excerpt">
                        Vịnh Vĩnh Hy mùa xuân đẹp đến nao lòng — chia sẻ hành trình 4 ngày
                        cùng những trải nghiệm lặn ngắm san hô đáng nhớ.
                    </p>
                    <a href="vinh_hy.jsp" class="tips-article__more">Đọc thêm <i class="fa fa-arrow-right"></i></a>
                </div>
            </article>

        </div>
    </div>
</section>

<%@ include file="includes/footer.jspf" %>
</body>
</html>
