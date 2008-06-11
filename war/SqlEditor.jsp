<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<script type="text/javascript" src="js/sortable.js"></script>
		<link rel="stylesheet" href="stylesheet.css" type="text/css" media="screen" />
		<title>SQL Interpreter on the Web</title>
	</head>

	<body>
		<div id="container">
			<div id="banner">
					<h3>SQL Interpreter on the Web</h3>
			</div>
			<div id="menu_left">
				<div id="menu">
					<table>
	                	<tr class="header_row">
							<td>
								<img src="images/upload-server.png" />
							</td>
	                		<td>
								<u>Uploaded Files:</u>
	                		</td>
	                	</tr>
	                </table>
					<table>
	                	<c:if test="${empty bean.uploadedFiles}">
		                	<tr>
		                		<td width>
	                				No files
		                		</td>
		                	</tr>
		                </c:if>  
		                	<c:forEach var="file" items="${bean.uploadedFiles}">
		                		<form name="form0" method="post">
								<tr>
									<td>
										<img src="images/file.png" width="20px" height="25px" />
									</td>
			                		<td width>
			                			${file}
			                		</td>
			                		<td>
			                			<input type="image" name="execute" value="Execute" src="images/execute.png" />
										<input type="hidden" name="selected_file" value="${file}" />
			                		</td>
			                		<td>
			                			<input type="image" name="delete" value="Remove" src="images/delete.png" />
			                		</td>
		                		</tr>
		                		</form>
							</c:forEach>
	                </table>
	                <br />
	                <form name="form1" method="post" enctype="multipart/form-data">
	                	<input type="file" name="uploadfile" size="15"/><input type="submit" name="upload" value="Upload"/>
	                </form>
	                <br />
	                <br />
	                	${bean.fileStatus}
	                <br />
	                <br />	
				</div>
			</div>
			<div id="content">
				<table>
					<tr>
						<td>
							<img src="images/query.png" />
						</td>
						<td>
							Please enter your SQL statement:
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<form name="form2" method="post">
								<textarea name="query" cols="40" rows="4">${bean.query}</textarea>
								
								<br />
								<br />
								<input type="submit" name="run" value="Run" /><input name="reset" type="submit" value="Clear" /><input type="submit" name="logout" value="Logout"/>
							</form>
						</td>
					</tr>
				</table>
				<hr>
				<table id="results" class="sortable">	
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
        	</div>
        <div id="footnote">
        	&copy;Gemaakt door: Karel van Os, David Ammeraal, Michiel Stam en Karel Manschot
        </div>
	</body>
</html>