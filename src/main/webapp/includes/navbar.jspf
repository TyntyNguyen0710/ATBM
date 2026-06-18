<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
        <header class="navbar">
            <a href="trangchu.jsp" class="navbar__logo">
                <img src="img/logo.png" alt="Travel Go">
            </a>

            <button class="navbar__toggle" id="menuToggle" aria-label="Mở menu" aria-expanded="false">
                <span></span><span></span><span></span>
            </button>

            <nav class="navbar__nav" id="mainNav" aria-label="Menu chính">
                <ul class="navbar__menu">
                    <li class="navbar__item">
                        <a class="navbar__link ${activePage == 'home' ? 'active' : ''}" href="trangchu.jsp">Trang
                            chủ</a>
                    </li>
                    <li class="navbar__item">
                        <a class="navbar__link ${activePage == 'about' ? 'active' : ''}" href="gioiThieu.jsp">Giới
                            thiệu</a>
                    </li>
                    <li class="navbar__item">
                        <a class="navbar__link ${activePage == 'tour' ? 'active' : ''}" href="tour.jsp">Tour</a>
                    </li>
                    <li class="navbar__item">
                        <a class="navbar__link ${activePage == 'tips' ? 'active' : ''}" href="kinhNghiem.jsp">Kinh
                            nghiệm</a>
                    </li>
                    <li class="navbar__item">
                        <a class="navbar__link ${activePage == 'contact' ? 'active' : ''}" href="lienHe.jsp">Liên hệ</a>
                    </li>
                </ul>

                <div class="navbar__actions">
                    <form action="checkingTour" method="get" class="navbar__check-form">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <button type="submit" class="btn btn--outline">
                            <i class="fa fa-search"></i> Kiểm tra tour
                        </button>
                    </form>

                    <div class="navbar__auth">
                        <c:if test="${not empty sessionScope.username}">
                            <a href="information.jsp" class="navbar__welcome">
                                <i class="fa fa-circle-user"></i>
                                <span>Xin chào, <strong>${fn:escapeXml(sessionScope.username)}</strong></span>
                            </a>
                            <form action="logout" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                <button class="btn btn--ghost navbar__logout" type="submit">
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