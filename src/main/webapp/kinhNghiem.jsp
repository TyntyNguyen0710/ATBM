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
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/kinhNghiem.css">
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
	<div class="noi_dung">
		<table>
			<tr>
				<td>
					<div class="tay_bac">
						<img width="360px" height="196px" src="img/kinhNghiemTB.jpg"><br>
						<a href="tay_bac.jsp" class="textn">Kinh nghiệm đi Tây Bắc mùa
							đông</a><br> <span class="ngayn">02/11/2019</span><br>
						<p>Share bài đi!! Tôi vừa kết thúc chuyến du lịch Mộc Châu
							trong 5 ngày...</p>
					</div>
				</td>
				<td>
					<div class="da_lat">
						<img width="360px" height="196px" src="img/kinhNghiemDL.jpg">

						<a href="da_lat.jsp" class="textn">Kinh nghiệm đà lạt mùa đông</a><br>
						<span class="ngayn">02/11/2020</span><br>
						<p>Share bài đi!! Tôi vừa kết thúc chuyến du lịch Đà Lạt
							trong 5 ngày...</p>
					</div>
				</td>
				<td><div class="sapa">
						<img width="360px" height="196px" src="img/kinhNghiemSP.jpg">

						<a href="sapa.jsp" class="textn">Kinh nghiệm du lịch tự túc
							SaPa</a><br> <span class="ngayn">02/11/2021</span><br>
						<p>Share bài đi! Tôi vừa kết thúc chuyến du lịch SaPa
							trong 5 ngày...</p>
					</div></td>
			</tr>
			<tr>
				<td>
					<div class="vinh_hy">
						<img width="360px" height="196px" src="img/kinhNghiemVH.jpg"><br>

						<a href="vinh_hy.jsp" class="textn">Kinh nghiệm du lịch Vịnh
							Vĩnh Hy</a><br> <span class="ngayn">02/01/2022</span><br>
						<p>Share bài đi!!Tôi vừa kết thúc chuyến du lịch Vĩnh Hy
							trong 5 ngày...</p>
					</div>
				</td>
				<td>
					<div class="cau_dat">
						<img width="360px" height="196px" src="img/kinhNghiemCD.jpg">

						<a href="cau_dat.jsp" class="textn">Kinh nghiệm du lịch cầu
							đất</a><br> <span class="ngayn">02/03/2023</span><br>
						<p>Share bài đi!!Tôi vừa kết thúc chuyến du lịch vườn chè
							cầu đất...</p>
				</td>
			</tr>
		</table>
	</div>
	<div class="footer">
		<ul class="list_footer">
			<li class="list1">
				<p class="p1">THÔNG TIN LIÊN HỆ</p>
				<p class="p2">Công ty du lịch LongPhuc</p> <span class="sp1">
					<i class="fa-solid fa-location-dot"></i> 1234 QL1k<br>Thành
					Phố Thủ Đức,HCM
			</span><br>
				<p class="p3">0123 456 789</p> <a class="a1"
				href="https://mail.google.com/mail/u/1/#inbox?compose=new"> <i
					class="fa fa-envelope email_icon" aria-hidden="true"></i>
					longphuc@gmail.com
			</a>
			</li>
			<li class="list2">
				<p class="p4">HỖ TRỢ TƯ VẤN</p>
				<p class="p5">HOTLINE 0123 456 789</p> <a
				href="https://www.facebook.com" class="fb_icon">
					<i class="fa-brands fa-facebook" style="color: #1262ed;"></i>
			</a> <a href="https://www.instagram.com2" class="ins_icon">
					<i class="fa-brands fa-instagram" style="color: #ec2222;"></i>
			</a> <a href="https://mail.google.com/mail
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