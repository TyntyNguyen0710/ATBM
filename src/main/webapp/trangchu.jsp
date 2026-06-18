<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
                <%@page import="model.Tour" %>
                    <%@page import="dao.*" %>
                        <%@page import="DataBase.*" %>
                            <%@page import="java.util.ArrayList" %>
                                <!DOCTYPE html>
                                <html lang="vi">

                                <head>
                                    <meta charset="UTF-8">
                                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                    <%-- Bảo mật: Content Security Policy --%>
                                        <meta http-equiv="Content-Security-Policy" content="default-src 'self';
                   script-src 'self' https://cdnjs.cloudflare.com https://code.jquery.com 'unsafe-inline';
                   style-src  'self' https://cdnjs.cloudflare.com https://fonts.googleapis.com 'unsafe-inline';
                   font-src   'self' https://fonts.gstatic.com https://cdnjs.cloudflare.com;
                   img-src    'self' data:;
                   connect-src 'self';">
                                        <meta http-equiv="X-Content-Type-Options" content="nosniff">
                                        <meta http-equiv="X-Frame-Options" content="DENY">
                                        <meta name="referrer" content="strict-origin-when-cross-origin">

                                        <title>Travel Go – Du lịch an toàn, trải nghiệm đỉnh cao</title>

                                        <link rel="stylesheet" href="css/reset.css">
                                        <link rel="stylesheet" href="css/trangchu.css">
                                        <link rel="preconnect" href="https://fonts.googleapis.com">
                                        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                                        <link
                                            href="https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:wght@300;400;500;600;700&family=Playfair+Display:wght@700&display=swap"
                                            rel="stylesheet">
                                        <link rel="stylesheet"
                                            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
                                            integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
                                            crossorigin="anonymous" referrerpolicy="no-referrer">
                                </head>

                                <body>
                                    <header class="navbar">
                                        <a href="trangchu.jsp" class="navbar__logo">
                                            <img src="img/logo.png" alt="Travel Go">
                                        </a>

                                        <button class="navbar__toggle" id="menuToggle" aria-label="Mở menu"
                                            aria-expanded="false">
                                            <span></span><span></span><span></span>
                                        </button>

                                        <nav class="navbar__nav" id="mainNav" aria-label="Menu chính">
                                            <ul class="navbar__menu">
                                                <li class="navbar__item"><a class="navbar__link active"
                                                        href="trangchu.jsp">Trang chủ</a></li>
                                                <li class="navbar__item"><a class="navbar__link"
                                                        href="gioiThieu.jsp">Giới thiệu</a></li>
                                                <li class="navbar__item"><a class="navbar__link"
                                                        href="tour.jsp">Tour</a></li>
                                                <li class="navbar__item"><a class="navbar__link"
                                                        href="kinhNghiem.jsp">Kinh nghiệm</a></li>
                                                <li class="navbar__item"><a class="navbar__link" href="lienHe.jsp">Liên
                                                        hệ</a></li>
                                            </ul>

                                            <div class="navbar__actions">
                                                <%-- CSRF token cho form kiểm tra tour --%>
                                                    <form action="checkingTour" method="get" class="navbar__check-form">
                                                        <input type="hidden" name="${_csrf.parameterName}"
                                                            value="${_csrf.token}">
                                                        <button type="submit" class="btn btn--outline">
                                                            <i class="fa fa-search"></i> Kiểm tra tour
                                                        </button>
                                                    </form>

                                                    <div class="navbar__auth">
                                                        <c:if test="${not empty sessionScope.username}">
                                                            <a href="information.jsp" class="navbar__welcome">
                                                                <i class="fa fa-circle-user"></i>
                                                                <span>Xin chào,
                                                                    <strong>${fn:escapeXml(sessionScope.username)}</strong></span>
                                                            </a>
                                                            <form action="logout" method="post">
                                                                <input type="hidden" name="${_csrf.parameterName}"
                                                                    value="${_csrf.token}">
                                                                <button class="btn btn--ghost navbar__logout"
                                                                    type="submit">
                                                                    <i class="fa fa-right-from-bracket"></i> Đăng xuất
                                                                </button>
                                                            </form>
                                                        </c:if>
                                                        <c:if test="${empty sessionScope.username}">
                                                            <a href="login.jsp" class="btn btn--primary navbar__login">
                                                                <i class="fa fa-user"></i> Đăng nhập
                                                            </a>
                                                        </c:if>
                                                    </div>
                                            </div>
                                        </nav>
                                    </header>
                                    <section class="hero" aria-label="Ảnh bìa điểm đến">
                                        <div class="hero__track" id="heroTrack">
                                            <div class="hero__slide">
                                                <img class="hero__img" src="img/NTTC.jpg" alt="Nha Trang">
                                                <div class="hero__caption">
                                                    <span class="hero__label">Khám phá</span>
                                                    <h1 class="hero__title">Nha Trang<br>Biển xanh cát trắng</h1>
                                                </div>
                                            </div>
                                            <div class="hero__slide">
                                                <img class="hero__img" src="img/DLTC.jpg" alt="Đà Lạt">
                                                <div class="hero__caption">
                                                    <span class="hero__label">Khám phá</span>
                                                    <h1 class="hero__title">Đà Lạt<br>Thành phố ngàn hoa</h1>
                                                </div>
                                            </div>
                                            <div class="hero__slide">
                                                <img class="hero__img" src="img/PYTC.jpg" alt="Phú Yên">
                                                <div class="hero__caption">
                                                    <span class="hero__label">Khám phá</span>
                                                    <h1 class="hero__title">Phú Yên<br>Hoang sơ mê hoặc</h1>
                                                </div>
                                            </div>
                                            <div class="hero__slide">
                                                <img class="hero__img" src="img/VHTC.jfif" alt="Vĩnh Hy">
                                                <div class="hero__caption">
                                                    <span class="hero__label">Khám phá</span>
                                                    <h1 class="hero__title">Vĩnh Hy<br>Vịnh đẹp ẩn giấu</h1>
                                                </div>
                                            </div>
                                            <div class="hero__slide">
                                                <img class="hero__img" src="img/HATC.jfif" alt="Hội An">
                                                <div class="hero__caption">
                                                    <span class="hero__label">Khám phá</span>
                                                    <h1 class="hero__title">Hội An<br>Di sản ngàn năm</h1>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="hero__dots" id="heroDots" role="tablist"
                                            aria-label="Điều hướng slider"></div>
                                    </section>
                                    <section class="whyus section">
                                        <div class="container">
                                            <span class="section-label">Về chúng tôi</span>
                                            <h2 class="section-title">Tại sao chọn Travel Go?</h2>

                                            <div class="whyus__grid">
                                                <div class="whyus__text">
                                                    <p class="whyus__desc">
                                                        Hơn <strong>16 năm</strong> tổ chức tour du lịch trong nước,
                                                        chúng tôi cam kết mang đến
                                                        hành trình tuyệt vời nhất với dịch vụ chuyên nghiệp, an toàn và
                                                        đáng tin cậy.
                                                    </p>
                                                    <ul class="whyus__features">
                                                        <li class="whyus__feature">
                                                            <span class="whyus__icon"><i class="fa fa-plane"></i></span>
                                                            <div>
                                                                <strong>Chuyến bay đẳng cấp</strong>
                                                                <p>Đối tác với các hãng hàng không uy tín hàng đầu</p>
                                                            </div>
                                                        </li>
                                                        <li class="whyus__feature">
                                                            <span class="whyus__icon"><i class="fa fa-bed"></i></span>
                                                            <div>
                                                                <strong>Khách sạn tiện nghi</strong>
                                                                <p>Hệ thống khách sạn 3-5 sao được kiểm định kỹ lưỡng
                                                                </p>
                                                            </div>
                                                        </li>
                                                        <li class="whyus__feature">
                                                            <span class="whyus__icon"><i
                                                                    class="fa fa-shield-halved"></i></span>
                                                            <div>
                                                                <strong>Bảo mật thông tin</strong>
                                                                <p>Dữ liệu khách hàng được mã hóa AES-256, hợp đồng có
                                                                    chữ ký số</p>
                                                            </div>
                                                        </li>
                                                        <li class="whyus__feature">
                                                            <span class="whyus__icon"><i class="fa fa-route"></i></span>
                                                            <div>
                                                                <strong>Hành trình đa dạng</strong>
                                                                <p>Hơn 50 tour trong nước với lịch trình linh hoạt</p>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                </div>

                                                <div class="whyus__visual">
                                                    <img src="img/tongquan.png" alt="Tổng quan Travel Go">
                                                    <div class="whyus__badge">
                                                        <span class="whyus__badge-num">50+</span>
                                                        <span class="whyus__badge-text">Tour trong nước</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </section>
                                    <section class="destinations section section--alt">
                                        <div class="container">
                                            <span class="section-label">Địa điểm</span>
                                            <h2 class="section-title">Điểm đến hấp dẫn</h2>

                                            <div class="destinations__grid">
                                                <div class="dest-card dest-card--large">
                                                    <img src="img/daLat.jpg" alt="Đà Lạt">
                                                    <div class="dest-card__info">
                                                        <span class="dest-card__name">Đà Lạt</span>
                                                        <span class="dest-card__sub">Thành phố ngàn hoa</span>
                                                    </div>
                                                </div>
                                                <div class="dest-card">
                                                    <img src="img/hanoi.jpg" alt="Hà Nội">
                                                    <div class="dest-card__info">
                                                        <span class="dest-card__name">Hà Nội</span>
                                                        <span class="dest-card__sub">Thủ đô nghìn năm văn hiến</span>
                                                    </div>
                                                </div>
                                                <div class="dest-card">
                                                    <img src="img/ninhbinh.jpg" alt="Ninh Bình">
                                                    <div class="dest-card__info">
                                                        <span class="dest-card__name">Ninh Bình</span>
                                                        <span class="dest-card__sub">Hạ Long trên cạn</span>
                                                    </div>
                                                </div>
                                                <div class="dest-card">
                                                    <img src="img/sonLa.jpg" alt="Sơn La">
                                                    <div class="dest-card__info">
                                                        <span class="dest-card__name">Sơn La</span>
                                                        <span class="dest-card__sub">Miền núi Tây Bắc</span>
                                                    </div>
                                                </div>
                                                <div class="dest-card">
                                                    <img src="img/Hue.jpg" alt="Huế">
                                                    <div class="dest-card__info">
                                                        <span class="dest-card__name">Huế</span>
                                                        <span class="dest-card__sub">Cố đô cổ kính</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </section>
                                    <section class="featured-tours section">
                                        <div class="container">
                                            <span class="section-label">Nổi bật</span>
                                            <h2 class="section-title">Tour nổi bật</h2>

                                            <% dao.tourDAO td=dao.tourDAO.getIntance(); java.util.ArrayList<model.Tour>
                                                tours = td.selectAll();
                                                request.getSession().setAttribute("tours", tours);
                                                %>

                                                <div class="tour-grid">
                                                    <c:forEach var="tour" items="${tours}">
                                                        <c:if test="${tour.id >= 1 and tour.id <= 6}">
                                                            <article class="tour-card">
                                                                <div class="tour-card__img-wrap">
                                                                    <img class="tour-card__img"
                                                                        src="img/${tour.imagePaths[0]}"
                                                                        alt="${fn:escapeXml(tour.name)}" loading="lazy">
                                                                    <span class="tour-card__badge">HOT</span>
                                                                </div>
                                                                <div class="tour-card__body">
                                                                    <a class="tour-card__name"
                                                                        href="showTour.jsp?tourId=${tour.id}">
                                                                        ${fn:escapeXml(tour.name)}
                                                                    </a>
                                                                    <ul class="tour-card__meta">
                                                                        <li class="tour-card__meta-item">
                                                                            <i class="fa fa-clock"></i>
                                                                            <span>${tour.duration}</span>
                                                                        </li>
                                                                        <li class="tour-card__meta-item">
                                                                            <i class="fa fa-calendar-days"></i>
                                                                            <span>${tour.schedule}</span>
                                                                        </li>
                                                                    </ul>
                                                                    <div class="tour-card__price">
                                                                        <fmt:setLocale value="vi_VN" />
                                                                        <fmt:formatNumber value="${tour.price}" /> VNĐ
                                                                    </div>
                                                                    <a class="tour-card__cta"
                                                                        href="showTour.jsp?tourId=${tour.id}">
                                                                        Xem chi tiết <i class="fa fa-arrow-right"></i>
                                                                    </a>
                                                                </div>
                                                            </article>
                                                        </c:if>
                                                    </c:forEach>
                                                </div>

                                                <div class="section-more">
                                                    <a href="tour.jsp" class="btn btn--primary">Xem tất cả tour <i
                                                            class="fa fa-arrow-right"></i></a>
                                                </div>
                                        </div>
                                    </section>
                                    <section class="travel-tips section section--alt">
                                        <div class="container">
                                            <span class="section-label">Cẩm nang</span>
                                            <h2 class="section-title">Kinh nghiệm du lịch</h2>

                                            <div class="tips-grid">
                                                <div class="tip-card tip-card--featured">
                                                    <img class="tip-card__img" src="img/kinhNghiemTB.jpg" alt="Tây Bắc">
                                                    <span class="tip-card__date">02/11/2019</span>
                                                    <a href="tay_bac.jsp" class="tip-card__title">
                                                        Kinh nghiệm phượt Tây Bắc mùa đông
                                                    </a>
                                                    <p class="tip-card__excerpt">
                                                        Tôi vừa kết thúc chuyến du lịch Mộc Châu trong 5 ngày và đang
                                                        rất háo hức
                                                        chia sẻ lại những kỉ niệm đẹp trong chuyến đi đó...
                                                    </p>
                                                    <a href="tay_bac.jsp" class="tip-card__more">Đọc thêm <i
                                                            class="fa fa-arrow-right"></i></a>
                                                </div>

                                                <aside class="tips-sidebar">
                                                    <div class="tip-card tip-card--mini">
                                                        <img class="tip-card__img-sm" src="img/kinhNghiemDL.jpg"
                                                            alt="Đà Lạt">
                                                        <div class="tip-card__content">
                                                            <span class="tip-card__date">02/11/2019</span>
                                                            <a href="da_lat.jsp" class="tip-card__title">Kinh nghiệm Đà
                                                                Lạt mùa đông</a>
                                                            <p>Tôi vừa kết thúc chuyến du lịch Đà Lạt trong 5 ngày...
                                                            </p>
                                                        </div>
                                                    </div>
                                                    <div class="tip-card tip-card--mini">
                                                        <img class="tip-card__img-sm" src="img/kinhNghiemSP.jpg"
                                                            alt="Sapa">
                                                        <div class="tip-card__content">
                                                            <span class="tip-card__date">02/11/2019</span>
                                                            <a href="sapa.jsp" class="tip-card__title">Kinh nghiệm du
                                                                lịch tự túc Sapa</a>
                                                            <p>Tôi vừa kết thúc chuyến du lịch Sapa trong 5 ngày...</p>
                                                        </div>
                                                    </div>
                                                    <div class="tip-card tip-card--mini">
                                                        <img class="tip-card__img-sm" src="img/kinhNghiemVH.jpg"
                                                            alt="Vĩnh Hy">
                                                        <div class="tip-card__content">
                                                            <span class="tip-card__date">02/11/2019</span>
                                                            <a href="vinh_hy.jsp" class="tip-card__title">Kinh nghiệm
                                                                Vĩnh Hy mùa xuân</a>
                                                            <p>Tôi vừa kết thúc chuyến đi Vĩnh Hy trong 4 ngày...</p>
                                                        </div>
                                                    </div>
                                                </aside>
                                            </div>
                                        </div>
                                    </section>
                                    <footer class="footer">
                                        <div class="container">
                                            <div class="footer__top">
                                                <div class="footer__col">
                                                    <p class="footer__heading">Thông tin liên hệ</p>
                                                    <p class="footer__company">Công ty du lịch Travel Go</p>
                                                    <address class="footer__address">
                                                        <i class="fa fa-location-dot"></i> 1234 QL1K, TP. Thủ Đức, HCM
                                                    </address>
                                                    <p class="footer__phone"><i class="fa fa-phone"></i> 0123 456 789
                                                    </p>
                                                    <a class="footer__email" href="mailto:travelgo@gmail.com">
                                                        <i class="fa fa-envelope"></i> travelgo@gmail.com
                                                    </a>
                                                </div>

                                                <div class="footer__col">
                                                    <p class="footer__heading">Hỗ trợ tư vấn</p>
                                                    <p class="footer__hotline">Hotline 0123 456 789</p>
                                                    <div class="footer__socials">
                                                        <a href="https://www.facebook.com" class="footer__social-link"
                                                            aria-label="Facebook" rel="noopener noreferrer"
                                                            target="_blank">
                                                            <i class="fa-brands fa-facebook"></i>
                                                        </a>
                                                        <a href="https://www.instagram.com" class="footer__social-link"
                                                            aria-label="Instagram" rel="noopener noreferrer"
                                                            target="_blank">
                                                            <i class="fa-brands fa-instagram"></i>
                                                        </a>
                                                        <a href="mailto:travelgo@gmail.com" class="footer__social-link"
                                                            aria-label="Email">
                                                            <i class="fa fa-envelope"></i>
                                                        </a>
                                                        <a href="https://www.tiktok.com" class="footer__social-link"
                                                            aria-label="TikTok" rel="noopener noreferrer"
                                                            target="_blank">
                                                            <i class="fa-brands fa-tiktok"></i>
                                                        </a>
                                                    </div>
                                                </div>

                                                <div class="footer__col">
                                                    <p class="footer__heading">Thông tin cần biết</p>
                                                    <nav class="footer__links" aria-label="Thông tin pháp lý">
                                                        <a class="footer__link" href="trangchu.jsp">Điều kiện &amp; điều
                                                            khoản</a>
                                                        <a class="footer__link" href="trangchu.jsp">Phương thức thanh
                                                            toán</a>
                                                        <a class="footer__link" href="trangchu.jsp">Bảo mật thông tin
                                                            khách hàng</a>
                                                        <a class="footer__link" href="trangchu.jsp">Chính sách quy
                                                            định</a>
                                                    </nav>
                                                </div>
                                            </div>

                                            <div class="footer__bottom">
                                                <p class="footer__copy">
                                                    &copy; 2024 Travel Go – Bản quyền thuộc về Du Lịch Travel Go.
                                                    Thông tin khách hàng được bảo mật theo tiêu chuẩn
                                                    <strong>AES-256</strong>.
                                                </p>
                                            </div>
                                        </div>
                                    </footer>

                                    <script src="https://code.jquery.com/jquery-3.7.1.min.js"
                                        integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
                                        crossorigin="anonymous"></script>
                                    <script>
                                        const toggle = document.getElementById('menuToggle');
                                        const nav = document.getElementById('mainNav');
                                        toggle.addEventListener('click', () => {
                                            const open = nav.classList.toggle('is-open');
                                            toggle.setAttribute('aria-expanded', open);
                                            toggle.classList.toggle('is-open', open);
                                        });

                                        (function () {
                                            const track = document.getElementById('heroTrack');
                                            const dotsEl = document.getElementById('heroDots');
                                            const slides = track.querySelectorAll('.hero__slide');
                                            let current = 0, timer;

                                            slides.forEach((_, i) => {
                                                const btn = document.createElement('button');
                                                btn.className = 'hero__dot' + (i === 0 ? ' hero__dot--active' : '');
                                                btn.setAttribute('role', 'tab');
                                                btn.setAttribute('aria-label', 'Slide ' + (i + 1));
                                                btn.addEventListener('click', () => goTo(i));
                                                dotsEl.appendChild(btn);
                                            });

                                            function goTo(idx) {
                                                slides[current].classList.remove('hero__slide--active');
                                                dotsEl.children[current].classList.remove('hero__dot--active');
                                                current = (idx + slides.length) % slides.length;
                                                slides[current].classList.add('hero__slide--active');
                                                dotsEl.children[current].classList.add('hero__dot--active');
                                                restartTimer();
                                            }

                                            function restartTimer() {
                                                clearInterval(timer);
                                                timer = setInterval(() => goTo(current + 1), 5000);
                                            }

                                            slides[0].classList.add('hero__slide--active');
                                            restartTimer();
                                        })();
                                    </script>
                                </body>

                                </html>