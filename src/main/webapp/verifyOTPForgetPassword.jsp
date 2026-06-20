
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/verifyOTPForgetPassword.css">
<title>Xác nhận OTP</title>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap">
</head>
<body>
	<div class="form-wrapper">
		<h2>Xác nhận OTP</h2>

		<form action="VerifyOTPForgetPasswordServlet" method="post">
			<label for="otp">Nhập OTP:</label> <input type="text"
				id="otpForgetPassword" name="otpForgetPassword" pattern="\d{4,4}"
				required>

			<!-- Nút xác nhận -->
			<input type="submit" value="Xác nhận">
		</form>
	</div>
</body>
</html>

