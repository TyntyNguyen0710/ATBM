<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String activePage = "about";
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

    <title>Giới thiệu – Travel Go</title>

    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/trangchu.css">
    <link rel="stylesheet" href="css/gioiThieu.css">
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

<!-- ===================== PAGE HERO (banner nhỏ) ===================== -->
<!--
    CLASS: .page-hero
        ├── .page-hero__overlay   → lớp phủ tối trên ảnh nền
        └── .page-hero__content
              ├── .page-hero__eyebrow  → label nhỏ phía trên
              └── .page-hero__title    → tiêu đề trang
-->
<section class="page-hero" style="background-image:url('img/tongquan.png');">
    <div class="page-hero__overlay"></div>
    <div class="page-hero__content">
        <span class="page-hero__eyebrow">Travel Go</span>
        <h1 class="page-hero__title">Giới thiệu về chúng tôi</h1>
    </div>
</section>

<!-- ===================== STORY ===================== -->
<!--
    CLASS: .story
        ├── .story__grid       → flex 2 cột (text | ảnh)
        │     ├── .story__text
        │     │     ├── .section-label / .section-title  (dùng chung trangchu.css)
        │     │     └── .story__paragraphs  → các đoạn văn
        │     └── .story__image
        └── .story__stats      → dải số liệu nổi bật dưới story
              └── .story__stat
                    ├── .story__stat-num
                    └── .story__stat-label
-->
<section class="story section">
    <div class="container">
        <div class="story__grid">
            <div class="story__text">
                <span class="section-label">Câu chuyện của chúng tôi</span>
                <h2 class="section-title">16 năm đồng hành cùng những chuyến đi</h2>
                <div class="story__paragraphs">
                    <p>
                        Travel Go được thành lập với mong muốn mang đến cho khách hàng những
                        hành trình khám phá Việt Nam trọn vẹn nhất – an toàn, tiện nghi và
                        đáng nhớ. Từ những ngày đầu chỉ với vài tour nội địa nhỏ, chúng tôi
                        đã phát triển thành một trong những đơn vị tổ chức tour uy tín với
                        hơn 50 hành trình trải dài khắp ba miền.
                    </p>
                    <p>
                        Chúng tôi tin rằng <strong>sự an toàn và minh bạch thông tin</strong>
                        là nền tảng của mọi chuyến đi đáng nhớ. Vì vậy, mọi hợp đồng, thông
                        tin khách hàng và giao dịch thanh toán đều được bảo vệ bằng các tiêu
                        chuẩn mã hóa hiện đại, đảm bảo quyền riêng tư cho từng khách hàng.
                    </p>
                </div>
            </div>
            <div class="story__image">
                <img src="img/daLat.jpg" alt="Hành trình Travel Go">
            </div>
        </div>

        <div class="story__stats">
            <div class="story__stat">
                <span class="story__stat-num">16+</span>
                <span class="story__stat-label">Năm kinh nghiệm</span>
            </div>
            <div class="story__stat">
                <span class="story__stat-num">50+</span>
                <span class="story__stat-label">Tour trong nước</span>
            </div>
            <div class="story__stat">
                <span class="story__stat-num">20K+</span>
                <span class="story__stat-label">Khách hàng tin tưởng</span>
            </div>
            <div class="story__stat">
                <span class="story__stat-num">4.8/5</span>
                <span class="story__stat-label">Đánh giá trung bình</span>
            </div>
        </div>
    </div>
</section>

<!-- ===================== VALUES (Giá trị cốt lõi) ===================== -->
<!--
    CLASS: .values
        └── .values__grid
              └── .value-card
                    ├── .value-card__icon
                    ├── .value-card__title
                    └── .value-card__desc
-->
<section class="values section section--alt">
    <div class="container">
        <span class="section-label">Giá trị cốt lõi</span>
        <h2 class="section-title">Điều làm nên Travel Go</h2>

        <div class="values__grid">
            <div class="value-card">
                <div class="value-card__icon"><i class="fa fa-shield-halved"></i></div>
                <h3 class="value-card__title">An toàn là ưu tiên số một</h3>
                <p class="value-card__desc">
                    Mọi tour đều được kiểm định kỹ về an toàn giao thông, lưu trú và bảo
                    hiểm du lịch trước khi đưa vào vận hành.
                </p>
            </div>
            <div class="value-card">
                <div class="value-card__icon"><i class="fa fa-lock"></i></div>
                <h3 class="value-card__title">Bảo mật thông tin khách hàng</h3>
                <p class="value-card__desc">
                    Dữ liệu cá nhân và hợp đồng của khách hàng được mã hóa, chỉ những
                    nhân sự được phân quyền mới có thể truy cập.
                </p>
            </div>
            <div class="value-card">
                <div class="value-card__icon"><i class="fa fa-handshake"></i></div>
                <h3 class="value-card__title">Minh bạch trong từng hợp đồng</h3>
                <p class="value-card__desc">
                    Giá tour, điều khoản hủy/đổi và chính sách hoàn tiền được nêu rõ
                    ràng, không phụ phí ẩn.
                </p>
            </div>
            <div class="value-card">
                <div class="value-card__icon"><i class="fa fa-heart"></i></div>
                <h3 class="value-card__title">Tận tâm với từng khách hàng</h3>
                <p class="value-card__desc">
                    Đội ngũ tư vấn luôn đồng hành 24/7, hỗ trợ kịp thời trong suốt
                    hành trình của bạn.
                </p>
            </div>
        </div>
    </div>
</section>

<!-- ===================== TEAM (tuỳ chọn, mang tính giới thiệu) ===================== -->
<!--
    CLASS: .team
        └── .team__grid
              └── .team-card
                    ├── .team-card__avatar
                    ├── .team-card__name
                    └── .team-card__role
-->
<section class="team section">
    <div class="container">
        <span class="section-label">Đội ngũ</span>
        <h2 class="section-title">Những người đồng hành cùng bạn</h2>

        <div class="team__grid">
            <div class="team-card">
                <div class="team-card__avatar"><i class="fa fa-user-tie"></i></div>
                <p class="team-card__name">Nguyễn Văn Long</p>
                <p class="team-card__role">Giám đốc điều hành</p>
            </div>
            <div class="team-card">
                <div class="team-card__avatar"><i class="fa fa-user-tie"></i></div>
                <p class="team-card__name">Trần Thị Phúc</p>
                <p class="team-card__role">Trưởng phòng Tư vấn tour</p>
            </div>
            <div class="team-card">
                <div class="team-card__avatar"><i class="fa fa-user-shield"></i></div>
                <p class="team-card__name">Lê Minh An</p>
                <p class="team-card__role">Trưởng phòng Bảo mật &amp; Dữ liệu</p>
            </div>
            <div class="team-card">
                <div class="team-card__avatar"><i class="fa fa-user-tie"></i></div>
                <p class="team-card__name">Phạm Thu Hà</p>
                <p class="team-card__role">Trưởng phòng Chăm sóc khách hàng</p>
            </div>
        </div>
    </div>
</section>

<%@ include file="includes/footer.jspf" %>
</body>
</html>
