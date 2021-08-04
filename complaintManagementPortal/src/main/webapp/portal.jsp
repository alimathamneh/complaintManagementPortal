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

td, th,input {
	border: 2px solid #dddddd;
	text-align: center;
	padding: 10px;
	width: calc(100% - 240px);
}

tr:nth-child(even) {
	background-color: #dddddd;
}
</style>
</head>

<body>
	<h1>Hi ${fname} ${lname}</h1>
	<h6><a href="logout.jsp">Logout</a></h6>

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
	</c:if>

	<c:if test="${admin == 1}">
		<h4>ADMIN PORTAL</h4>
		<table>
			<tr>
				<th>ID</th>
				<th>Username</th>
				<th>Complaint</th>
				<th>Status</th>
				<th>Result</th>
			</tr>
			<c:forEach items="${complaintList}" var="complaintData">
				<tr class="nav-item" style="cursor: pointer;">
					<td><c:out value="${complaintData.getId()}" /></td>
					<td><c:out value="${complaintData.getUsername()}" /></td>
					<td><c:out value="${complaintData.getComplaint()}" /></td>
					<td><c:out value="${complaintData.getStatus()}" /></td>
					<td><div><form action="Operations" method="post">
							<input size="100" type="text" name="result"
								placeholder="${complaintData.getResult()}" required /> <input
								type="hidden" name="comid" value="${complaintData.getId()}" /><input
								type="hidden" id="submitComp" name="submitComp"
								value="submitComp"><input type="submit" />
						</form></div></td>
				</tr>
			</c:forEach>

		</table>
	</c:if>
</body>
</html>
