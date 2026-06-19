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
<link rel="stylesheet" href="css/kinhNgiem_Tour.css">
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
	<div class="gioi_thieu">
		<ul class="phan_than">
			<li class="trai">
				<h2>TOUR-KHÁCH SẠN HOT</h2>
				<ul class="tour">
					<c:forEach var="tour" items="${tours}">
						<li class="tour_detail">
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
					height="50" src="img/kinhNghiemVH.jpg"></li>
				<li class="text"><a href="vinh_hy.jsp">Kinh nghiệm du lịch
						Vĩnh Hy mùa xuân</a></li>
			</ul>
		</li>
		<li class="cau_dat">
			<ul>
				<li class="anh"><img class="anh_kinh_nghiem" width="50"
					height="50" src="img/kinhNghiemVD.jpg" alt="NhaTrang"></li>
				<li class="text"><a href="cau_dat.jsp">Du lịch Đà Lạt đừng
						quên ghé "check-in" đồi chè Cầu Đất </a></li>
			</ul>
		</li>

		</ul>


		</li>
		<!-- bên phải -->
		<li class="phai">

			<h1>Kinh nghiệm đi du lịch Vĩnh Hy mùa Xuân</h1> <span class="day">Posted
				on 02/09/2019 by <a href="#">Kha Ne </a>
		</span>
			<p class="text1">
				Share bài đi!!! Tôi vừa kết thúc chuyến du lịch Vĩnh Hy tự túc
				trong 5 ngày, và giờ mình đang rất háo hức chia sẻ lại những kỷ niệm
				đẹp trong chuyến đi lang thang dọc vùng Bắc của mình.<br>
				<br> Nói về Vĩnh Hy, mình sẽ ví vẻ đẹp của Vĩnh Hy với một cô
				gái vùng sơn cước. Cô gái ấy hồn nhiên, với đôi mắt xanh trong màu
				ngọc bích, đẹp như những dòng thác trong rừng sâu. Còn mái tóc nàng
				trải dài như dòng Mekong tới từ phương Bắc.<br>
				<br> Nàng giấu kín nét đẹp của mình sau những rừng cây xanh bạt
				ngàn nên nàng vẫn còn là điều gì đấy bí ẩn, mới mẻ, lôi cuốn với
				những kẻ yêu thích khám phá. Hiền hòa, giản dị, lặng lẽ, yên bình là
				những gì Việt Pha có thể nói với bạn về Vĩnh Hy. <img
					src="img/kinhNghiemVH.jpg"><br> <span>Bãi biển ở
					Vĩnh Hy</span>
			</p>
			<p class="text2">
				Thông tin xe khách Việt Nam – Luang Prabang (chạy ở Hà Nội) không cố
				định, có xe chạy thứ 2-4-6, có xe chạy ngày 3-5-7, có xe ngày nào
				cũng chạy nên Việt Anh không lấy được thông tin, đành khuyến cáo mọi
				người nên ra bến xe nước ngầm đặt xe trước ngày đi.<br>
				<br> Với những bạn ở xa có thể gọi điện đặt trước qua hãng bus
				VIETTRANS – Hotline: 093 254 1114 – Xuất phát tại Hà Nội (có hai
				điểm đón là phố cổ Hà Nội và bến xe Nước Ngầm) đi qua cửa khẩu Nậm
				Cắn ( Nghệ An) và đến Luang Prabang.<br>
				<br> Giá vé: từ 900,000 – >1,000,000VND/chiều (tùy từng thời
				điểm)<br>
				<br> Lưu ý: nên đặt vé xe trước ngày đi 2-3 ngày tại bến xe
				Nước Ngầm (Hà Nội)<br>
				<br> <span>Đi Luang Prabang từ Hồ Chí Minh</span><br>
				<br> Nếu đi từ Hồ Chí Minh, các bạn có thể:<br>
				<br> (1) bay ra Hà Nội để đi xe khách sang Lào.<br>
				<br> Hoặc (2) đi xe khách lên Kon Tum (cửa khẩu Bờ Y) và bắt xe
				đi Pakse – Vientiane rồi đi tiếp qua Luang Prabang. Đi xe máy hoặc
				xe ô tô riêng qua Luang Prabang<br>
				<br> Mang xe qua Lào rất đơn giản, mình có anh bạn từng mang xe
				máy qua Lào, anh bạn chia sẻ lại “các bạn chỉ cần giấy tờ chính chủ
				và bằng lái xe quốc tế” là mang xe qua được. Ở cửa khẩu người ta
				không làm khó, chỉ cần đóng tiền lệ phí là cho qua.<br>
				<br> Với xe máy các bạn cầm bằng lái xe phổ thông ra cục đường
				bộ ở thành phố mình sống để đổi nhé, khoảng 7-10 ngày là có. Bằng
				lái xe ô tô mình chưa tìm hiểu được.<br>
				<br> Các cửa khẩu Việt Lào bạn có thể đi:<br> Cửa khẩu Tây
				Trang (Điện Biên) – Cửa khẩu Loong Sập (Mộc Châu) – Cửa khẩu Bờ Y
				(Kon Tum) – Cửa khẩu Nậm Cắn (Nghệ An) – Cửa khẩu Cầu Treo (Hà Tĩnh)
				– Của khẩu Cha Leo (Quảng Bình) – Cửa khẩu Lao Bảo (Quảng Trị) – Cửa
				khẩu Na Mèo (Thanh Hóa)<br>
				<br> Lưu ý: Visa thường được chấp nhận ở tất cả cửa khẩu. Visa
				điện tử chỉ được chấp nhận ở 3 cửa khẩu: Nậm Cắn, Cầu Treo, Bờ Y bạn
				nhé!
			</p>
			<div class="text3">
				<span class="tieu_de">Bài viết liên quan</span><br>
				<ul>
					<li><img src="img/kinhNghiemTB.jpg" alt="">
						<p>
							<a href="tay_bac.jsp">Kinh nghiệm checkin Tây Bắc mùa Đông</a>
						</p> <span>Th11,2,2018</span></li>
					<li><img src="img/kinhNghiemDL.jpg" alt="">

						<p>
							<a href="vinh_hy.jsp">Kinh nghiệm du lịch Đà Lạt mùa đông</a>
						</p> <span>Th11,5,2019</span></li>
					<li><img src="img/kinhNghiemSP.jpg" alt="">

						<p>
							<a href="sapa.sjp">Kinh nghiệm checkin Sapa mùa Đông</a>
						</p> <span>Th15,9,2020</span></li>
				</ul>
			</div>
		</li>
	</div>
	<div class="footer">
		<ul class="list_footer">
			<li class="list1">
				<p class="p1">THÔNG TIN LIÊN HỆ</p>
				<p class="p2">Công ty du lịch LongPhuc</p> <span class="sp1">
					<i class="fa-solid fa-location-dot"></i> 1234 QL1K <br>Thành
					Phố Thủ Đức,HCM.
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