<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<jsp:useBean id="key" class="sqlunplugged.beans.SqlEditorBean" scope="session"/>
<%@ taglib uri="/Taglib" prefix="sql"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>SQL Interpreter on the Web</title>
</head>
<body>
<center>
<h3>SQL Interpreter on the Web</h3>
Please enter your SQL statement:
<form method=post>
	<textarea name="query" cols=40 rows=4 >${key.query}</textarea>
	<br>
	<input type="submit" value="OK" />
</form>
<hr />
<jsp:getProperty name="key" property="columnLength"/>
<table>	
<sql:arrayListTag var="next" arrayList="${key.queryResultHeaders}">
	<tr>
		<sql:forEachTag var="item" collection="${next}">
			<td>${item}</td>
		</sql:forEachTag>
	</tr>
</sql:arrayListTag>
<sql:arrayListTag var="next" arrayList="${key.queryResultData}">
<tr>
<td>${next[1]}</td>
<td>${next[2]}</td>
<td>${next[3]}</td>
<td>${next[4]}</td>
</tr>
</sql:arrayListTag>
</table>	
	
</center>
</body>
</html>