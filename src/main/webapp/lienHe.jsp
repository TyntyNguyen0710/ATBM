<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String activePage = "contact";
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

    <title>Liên hệ – Travel Go</title>

    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/trangchu.css">
    <link rel="stylesheet" href="css/tour.css">
    <link rel="stylesheet" href="css/lienHe.css">
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
<section class="page-hero" style="background-image:url('img/Hue.jpg');">
    <div class="page-hero__overlay"></div>
    <div class="page-hero__content">
        <span class="page-hero__eyebrow">Travel Go</span>
        <h1 class="page-hero__title">Liên hệ với chúng tôi</h1>
    </div>
</section>

<!-- ===================== CONTACT MAIN ===================== -->
<!--
    CLASS: .contact
        └── .contact__grid             → flex 2 cột (info | form)
              ├── .contact__info        → cột trái
              │     ├── .contact__info-item       → từng dòng info (icon + text)
              │     │     ├── .contact__info-icon
              │     │     └── .contact__info-text
              │     │           ├── .contact__info-label   → label nhỏ
              │     │           └── .contact__info-value    → giá trị chính
              │     └── .contact__socials              → icon mạng xã hội (tái dùng .footer__social-link)
              └── .contact__form-wrap   → cột phải, bọc form
                    ├── .contact__form-title
                    └── .contact-form
                          ├── .contact-form__row    → 2 input ngang (Họ tên + SĐT)
                          ├── .contact-form__group  → wrapper label + input
                          ├── .contact-form__label
                          ├── .contact-form__input / textarea
                          ├── .contact-form__note   → ghi chú bảo mật dưới form
                          └── .contact-form__submit → nút gửi
-->
<section class="contact section">
    <div class="container">
        <div class="contact__grid">

            <div class="contact__info">
                <span class="section-label">Liên hệ</span>
                <h2 class="section-title">Chúng tôi luôn sẵn sàng hỗ trợ</h2>

                <div class="contact__info-item">
                    <div class="contact__info-icon"><i class="fa fa-location-dot"></i></div>
                    <div class="contact__info-text">
                        <span class="contact__info-label">Địa chỉ</span>
                        <span class="contact__info-value">1234 QL1K, TP. Thủ Đức, TP.HCM</span>
                    </div>
                </div>

                <div class="contact__info-item">
                    <div class="contact__info-icon"><i class="fa fa-phone"></i></div>
                    <div class="contact__info-text">
                        <span class="contact__info-label">Hotline</span>
                        <span class="contact__info-value">0123 456 789</span>
                    </div>
                </div>

                <div class="contact__info-item">
                    <div class="contact__info-icon"><i class="fa fa-envelope"></i></div>
                    <div class="contact__info-text">
                        <span class="contact__info-label">Email</span>
                        <span class="contact__info-value">travelgo@gmail.com</span>
                    </div>
                </div>

                <div class="contact__info-item">
                    <div class="contact__info-icon"><i class="fa fa-clock"></i></div>
                    <div class="contact__info-text">
                        <span class="contact__info-label">Giờ làm việc</span>
                        <span class="contact__info-value">8:00 – 21:00, Thứ 2 – Chủ nhật</span>
                    </div>
                </div>

                <div class="contact__socials">
                    <a href="https://www.facebook.com" class="footer__social-link footer__social-link--light" aria-label="Facebook" rel="noopener noreferrer" target="_blank">
                        <i class="fa-brands fa-facebook"></i>
                    </a>
                    <a href="https://www.instagram.com" class="footer__social-link footer__social-link--light" aria-label="Instagram" rel="noopener noreferrer" target="_blank">
                        <i class="fa-brands fa-instagram"></i>
                    </a>
                    <a href="mailto:travelgo@gmail.com" class="footer__social-link footer__social-link--light" aria-label="Email">
                        <i class="fa fa-envelope"></i>
                    </a>
                    <a href="https://www.tiktok.com" class="footer__social-link footer__social-link--light" aria-label="TikTok" rel="noopener noreferrer" target="_blank">
                        <i class="fa-brands fa-tiktok"></i>
                    </a>
                </div>
            </div>

            <div class="contact__form-wrap">
                <h3 class="contact__form-title">Gửi yêu cầu tư vấn</h3>

                <%-- 
                    Form gửi tới servlet "ContactServlet" (cần tạo ở module sau).
                    CSRF token đính kèm để chống giả mạo request.
                    Validate phía client (required, pattern) + BẮT BUỘC validate
                    lại phía server trong servlet, không tin tưởng dữ liệu client.
                --%>
                <form class="contact-form" action="contact" method="post" novalidate>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                    <div class="contact-form__row">
                        <div class="contact-form__group">
                            <label class="contact-form__label" for="cf-name">Họ và tên <span>*</span></label>
                            <input class="contact-form__input" type="text" id="cf-name" name="fullName"
                                   placeholder="Nguyễn Văn A" required maxlength="100">
                        </div>
                        <div class="contact-form__group">
                            <label class="contact-form__label" for="cf-phone">Số điện thoại <span>*</span></label>
                            <input class="contact-form__input" type="tel" id="cf-phone" name="phone"
                                   placeholder="09xxxxxxxx" required pattern="[0-9]{9,11}" maxlength="11">
                        </div>
                    </div>

                    <div class="contact-form__group">
                        <label class="contact-form__label" for="cf-email">Email <span>*</span></label>
                        <input class="contact-form__input" type="email" id="cf-email" name="email"
                               placeholder="ban@email.com" required maxlength="150">
                    </div>

                    <div class="contact-form__group">
                        <label class="contact-form__label" for="cf-subject">Chủ đề</label>
                        <select class="contact-form__input" id="cf-subject" name="subject">
                            <option value="tour">Tư vấn tour</option>
                            <option value="booking">Hỗ trợ đặt tour</option>
                            <option value="complaint">Phản hồi / Khiếu nại</option>
                            <option value="other">Khác</option>
                        </select>
                    </div>

                    <div class="contact-form__group">
                        <label class="contact-form__label" for="cf-message">Nội dung <span>*</span></label>
                        <textarea class="contact-form__input contact-form__textarea" id="cf-message" name="message"
                                  rows="5" placeholder="Bạn cần hỗ trợ gì..." required maxlength="2000"></textarea>
                    </div>

                    <p class="contact-form__note">
                        <i class="fa fa-lock"></i>
                        Thông tin liên hệ của bạn được mã hóa khi truyền tải và chỉ dùng để phản hồi yêu cầu này.
                    </p>

                    <button type="submit" class="btn btn--primary contact-form__submit">
                        Gửi yêu cầu <i class="fa fa-paper-plane"></i>
                    </button>
                </form>
            </div>

        </div>
    </div>
</section>

<!-- ===================== MAP ===================== -->
<!--
    CLASS: .contact-map
        └── .contact-map__frame    → bọc iframe bản đồ, set chiều cao cố định
-->
<section class="contact-map">
    <div class="contact-map__frame">
        <iframe
            src="https://www.google.com/maps?q=Th%C3%A0nh%20ph%E1%BB%91%20Th%E1%BB%A7%20%C4%90%E1%BB%A9c%2C%20H%E1%BB%93%20Ch%C3%AD%20Minh&output=embed"
            width="100%" height="100%" style="border:0;" allowfullscreen="" loading="lazy"
            referrerpolicy="no-referrer-when-downgrade"
            title="Bản đồ vị trí Travel Go"></iframe>
    </div>
</section>

<%@ include file="includes/footer.jspf" %>
</body>
</html>
