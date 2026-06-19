<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/createAccount.css">
<title>Đăng ký</title>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap">
</head>
<body>
	<main>

		<form action="CreateAccountServlet" method="post"
			onsubmit="return IsInvalidEmail(this.email.value);">
			<label for="fullName">Họ và tên:</label> <input type="text"
				id="fullName" name="fullName" required> <label for="address">Địa
				chỉ:</label> <input type="text" id="address" name="address" required>

			<label for="phone">Số điện thoại:</label> <input type="text"
				id="phone" name="phone" required> <label for="email">Email:</label>
			<input type="email" id="email" name="email" required> <label
				for="username">Tên đăng nhập:</label> <input type="text"
				id="username" name="username" required> <label
				for="password">Mật khẩu:</label> <input type="password"
				id="password" name="password" required> <input type="submit"
				value="Xác nhận">
		</form>
	</main>

	<script>
		function IsInvalidEmail(the_email) {
			var at = the_email.indexOf("@");
			var dot = the_email.lastIndexOf(".");
			var space = the_email.indexOf(" ");

			if ((at != -1) && (at != 0) && (dot != -1) && (dot > at + 1)
					&& (dot < the_email.length - 1) && (space == -1)) {
				return true;
			} else {
				alert("Email không hợp lệ");
				return false;
			}
		}
	</script>
</body>
</html>
