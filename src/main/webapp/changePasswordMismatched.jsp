<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/changePasswordMismatched.css">
<title>Đổi mật khẩu</title>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap">
</head>
<body>
	<div class="form-wrapper">
		<h2>Đổi mật khẩu</h2>
		<div class="error-message">Mật khẩu mới không khớp!</div>

		<form action="ChangePasswordAfterLogin" method="post">
			<label for="currentPassword">Nhập mật khẩu cũ:</label> <input
				type="password" id="currentPassword" name="currentPassword" required>

			<label for="newPassword">Nhập mật khẩu mới:</label> <input
				type="password" id="newPassword" name="newPassword" required>

			<label for="confirmPassword">Xác nhận mật khẩu mới:</label> <input
				type="password" id="confirmPassword" name="confirmPassword" required>

			<!-- Nút xác nhận -->
			<input type="submit" value="Confirm">
		</form>
	</div>
</body>
</html>
