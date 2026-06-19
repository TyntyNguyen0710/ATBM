<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/checkingTourNoLogin.css">
<title>Checking Tour</title>
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
		<h2>Kiểm tra Tour</h2>

		<form action="SendOTPViaEmailServlet" method="post"
			onsubmit="return validateEmail()">
			<label for="email">Email:</label> <input type="email" id="email"
				name="email" required>

			<div id="email-error"></div>

			<input type="submit" value="Gửi OTP">
		</form>
	</div>

	<script>
        function validateEmail() {
            var emailInput = document.getElementById('email');
            var emailError = document.getElementById('email-error');

            var email = emailInput.value.trim();

            var atPos = email.indexOf('@');
            var dotPos = email.lastIndexOf('.');

            if (atPos < 1 || dotPos < atPos + 2 || dotPos + 2 >= email.length) {
                emailError.innerHTML = 'Email không hợp lệ.';
                return false;
            } else {
                emailError.innerHTML = '';
                return true;
            }
        }
    </script>
</body>

</html>
