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
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/showTour.css">
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

	<div class="noi_dung">
		<ul>
			<c:forEach var="tour" items="${tours}">
				<c:if test="${tour.id eq param.tourId}">
					<li class="trai">
						<div class="container">
							<div class="main">
								<img class="img-main" src="img/${tour.imagePaths[0]}">
								<div class="control prev"></div>
								<div class="control next">></div>
							</div>
							<div class="list-img">
								<div>
									<img src="img/${tour.imagePaths[1]}">
								</div>
								<div>
									<img src="img/${tour.imagePaths[2]}">
								</div>
								<div>
									<img src="img/${tour.imagePaths[3]}">
								</div>
								<div>
									<img src="img/${tour.imagePaths[4]}">
								</div>
								<div>
									<img src="img/${tour.imagePaths[5]}">
								</div>

							</div>
						</div>
					</li>
					<!-- phai -->
					<li class="phai">
						<h1>Gọi để tư vấn</h1>
						<table>
							<tr>
								<td>Ngày khởi hành</td>
								<td>${tour.departure}</td>
							</tr>
							<tr>
								<td>Thời gian</td>
								<td>${tour.duration}</td>
							</tr>
							<tr>
								<td>Lịch trình</td>
								<td>${tour.schedule}</td>
							</tr>
							<tr>
								<td>Phương tiện</td>
								<td>${tour.transport}</td>
							</tr>
						</table>
						<p class="price1">
							<fmt:setLocale value="vi_VN" />
							<fmt:formatNumber value="${tour.price}" />
							VND
						</p>
						<div class="button">
							<c:set var="username" value="${sessionScope.username}" />

							<div class="button">
								<form id="bookingForm" action="optionBookingTour" method="get">
									<c:set var="tourID" value="${tour.id}" scope="session" />
									<input type="submit" value="ĐẶT TOUR NGAY"
										onclick="return checkLogin('${sessionScope.username}', '${tour.id}')">
								</form>
							</div>

						</div>
						<p class="lh">Liên hệ càng sớm-Giá càng rẻ cả nhà ơi!</p>
						<p class="hotline">
							<span>0123.456.789 </span>
						</p>

					</li>
				</c:if>
			</c:forEach>
		</ul>
	</div>
	<div class="tour">
		<h4>TẤT CẢ CÁC TOUR</h4>
		<div class="container_tour">
			<table>

				<c:forEach var="tour" items="${tours}" varStatus="loopStatus">
					<c:if test="${tour.id != param.tourId}">
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

						</c:if>
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
					<i class="fa-solid fa-location-dot"></i> 1234, QL1K, <br>Thành
					Phố Thủ Đức, HCM.
			</span><br>
				<p class="p3">0399 790 053</p> <a class="a1"
				href="https://mail.google.com/mail/u/1/#inbox?compose=new"> <i
					class="fa fa-envelope email_icon" aria-hidden="true"></i>
					longphuc@gmail.com
			</a>
			</li>
			<li class="list2">
				<p class="p4">HỖ TRỢ TƯ VẤN</p>
				<p class="p5">HOTLINE 0123 456 789</p> <a
				href="https://www.facebook.com" class="fb_icon"> <i
					class="fa-brands fa-facebook" style="color: #1262ed;"></i>
			</a> <a href="https://www.instagram.com" class="ins_icon"> <i
					class="fa-brands fa-instagram" style="color: #ec2222;"></i>
			</a> <a href="https://mail.google.com/mail" class="mail_icon"> <i
					class="fa-regular fa-envelope" style="color: #db611f;"></i>
			</a> <a href="https://www.tiktok.com" class="tiktok_icon"> <i
					class="fa-brands fa-tiktok" style="color: #e3e7ed;"></i>
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
<script src="app.js"></script>
<script>
	document
			.getElementById('bookingForm')
			.addEventListener(
					'submit',
					function(event) {
						// Prevent the default form submission
						event.preventDefault();

						// Check if the username is not empty
						var username = "${username}";
						if (username.trim() == "") {
							// Redirect to the bookingTourAfterLogin page
							window.location.href = "bookingTourAfterLogin.jsp?tourId=${tour.id}";
						} else {
							// Proceed with the regular form submission
							this.submit();
						}
					});
	function checkLogin(username, tourId) {
		if (!username || username.trim() === "") {
			alert("Vui lòng đăng nhập để đặt tour!");
			// Chuyển hướng thẳng đến trang đăng nhập
			window.location.href = "login.jsp?redirectTourId=" + tourId;
			return false; // Ngăn form submit
		}
		return true; // Cho phép submit bình thường
	}
</script>
</html>