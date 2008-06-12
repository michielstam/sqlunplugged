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
		<div id="container"><div id="banner"><br /><B>SQL Interpreter on the Web</B></div>
			<div id="menu_left">
				<div id="menu">
					<table>
	                	<tr class="header_row">
							<td>
								<img src="images/upload-server.png" />
							</td>
	                		<td>
								<h4><u>Uploaded Files:</u></h4>
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
		                
		                <c:if test="${!empty bean.uploadedFiles}">
			               <form name="form5" method="post">
			                	    <c:forEach var="file" items="${bean.uploadedFiles}">
	                                    <tr>
	                                        <td>
	                                        	<input type="radio" name="selected_file" value="${file}">
	                                        </td>
	                                        <td>
	                                            ${file}
	                                        </td>
	                                	</tr>
	                            	</c:forEach>
	                            <tr>
	                            	<td></td>
	                            	<td>
	                            		<input type="image" name="execute" value="Execute" src="images/execute.png" /> 
	                            		<input type="image" name="delete" value="Remove" src="images/delete.png" />
	                           		</td>
	                           	</tr>
	                        </form>
	                        </c:if>
	                        <tr><td colspan="2"></td></tr>
	                        <tr><td colspan="2"><b>${bean.fileStatus}</b></td></tr>
		                
	                 </table>
                <br />
                  	<form name="form1" method="post" enctype="multipart/form-data">
              			<input type="file" name="uploadfile" size="15"/>
                		<input type="submit" name="upload" value="Upload"/>
                	</form>
                <br />
                </div>	
			</div>
			<div id="content">
            	<center>
            	<br />
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
						<td colspan="2" align="center">
							<form name="form2" method="post">
								<textarea name="query" cols="40" rows="4">${bean.query}</textarea>
								
								<br />
								<br />
								<input type="submit" name="run" value="Run" /><input name="reset" type="submit" value="Clear" /><input type="submit" name="logout" value="Logout"/>
							</form>
						</td>
					</tr>
				</table>
				${bean.status}
				<br />
				<hr>
				<br/>
				<table id="results" class="sortable">	
					<c:forEach var="item" items="${bean.queryResultHeaders}">
						<tr>
							<c:forEach var="i" begin="1" end="${bean.columnLength}">
								<th align="center">
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
				<br />
                </center>
        	</div>
        <div id="footnote">
        	&copy;Gemaakt door: Karel van Os, David Ammeraal, Michiel Stam en Karel Manschot
        </div>
     </div>
	</body>
</html>