<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/forgetPasswordRegain.css">
<title>Quên Mật Khẩu</title>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap">
</head>
<body>
	<header>
		<a href="trangchu.jsp"> <img id="logo" src="img/logo.png"
			alt="Logo">
		</a>
	</header>
	<div class="form-wrapper">
		<h2>Quên mật khẩu</h2>

		<div class="error-message">Tên đăng nhập không tồn tại!</div>
		<form action="SendOTPViaForgetPasswordServlet" method="post"
			onsubmit="return validateUsername()">
			<label for="username">Tên đăng nhập:</label> <input type="text"
				id="username" name="username" required> <input type="submit"
				value="Gửi OTP">
		</form>
	</div>

	<script>
		function validateUsername() {
			var usernameInput = document.getElementById('username');
			var emailError = document.getElementById('email-error');

			var username = usernameInput.value.trim();

			if (username.length === 0) {
				emailError.innerHTML = 'Vui lòng nhập tên đăng nhập.';
				return false;
			} else {
				emailError.innerHTML = '';
				return true;
			}
		}
	</script>
</body>
</html>


