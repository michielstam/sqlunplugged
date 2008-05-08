<%-- Instantiate sqlInterpreter Bean --%>
<jsp:useBean id="myBean" class="sqlInterpreter.sqlInterpreter">
</jsp:useBean>
<%-- Set Bean Properties --%>
<jsp:setProperty name="myBean" property="userName" value="book"/>
<jsp:setProperty name="myBean" property="password" value="book"/>
<jsp:setProperty name="myBean" property="connectionUrl"
value="jdbc:oracle:thin:@tinman.cs.gsu.edu:1521:sid9ir2"/>
<jsp:setProperty name="myBean" property="sql"/>
<%-- Define jspInit to load JDBC Driver --%>
<%!
  public void jspInit() {
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
    } catch (ClassNotFoundException e) {
      System.out.println(e.toString());
    }
  }
%>
<%-- Start HTML Code --%>
<html>
<head>
<title>SQL Interpreter on the Web</title>
</head>
<body>
<h3>SQL Interpreter on the Web</h3>
Please enter your SQL statement:
<!-- Include HTML Form with textarea element -->
<form method=post>
<textarea name=sql cols=40 rows=4>
</textarea>
<br>
<input type=submit>
</form>
<hr>
<!-- Invoke getQueryResult() on bean -->
<jsp:getProperty name="myBean" property="sql"/>
<%= myBean.getQueryResult() %>
</body>
</html>
