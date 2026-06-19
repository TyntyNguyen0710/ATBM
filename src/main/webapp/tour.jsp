<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import=" model.Tour"%>
<%@page import=" dao.*"%>
<%@page import=" DataBase.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon" href="favicon.ico" type="image/x-icon">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/tour.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
	integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<title>Du Lịch</title>
</head>
<body>
	<div class="header">
		<a href="trangchu.jsp"><img class="header-logo" src="img/logo.png"></a>
		<div class="menu-icon">
			<i class="fa fa-bars" aria-hidden="true"></i>
		</div>
		<nav>
			<ul>
				<li><a href="trangchu.jsp">TRANG CHỦ</a></li>
				<li><a href="gioiThieu.jsp">GIỚI THIỆU</a></li>
				<li><a href="tour.jsp">TOUR</a></li>
				<li><a href="kinhNghiem.jsp">KINH NGHIỆM</a></li>

				<li><a href="lienHe.jsp"> LIÊN HỆ</a></li>
				<li><c:if test="${not empty sessionScope.username}">
						<p class="c-welcome">
							<a href="information.jsp ">Chào mừng,
								${sessionScope.username}!</a>
						</p>
						<form action="logout" method="post">
							<button class="c-logout-btn" type="submit">
								<i class="fa fa-sign-in" aria-hidden="true"></i> Đăng xuất
							</button>
						</form>
					</c:if> <c:if test="${empty sessionScope.username}">
						<button class="login-btn" onclick="location.href='login.jsp'">
							<i class="fa fa-user" aria-hidden="true"></i> Đăng nhập
						</button>
					</c:if></li>

			</ul>
		</nav>
	</div>
	</div>
	<div class="tour">
		<h4>TẤT CẢ CÁC TOUR</h4>
		<div class="container_tour">
			<table>
				<c:forEach var="tour" items="${tours}" varStatus="loopStatus">
					<c:if test="${loopStatus.index % 3 == 0}">
						<tr>
					</c:if>

					<td>
						<div class="box_tour">
							<div class="tour-highlight">

								<img class="anh_tour" width="365" height="296"
									src="img/${tour.imagePaths[0]}">

								<c:if
									test="${tour.id==1 or tour.id==5 or tour.id==7 or tour.id==9}">

									<div class="highlight-text">
										<span class="noi_bat">Nổi Bật</span>
									</div>
								</c:if>
								<a class="ngaydi" href="showTour.jsp?tourId=${tour.id}">${tour.name}</a><br>
								<i class="fa fa-plane icon_tour" aria-hidden="true"></i> <span>${tour.duration }</span><br>
								<i class="fa fa-calendar icon_tour" aria-hidden="true"></i> <span>${tour.schedule }</span><br>
								<p class="price">
									<fmt:setLocale value="vi_VN" />
									<fmt:formatNumber value="${tour.price}" />
									VND
								</p>
								<a class="chi_tiet_tour" href="showTour.jsp?tourId=${tour.id}">XEM
									CHI TIẾT</a>
							</div>
						</div>
					</td>

					<c:if test="${(loopStatus.index + 1) % 3 == 0 or loopStatus.last}">
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
	</div>
	<div class="footer">
		<ul class="list_footer">
			<li class="list1">
				<p class="p1">THÔNG TIN LIÊN HỆ</p>
				<p class="p2">Công ty du lịch LongPhuc</p> <span class="sp1">
					<i class="fa-solid fa-location-dot"></i> 1234 QL1K<br>Thành
					Phố Thủ Đức,HCM.
			</span><br>
				<p class="p3">0123 456 789</p> <a class="a1"
				href="https://mail.google.com/mail"> <i
					class="fa fa-envelope email_icon" aria-hidden="true"></i>
					longphuc@gmail.com
			</a>
			</li>
			<li class="list2">
				<p class="p4">HỖ TRỢ TƯ VẤN</p>
				<p class="p5">HOTLINE 0123 456 789</p> <a
				href="https://www.facebook.com" class="fb_icon">
					<i class="fa-brands fa-facebook" style="color: #1262ed;"></i>
			</a> <a href="https://www.instagram.com" class="ins_icon">
					<i class="fa-brands fa-instagram" style="color: #ec2222;"></i>
			</a> <a href="https://mail.google.com/mail"
				class="mail_icon"> <i class="fa-regular fa-envelope"
					style="color: #db611f;"></i>
			</a> <a href="https://www.tiktok.com"
				class="tiktok_icon"> <i class="fa-brands fa-tiktok"
					style="color: #e3e7ed;"></i>
			</a>
			</li>
			<li class="list3">
				<p class="p6">THÔNG TIN CẦN BIẾT</p> <a class="a2"
				href="trangchu.jsp">Điều kiện điều khoản</a><br> <a class="a3"
				href="trangchu.jsp">Phương thức thanh toán</a><br> <a
				class="a4" href="trangchu.jsp">Bảo mật thông tin khách hàng</a><br>
				<a class="a5" href="trangchu.jsp">Chính sách quy định</a><br>

			</li>
		</ul>

		<div class="text_footer">
			<span class="sp2">Bản quyền thuộc về Du Lịch | Thiết kế bởi </span><span
				class="sp3">VPVL</span>
		</div>
	</div>
</body>
</html>