<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import=" model.Tour"%>
<%@page import=" dao.*"%>
<%@page import=" DataBase.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/gioiThieu.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
	integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<title>Du Lịch</title>
</head>
<body>
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
	<%
	tourDAO td = tourDAO.getIntance();
	ArrayList<Tour> tours = td.selectAll();
	HttpSession Mysession = request.getSession();
	Mysession.setAttribute("tours", tours);
	%>
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
					height="50" src="img/kinhNghiemCD.jpg"></li>
				<li class="text"><a href="cau_dat.jsp">Du lịch Đà Lạt đừng
						quên ghé "check-in" đồi chè Cầu Đất </a></li>
			</ul>
		</li>

		</ul>


		</li>
		<!-- bên phải -->
		<li class="phai"><img src="img/gioiThieu.jpg"><br>
			<h1>Câu chuyện của chúng tôi</h1>
			<p class=" cc1">VP Travel chính là một câu chuyện thành công mà
				chúng tôi muốn chia sẻ với các bạn. VP Travel được thành lập vào năm
				2006 bởi một cặp vợ chồng trẻ từ Ninh Thuận.Việt Pha phụ trách quản
				lý và sản phẩm, trong khi VL nhận trách nhiệm mảng kinh doanh. Mặc
				dù có kinh nghiệm nhiều năm làm việc tại một công ty tour du lịch
				trước đó, , vì nhiều người sẽ nghĩ rằng 27 tuổi là quá trẻ để làm
				chủ một doanh nghiệp.</p> <br>
			<p class="cc2">Cũng giống như nhiều startup khác, những ngày đầu
				tiên chúng tôi đã gặp rất nhiều khó khăn. Văn phòng thì nhỏ, các đơn
				đặt mua đến chậm chạp, chủ yếu từ bạn bè và người thân muốn đặt vé
				và phòng khách sạn.</p>
			<h4>Nhận diện thương hiệu</h4>
			<p class="cc3">Giá trị của chúng tôi là giúp việc kinh doanh của
				công ty tiến hành một cách rất có nguyên tắc. Slogan của chúng tôi
				là: “Experience is true value” trải nghiệm tạo nên giá trị đích
				thực.</p>
			<p class="cc4">Tên VP Travel xuất phát từ thực tế rằng chúng tôi
				sẵn sàng có mặt để phục vụ bạn 24/7, 365 ngày trong 1 năm và là một
				đối tác kinh doanh uy tín, đáng tin cậy với “Sự chăm sóc liên tục”
				và “khiêm tốn” để chuẩn bị cho tương lai và chúng tôi luôn luôn ghi
				nhớ rằng khách hàng và đối thủ cạnh tranh là yếu tố cần thiết để cải
				thiện bản thân doanh nghiệp. “Ngay thẳng” có nghĩa là tính minh bạch
				và trách nhiệm đi đôi với cảm hứng cũng như thách thức trong công
				việc. Sống và làm việc theo những giá trị đặc biệt, chia sẻ đam mê
				và cam kết tiếp tục phấn đấu , sẵn sàng cho những nấc thang cao hơn.
			</p>
			<p class="cc5">Chúng tôi hy vọng sẽ là người bạn đồng hành đáng
				tin cậy của bạn trong tương lai!</p></li>

		</ul>
	</div>
	<div class="footer">
		<ul class="list_footer">
			<li class="list1">
				<p class="p1">THÔNG TIN LIÊN HỆ</p>
				<p class="p2">Công ty du lịch LongPhuc</p> <span class="sp1">
					<i class="fa-solid fa-location-dot"></i> 1234 QL1K<br>Thành
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