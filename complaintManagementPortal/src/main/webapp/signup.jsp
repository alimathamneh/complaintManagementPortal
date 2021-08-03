<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
body {
	background-color: lightblue;
	text-align: center;
}

input {
	background-color: #eee;
	border: none;
	padding: 12px 15px;
	margin: 8px 0;
	width: 100%;
	text-align: center;
}

form {
	display: inline-block;
}
</style>
<title>Registration Form</title>
</head>
<body>
	<h1>Register Form</h1>

	<%
		if (request.getAttribute("username") != null) {
		out.println("\'" + request.getAttribute("username") + "\' is not available! please use another username!");
	}
	%>
	<form action="Operations" method="post">
		<table style="with: 50%">
			<tr>
				<td>First Name</td>
				<td><input type="text" name="first_name" required /></td>
			</tr>
			<tr>
				<td>Last Name</td>
				<td><input type="text" name="last_name" required /></td>
			</tr>
			<tr>
				<td>UserName</td>
				<td><input type="text" name="username" required /></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="password" required /></td>
			</tr>
		</table>
		<input type="hidden" name="signup" value="newuser" /> <input
			type="submit" value="Submit" />
	</form>
	<br>
</body>
</html>