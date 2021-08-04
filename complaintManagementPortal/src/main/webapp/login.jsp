<%@ include file="index.jsp"%>
<hr />
<html>
<head>
<style>
body {
	margin: 0;
	width: 100vw;
	height: 100vh;
	background: #ecf0f3;
	display: flex;
	align-items: center;
	text-align: center;
	justify-content: center;
	place-items: center;
	overflow: hidden;
	font-family: poppins;
}

input {
	background: #ecf0f3;
	padding: 10px;
	padding-left: 20px;
	height: 50px;
	font-size: 14px;
	border-radius: 50px;
	box-shadow: inset 6px 6px 6px #cbced1, inset -6px -6px 6px white;
}

label {
	margin-bottom: 4px;
}

.inputs {
	text-align: center;
	margin-top: 30px;
}

h1 {
	font-weight: bold;
	margin: 0;
}

.btitle {
	margin-top: 10px;
	font-weight: 900;
	font-size: 1.8rem;
	color: #FF4B2B;
	letter-spacing: 1px;
}

.container {
	position: relative;
	width: 350px;
	height: 500px;
	border-radius: 20px;
	padding: 40px;
	box-sizing: border-box;
	background: #ecf0f3;
	box-shadow: 14px 14px 20px #cbced1, -14px -14px 20px #888888;
}

.button {
	border-radius: 20px;
	border: 0px solid #FF4B2B;
	background-color: #FF4B2B;
	color: #FFFFFF;
	font-size: 12px;
	font-weight: bold;
	padding: 12px 45px;
	letter-spacing: 1px;
	text-transform: uppercase;
	transition: transform 80ms ease-in;
}
</style>
</head>
<body>

	<div class="container">
		<h3 class="btitle">Login Form</h3>
		<%
			String profile_msg = (String) request.getAttribute("profile_msg");
		if (profile_msg != null) {
			out.print(profile_msg);
		}
		String login_msg = (String) request.getAttribute("login_msg");
		if (login_msg != null) {
			out.print(login_msg);
		}

		if (request.getAttribute("new") != null) {
			out.println("Thank you " + request.getAttribute("new") + ", you can login now!");
		}

		request.setAttribute("login", "1");
		%>
		<br />
		<form action="Operations" method="post">
			<div class="inputs">
				<label>Username</label> <input type="text" name="username"
					placeholder="Username" required /><br>
				<br> <label>Password</label><br> <input type="password"
					name="password" placeholder="Password" required /><br /> <br />
				<input type="hidden" id="olduser" name="olduser" value="olduser">
				<input class="button" type="submit" value="Login" />
			</div>
		</form>

	</div>
</body>
</html>