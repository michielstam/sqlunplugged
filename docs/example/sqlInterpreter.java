package sqlInterpreter;

import java.sql.*;

public class sqlInterpreter {

  private String sql = "";
  private String userName = "";
  private String password = "";
  private String connectionUrl;
  private int columnCount= 0;

  public void setSql(String sql) {
    if (sql!=null)
      this.sql = sql;
  }

  public String getSql() {
    return sql;
  }

  public void setUserName(String userName) {
    if (userName!=null)
      this.userName = userName;
  }

  public String getUserName() {
    return encodeHtmlTag(userName);
  }

  public void setPassword(String password) {
    if (password!=null)
      this.password = password;
  }

  public String getPassword() {
    return encodeHtmlTag(password);
  }

  public void setConnectionUrl(String url) {
    connectionUrl = url;
  }

  public String getQueryResult() {
    if (sql==null || sql.equals(""))
      return "";
    StringBuffer result = new StringBuffer(1024);
    try {
      Connection con = DriverManager.getConnection(
           connectionUrl, userName, password);
      Statement s = con.createStatement();
      if (sql.trim().toUpperCase().startsWith("SELECT")) {
        result.append("<table border=1>");
        ResultSet rs = null;
        try{
          rs = s.executeQuery(sql);
	} catch(SQLException ee) {
	    result.append("<br>Not well formed query<br>");
	  }
        ResultSetMetaData rsmd = rs.getMetaData();
        // Write table headings
        columnCount = rsmd.getColumnCount();
        result.append("<tr>");
        for (int i=1; i<=columnCount; i++) {
          result.append("<th>" + rsmd.getColumnName(i) +
                        "</th>\n");
        }
        result.append("</tr>");
        while (rs.next()) {
          result.append("<tr>");
          for (int i=1; i<=columnCount; i++) {
            result.append("<td>" +
                 encodeHtmlTag(rs.getString(i)) + "</td>" );
          }
          result.append("</tr>");
        }
        rs.close();
        result.append("</table>");
      }
      else {
        int i = s.executeUpdate(sql);
        result.append("Record(s) affected: " + i);
      }

      s.close();
      con.close();
      result.append("</table>");
    }
    catch (SQLException e) {
      result.append("<B>Error</B>");
      result.append(sql + "<BR>");
      result.append(e.toString());
    }
    catch (Exception e) {
      result.append("<B>Error</B>");
      result.append("<BR>");
      result.append(e.toString());
    }
    return result.toString();
  }

  private static String encodeHtmlTag(String tag) {
    if (tag==null)
      return null;
    int length = tag.length();
    StringBuffer encodedTag = new StringBuffer(2 * length);
    for (int i=0; i<length; i++) {
      char c = tag.charAt(i);
      if (c=='<')
        encodedTag.append("&lt;");
      else if (c=='>')
        encodedTag.append("&gt;");
      else if (c=='&')
        encodedTag.append("&amp;");
      else if (c=='"')
        encodedTag.append("&quot;");
      else if (c==' ')
        encodedTag.append("&nbsp;");
      else
        encodedTag.append(c);
    }
    return encodedTag.toString();
  }
}
