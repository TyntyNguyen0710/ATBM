<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/lienHe.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
	integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<title>Du Lịch</title>
</head>
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
				  <li>
                <c:if test="${not empty sessionScope.username}">
   <p class="c-welcome"><a href="information.jsp ">Chào mừng, ${sessionScope.username}!</a></p>
    <form action="logout" method="post">
        <button class="c-logout-btn" type="submit"><i class="fa fa-sign-in" aria-hidden="true"></i> Đăng xuất</button>
    </form>
</c:if>
                <c:if test="${empty sessionScope.username}">
                    <button class="login-btn" onclick="location.href='login.jsp'">
                       <i class="fa fa-user" aria-hidden="true"></i> Đăng nhập
                    </button>
                </c:if>
            </li>

			</ul>
		</nav>
	</div>
	</div>
	<div class="lien_he">
		<ul class="phan_than">
			<li class="trai">
				<h2>TOUR-KHÁCH SẠN HOT</h2>
				<ul class="tour">
					<c:forEach var="tour" items="${tours}">
						<li class="tour_NT">
							<ul>
								<li class="anh"><img class="anh_tour" width="60"
									height="60" src="img/${tour.imagePaths[0]}"></li>
								<li class="text"><a href="showTour.jsp?tourId=${tour.id}"
									class="ten_tour">${tour.name}</a><br> <span class="price1"><s>6.000.000VND</s></span><br>
									<span class="price2"><fmt:setLocale value="vi_VN" /> <fmt:formatNumber
											value="${tour.price}" /> VND</span><br></li>
							</ul>
						</li>
					</c:forEach>
				</ul>
				<h3>BÀI VIẾT MỚI</h3>
				<ul class="kinh_nghiem">
					<li class="tay_bac">
						<ul>
							<li><img class="anh_kinh_nghiem" width="50" height="50"
								src="img/kinhNghiemTB.jpg"></li>
							<li><a href="tay_bac.jsp">Kinh nghiệm phượt Tây Bắc Mùa
									Đông </a></li>
						</ul>
					</li>
					<li class="da_lat">
						<ul>
							<li><img class="anh_kinh_nghiem" width="50" height="50"
								src="img/kinhNghiemDL.jpg"></li>
							<a href="da_lat.jsp">Kinh nghiệm checkin Đà Lạt mùa đông </a>
					</li>
				</ul>
			</li>
			<li class="sapa">
				<ul>
					<li><img class="anh_kinh_nghiem" width="50" height="50"
						src="img/kinhNghiemSP.jpg"></li>
					<a href="sapa.jsp">Kinh nghiệm du lịch tự túc sapa</a>
			</li>
		</ul>
		</li>
		<li class="vinh_hy">
			<ul>
				<li class="anh"><img class="anh_kinh_nghiem" width="50"
					height="50"
					src="img/kinhNghiemVH.jpg"></li>
				<li class="text"><a href="vinh_hy.jsp">Kinh nghiệm du lịch
						Vĩnh Hy mùa xuân</a></li>
			</ul>
		</li>
		<li class="cau_dat">
			<ul>
				<li class="anh"><img class="anh_kinh_nghiem" width="50"
					height="50"
					src="img/kinhNghiemCD.jpg"
					alt="NhaTrang"></li>
				<li class="text"><a href="cau_dat.jsp">Du lịch Đà Lạt đừng
						quên ghé "check-in" đồi chè Cầu Đất </a></li>
			</ul>
		</li>

		</ul>


		</li>
		<!-- o giua -->
		<li class="giua">

			<p class="ten">Công ty Du Lịch LongPhuc</p>
			<p class="dia_chi">Văn phòng tại Hồ Chí Minh</p>
			<p class="dia_chi_cu_the">
				<i class="fa fa-map-marker" aria-hidden="true"></i> 1234, QL1K, TP Thủ Đức, HCM
			</p>
			<p class="phone">
				<i class="fa fa-phone" aria-hidden="true"></i>+84-123-456-789
			</p> <a href="#" class="mail"> <i class="fa fa-envelope email_icon"
				aria-hidden="true"></i> longphuc@gmail.com
		</a><br> <a href="#" class="fb"><i class="fa-brands fa-facebook"
				aria-hidden="true"></i> facebook.com/longphuctravel</a>
			<hr>
			<div class="phan2">
				<p>Văn Phòng Tại TPHCM</p>
				<p>Quản lí: Nguyễn Phi Long</p>
				<p>
					<i class="fa fa-map-marker" aria-hidden="true"></i> 1234, QL1K, Thủ Đức, TP.HCM
				</p>
				<p>
					<i class="fa fa-phone" aria-hidden="true"></i> +84-456-123-789
				</p>
			</div>

		</li>
		<li class="phai">
			<p>
				Để đóng góp ý kiến về chất lượng dịch vụ, cũng như có những yêu cầu,
				thắc mắc cần được giải đáp. Xin vui lòng gửi về mail longphuc@gmail.com.<br> <br> Ngay sau khi nhận được thông
				tin, chúng tôi sẽ phản hồi với bạn trong thời gian sớm nhất.<br>
				<br> Xin chân thành cảm ơn!
			</p>
			
		</li>
	</div>
	<div class="footer">
		<ul class="list_footer">
			<li class="list1">
				<p class="p1">THÔNG TIN LIÊN HỆ</p>
				<p class="p2">Công ty du lịch LongPhuc</p> <span class="sp1">
					<i class="fa-solid fa-location-dot"></i> 1234, QL1K<br>Thành
					Phố Thủ Đức, HCM
			</span><br>
				<p class="p3">0123 456 789</p> <a class="a1"
				href="https://mail.google.com/mail"> <i
					class="fa fa-envelope email_icon" aria-hidden="true"></i>
					longphuc@gmail.com
			</a>
			</li>
			<li class="list2">
				<p class="p4">HỖ TRỢ TƯ VẤN</p>
				<p class="p5">HOTLINE 0399 709 053</p> <a
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