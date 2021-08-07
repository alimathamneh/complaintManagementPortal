<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

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
</style>
</head>

<body>

	<h1>Hi ${fname} ${lname}</h1>
	<h6>
		<a href="logout.jsp">Logout</a>
	</h6>

	<table border="1" cellpadding="5" cellspacing="5">
		<tr>
			<th>ID</th>
			<th>Username</th>
			<th>Complaint</th>
			<th>Status</th>
			<th>Result</th>
		</tr>

		<c:forEach var="employee" items="${employeeList}">
			<tr>
				<td>${employee.getId()}</td>
				<td>${employee.getUsername()}</td>
				<td>${employee.getComplaint()}</td>
				<td>${employee.getResult()}</td>
				<td><div>
						<form action="Operations" method="post">
							<input size="100" type="text" name="result"
								placeholder="${employee.getResult()}" required /> <input
								type="hidden" name="comid" value="${employee.getId()}" /><input
								type="hidden" id="submitComp" name="submitComp"
								value="submitComp"><input type="submit" />
						</form>
					</div></td>
			</tr>
		</c:forEach>
	</table>

	<%--For displaying Previous link except for the 1st page --%>
	<c:if test="${currentPage != 1}">
		<td><a href="Operations?page=${currentPage - 1}">Previous</a></td>
	</c:if>

	<%--For displaying Page numbers. 
    The when condition does not display a link for the current page--%>
	<table border="1" cellpadding="5" cellspacing="5">
		<tr>
			<c:forEach begin="1" end="${noOfPages}" var="i">
				<c:choose>
					<c:when test="${currentPage eq i}">
						<td>${i}</td>
					</c:when>
					<c:otherwise>
						<td><a href="Operations?page=${i}">${i}</a></td>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</tr>
	</table>

	<%--For displaying Next link --%>
	<c:if test="${currentPage lt noOfPages}">
		<td><a href="Operations?page=${currentPage + 1}">Next</a></td>
	</c:if>

	<form name="recordNumberForm">
		<label for="cars">Number Of Records</label> <select name="numbers"
			onChange="nav()">
			<option value="" selected="selected"></option>
			<option value="2">2</option>
			<option value="4">4</option>
			<option value="6">6</option>
			<option value="8">8</option>
		</select> <br> <br> <input type="hidden" value="Submit">
	</form>

	<script type="text/javascript">
	
		function nav() {
			var w = document.recordNumberForm.numbers.selectedIndex;
			var url_add = document.recordNumberForm.numbers.options[w].value;
	
			window.location.href = "Operations?numOfRec=" + url_add;
		}
	</script>
</body>
</html>