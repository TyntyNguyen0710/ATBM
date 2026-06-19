<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng nhập thất bại</title>
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/regain.css">
</head>
<body>
	<div class="login-container">
		<h1>Đăng nhập</h1>
		<div class="error-message">Đăng nhập thất bại, vui lòng thử lại!</div>
		<form class="login-form" action="login" method="post">
			<div class="form-group">
				<label for="username">Tên đăng nhập:</label> <input type="text"
					id="username" name="username" required>
			</div>
			<div class="form-group">
				<label for="password">Mật khẩu:</label> <input type="password"
					id="password" name="password" required>
			</div>
			<div class="form-group" style="text-align: center">
				<button type="submit">Đăng nhập</button>
			</div>
		</form>
		<div class="create-account">
			<p>
				Bạn chưa có tài khoản? <a href="createAccount.jsp">Tạo tài khoản
					mới</a>
			</p>
			<p>
				<a href="forgetPassword.jsp">Quên mật khẩu?</a>
			</p>
		</div>
		
	</div>
</body>
</html>