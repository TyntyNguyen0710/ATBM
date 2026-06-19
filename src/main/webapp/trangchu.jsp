<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import=" model.Tour"%>
<%@page import=" dao.*"%>
<%@page import=" DataBase.*"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/trangchu.css">


<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
	integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<script src="https://code.jquery.com/jquery-3.7.1.js"
	integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
	crossorigin="anonymous"></script>
<title>Du Lịch</title>
</head>
<script>
	$(document).ready(function() {
		$('.menu-icon').click(function() {
			$('nav').slideToggle();
		});
	})
</script>
<body>
	<div class="header">
		<img class="header-logo" src="img/logo.png">
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
				<li><form action="checkingTour" method="get">
						<input type="submit" value="KIỂM TRA TOUR">
					</form></li>

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
		<!-- Thêm nút đăng nhập dưới đây -->

	</div>
	<div class="img-slider">
		<div class="img-wrap">

			<div class="img">
				<img src="img/NTTC.jpg"> <img src="img/DLTC.jpg"> <img
					src="img/PYTC.jpg"> <img src="img/VHTC.jfif"> <img
					src="img/HATC.jfif">
			</div>
		</div>
	</div>
	<div class="tong_quan">
		<h2>Hãy chọn Travel Go</h2>
		<ul>
			<li>
				<p class="tong_quan-1">1.000 lý do tại sao bạn nên chọn đến với
					chung tôi Travel Go, có 1 thế giới tuyệt đẹp quanh ta hãy đến với
					chúng tôi.</p> <br>
				<p class="tong_quan-2">Với hơn 16 năm kinh nghiệm tổ chức và
					triển khai các tour du lịch trong nước, chúng tôi cam kết đem lại
					cho khách hàng những hành trình tuyệt vời nhất thông qua những dịch
					vụ chuyên nghiệp mà chúng tôi thực hiện như:</p>
				<div class="tong_quan-uu_iem">
					<table>
						<tr>
							<td><i class="fa fa-plane tong_quan-icon "
								aria-hidden="true"></i> <span>Chuyến bay đẳng cấp</span></td>
							<td><i class="fa fa-bicycle tong_quan-icon"
								aria-hidden="true"></i> <span>Hành trình hấp dẫn</span></td>
							<td><i class="fa fa-book tong_quan-icon" aria-hidden="true"></i>
								<span>Quản lý chặt chẽ</span></td>
						</tr>
						<tr>
							<td><span><i class="fa fa-bed tong_quan-icon"
									aria-hidden="true"></i> Khách sạn tiện nghi</span></td>
							<td><span><i
									class="fa fa-sign-language tong_quan-icon" aria-hidden="true"></i>
									Chất lượng đỉnh cao</span></td>
							<td><span><i
									class="fa fa-camera-retro tong_quan-icon" aria-hidden="true"></i>
									Các tour đa dạng</span></td>
						</tr>
					</table>
				</div>

			</li>
			<li><img class="tong_quan-img" src="img/tongquan.png" alt=""></li>
		</ul>
	</div>
	<div class="dia_diem">
		<h3>ĐIỂM ĐẾN HẤP DẪN</h3>
		<br>
		<div class="anh_dia_diem">

			<img class="da_lat" width="740px" height="370px" src="img/daLat.jpg"
				alt="Dalat"> <img class="ha_noi" width="370px" height="370px"
				src="img/hanoi.jpg" alt="hanoi"> <br> <img
				class="ninh_binh" width="370px" height="370px"
				src="img/ninhbinh.jpg" alt="ninhBinh"> <img class="son_la"
				width="370px" height="370px" src="img/sonLa.jpg" alt="sonLa">
			<img class="hue" width="370px" height="370" src="img/Hue.jpg"
				alt="hue">
		</div>
	</div>
	<div class="tour">
		<h4>TOUR NỔI BẬT</h4>
		<%
		tourDAO td = tourDAO.getIntance();
		ArrayList<Tour> tours = td.selectAll();
		HttpSession Mysession = request.getSession();
		Mysession.setAttribute("tours", tours);
		%>
		<table>
			<c:forEach var="tour" items="${tours}" varStatus="loopStatus">

				<c:if
					test="${tour.id==1 or tour.id==2 or tour.id==3 or tour.id==4
									or tour.id==5 or tour.id==6}">
					<c:if test="${loopStatus.index % 3 == 0}">
						<tr>
					</c:if>
					<tr>
						<td>
							<div class="box_tour">
								<div class="tour-highlight">

									<img class="anh_tour" width="365" height="296"
										src="img/${tour.imagePaths[0]}"> <a class="ngaydi"
										href="showTour.jsp?tourId=${tour.id}">${tour.name}</a><br>
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
					</tr>
					<c:if test="${(loopStatus.index + 1) % 3 == 0 or loopStatus.last}">
					</c:if>
				</c:if>
			</c:forEach>
		</table>

	</div>
	<div class="cam_nang_du_lich">
		<h5>CẨM NANG DU LỊCH</h5>
		<ul class="cam_nang">
			<li class="tay_bac"><img src="img/kinhNghiemTB.jpg"><br>
				<a href="tay_bac.jsp" class="text">Kinh nghiệm phượt Tây Bắc mùa
					đông</a><br> <span class="ngay">02/11/2019</span><br>
				<p>Share bài đi!!Tôi vừa kết thúc chuyến du lịch Mộc Châu trong
					5 ngày và đang rất háo hức chia sẽ lại những kỉ niệm đẹp trong
					chuyến đi đó...</p></li>
			<li>
				<ul class="DL-SP-NT">
					<li class="da_lat">
						<ul>
							<li class="anh"><img width="168" height="124"
								src="img/kinhNghiemDL.jpg"></li>
							<li><a href="da_lat.jsp" class="textn">Kinh nghiệm đà
									lạt mùa đông</a><br> <span class="ngayn">02/11/2019</span><br>
								<p>Share bài đi!!Tôi vừa kết thúc chuyến du lịch Đà Lạt
									trong 5 ngày...</p></li>
						</ul>
					</li>
					<li class="sapa">
						<ul>
							<li class="anh"><img width="168" height="124"
								src="img/kinhNghiemSP.jpg"></li>
							<li><a href="sapa.jsp" class="textn">Kinh nghiệm du lịch
									tự túc SaPa</a><br> <span class="ngayn">02/11/2019</span><br>
								<p>Share bài đi!!Tôi vừa kết thúc chuyến du lịch SaPa trong
									5 ngày...</p></li>
						</ul>
					</li>
					<li class="vinh_hy">
						<ul>
							<li class="anh"><img width="168" height="124"
								src="img/kinhNghiemVH.jpg"></li>
							<li><a href="vinh_hy.jsp" class="textn">Kinh nghiệm Vĩnh
									Huy mùa xuân</a><br> <span class="ngayn">02/11/2019</span><br>
								<p>Share bài đi!!Tôi vừa kết thúc chuyến đi Vĩnh Huy trong 4
									ngày...</p></li>
						</ul>

					</li>
				</ul>
			</li>
		</ul>
	</div>
	<div class="footer">
		<ul class="list_footer">
			<li class="list1">
				<p class="p1">THÔNG TIN LIÊN HỆ</p>
				<p class="p2">Công ty du lịch LongPhuc</p> <span class="sp1">
					<i class="fa-solid fa-location-dot"></i> 1234, QL1K<br>Thành
					Phố Thủ Đức,HCM
			</span><br>
				<p class="p3">0123 456 789</p> <a class="a1"
				href="https://mail.google.com"> <i
					class="fa fa-envelope email_icon" aria-hidden="true"></i>
					longphuc@gmail.com
			</a>
			</li>
			<li class="list2">
				<p class="p4">HỖ TRỢ TƯ VẤN</p>
				<p class="p5">HOTLINE 0123 456 789</p> <a
				href="https://www.facebook.com" class="fb_icon"> <i
					class="fa-brands fa-facebook" style="color: #1262ed;"></i>
			</a> <a href="https://www.facebook.com" class="ins_icon"> <i
					class="fa-brands fa-instagram" style="color: #ec2222;"></i>
			</a> <a href="https://mail.google.com" class="mail_icon"> <i
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
			<span class="sp2">Bản quyền thuộc về Du Lịch | Thiết kế bởi </span>
		</div>
	</div>
</body>
</html>