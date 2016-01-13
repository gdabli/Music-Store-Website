<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>Register</title>

<!-- Google Fonts -->
<link
	href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700|Lato:400,100,300,700,900'
	rel='stylesheet' type='text/css'>

<link rel="stylesheet" href="css/animate.css">
<!-- Custom Stylesheet -->
<link rel="stylesheet" href="css/style.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>

<body>
	<div class="container">
		<div class="top">

			<h1 id="title" class="hidden">
				<span id="logo">Music Store<span></span></span>
			</h1>
		</div>
		<form:form commandName="user" method="post">

			<div class="login-box animated fadeInUp">
				<div class="box-header">
					<h2>Sign Up</h2>
				</div>
				<label for="firstName">First Name</label> <br />
				<form:input path="firstname" id="firstName" />
				<br /> <span><form:errors path="firstname" 
						style="color:red" /></span> <br /> <label for="lastName">Last
					Name</label> <br />


				<form:input path="lastname" id="lastName" />
				<br /> <span><form:errors path="lastname" style="color:red" /></span><br /> <label for="email">Email</label> <br />
				<form:input path="emailAddress" id="email" />
				<br /> <span><form:errors path="emailAddress" style="color:red" /></span><br /> <br />
				<button type="submit" id="save" value="submit">Sign Up</button>
				<br />
			</div>
		</form:form>
	</div>
</body>

<script>
	$(document).ready(function() {
		$('#logo').addClass('animated fadeInDown');
		$("input:text:visible:first").focus();
	});
	$('#username').focus(function() {
		$('label[for="username"]').addClass('selected');
	});
	$('#username').blur(function() {
		$('label[for="username"]').removeClass('selected');
	});
	$('#password').focus(function() {
		$('label[for="password"]').addClass('selected');
	});
	$('#password').blur(function() {
		$('label[for="password"]').removeClass('selected');
	});
</script>

</html>