<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style>
html, body {
	overflow-x: hidden !important;
	/*font-family: 'Source Sans Pro', sans-serif;*/
	font-family: 'Lato', Serif;
	-webkit-font-smoothing: antialiased;
	min-height: 100%;
	background: #f1f2f7;
}

table {
	font-family: Serif, sans-serif;
	border-collapse: collapse;
	width: 100%;
}

td, th, input {
	border: 2px solid #dddddd;
	text-align: center;
	padding: 10px;
	width: calc(100% - 240px);
}

tr:nth-child(even) {
	background-color: #dddddd;
}

.center {
	margin: center;
	width: 100%;
	padding: 100px;
}

li, a {
	display: block;
	color: white;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
}

ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: hidden;
	background-color: #333;
}

li a:hover {
	background-color: #111;
}
</style>
</head>

<body>

	<ul>
		<li>Hi ${fname} ${lname}</li>
		<li><a href="logout.jsp">Logout</a></li>
	</ul>

<%
		String profile_msg = (String) request.getAttribute("profile_msg");
	if (profile_msg != null) {
		out.print(profile_msg);
	}
	%>

	<c:if test="${admin == 0}">
		<h4>USER PORTAL</h4>
		<table>
			<tr>
				<th>Complaint</th>
				<th>Status</th>
				<th>Result</th>
			</tr>
			<c:forEach items="${complaintList}" var="complaintData">
				<tr class="nav-item" style="cursor: pointer;">
					<td><c:out value="${complaintData.getComplaint()}" /></td>
					<td><c:out value="${complaintData.getStatus()}" /></td>
					<td><c:out value="${complaintData.getResult()}" /></td>
				</tr>
			</c:forEach>
		</table>
		<div class="center">
			<form action="Operations" method="post">
				<input size="100" type="text" name="result"
					placeholder="Add new Complaint" required /> <input type="hidden"
					name="comid" value="newUserComp" /><input type="hidden"
					id="userComp" name="userComp" value="userComp"><input
					type="submit" />
			</form>
		</div>



	</c:if>
</body>
</html>
