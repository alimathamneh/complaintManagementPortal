<%@ include file="index.jsp"%>
<hr />
<html>
<head>
<style>
body {
	background-color: lightblue;
}

input {
	background-color: #eee;
	border: none;
	padding: 12px 15px;
	margin: 8px 0;
	width: 100%;
}

h1 {
	font-weight: bold;
	margin: 0;
}
</style>
</head>
<body>
	<h3>Login Form</h3>
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
		Username:<input type="text" name="username" placeholder="Username"
			required /><br /> <br /> Password:<input type="password"
			name="password" placeholder="Password" required /><br /> <br /> <input
			type="hidden" id="olduser" name="olduser" value="olduser"> <input
			type="submit" value="Login" />
	</form>
</body>
</html>