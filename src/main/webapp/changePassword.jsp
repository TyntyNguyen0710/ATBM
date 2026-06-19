<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/changePassword.css">
<title>Đổi mật khẩu</title>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap">
</head>
<body>
	<div class="form-wrapper">
		<h2>Mật khẩu</h2>

		<form action="ChangePassword" method="post">
			<label for="Password">Nhập mật khẩu mới:</label> <input
				type="password" id="newPassword" name="newPassword" required>

			<!-- Nút xác nhận -->
			<input type="submit" value="Confirm">
		</form>
	</div>
</body>
</html>

