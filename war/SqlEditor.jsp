<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link rel="stylesheet" href="stylesheet.css" type="text/css" media="screen" />
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>SQL Interpreter on the Web</title>
	</head>

	<body>
		<div id="container">
			<div id="banner">
					<h3>SQL Interpreter on the Web</h3>
			</div>
			<div id="menu_left">
				<table>
                	<tr>
                		<td width="25">
                			Uploaded Files:
                		</td>
                		<td>
                		</td>
                		<td>
                		</td>
                	</tr>
                	<tr>
                		<td width="25">
                			&nbsp;
               			</td>
                		<td>
                		</td>
                		<td>
                		</td>
                	</tr>
                	<c:if test="${empty bean.uploadedFiles}">
	                	<tr>
	                		<td width="25">
                				No files
	                		</td>
	                		<td>
	                		</td>
	                		<td>
	                		</td>
	                	</tr>
	                </c:if>                	
                	
	                	<c:forEach var="file" items="${bean.uploadedFiles}">
	                		<form name="form0" method="post">
							<tr>
		                		<td width="25">
		                			${file}
		                		</td>
		                		<td>
		                			<input type="submit" name="execute" value="Execute"/>
		                			<input type="hidden" name="selected_file" value="${file}" />
		                		</td>
		                		<td>
		                			<input type="submit" name="delete" value="Remove"/>
		                			
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
			<div id="content">
            <br />
					Please enter your SQL statement:
					<br />
					<br />
                    <center>
					<form name="form2" method="post">
						<textarea name="query" cols="40" rows="4">${bean.query}</textarea>
						
						<br />
						<br />
						<input type="submit" name="run" value="Run" /><input name="reset" type="submit" value="Clear" /><input type="submit" name="logout" value="Logout"/>
					</form>
					<br />
						${bean.status}
					<br />
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
                <br/>
        	</div>
        <div id="footnote">
        	&copy;Gemaakt door: Karel van Os, David Ammeraal, Michiel Stam en Karel Manschot
        </div>
	</body>
</html>