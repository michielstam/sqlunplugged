<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>SQL Interpreter on the Web</title>
	</head>

	<body>
		<center>
			<h3>SQL Interpreter on the Web</h3>
			Please enter your SQL statement:
			<br />
			<br />
			<form name="form1" method="post">
				<textarea name="query" cols="40" rows="4">${bean.query}</textarea>
				<br />
				${bean.status}
				<br />
				<br />
				<input type="submit" name="execute" value="OK"><input name="reset" type="submit" value="Clear"><input type="submit" name="logout" value="Logout">
			</form>
			<br />
			<hr />
			<br />
			<table border="1">	
				<c:forEach var="item" items="${bean.queryResultHeaders}">
					<tr>
						<c:forEach var="i" begin="1" end="${bean.columnLength}">
							<th>
								${item[i]}
							</th>	
						</c:forEach>
					</tr>
				</c:forEach>
				
				<c:forEach var="item" items="${bean.queryResultData}">
					<tr>
						<c:forEach var="i" begin="1" end="${bean.columnLength}">
							<td>
								${item[i]}
							</td>	
						</c:forEach>
					</tr>
				</c:forEach>
			</table>	
		</center>
	</body>
</html>