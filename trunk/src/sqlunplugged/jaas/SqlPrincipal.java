package sqlunplugged.jaas;


import java.security.Principal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.Serializable;


public class SqlPrincipal implements Principal, Serializable
{
  protected String username = null;
  protected String password = null;

  public SqlPrincipal(String username)
  {
    System.out.println("SqlPrincipal(" + username + ")");
    if (username == null)
    {
      throw new NullPointerException("illegal null input");
    }
    this.username = username;
  }

  public SqlPrincipal(String username, String password)
  {
    System.out.println("SqlPrincipal(" + username + ", " + password + ")");
    if (username == null)
    {
      throw new NullPointerException("illegal null input");
    }
    this.username = username;
    this.password = password;
  }

  public boolean equals(Object another)
  {
    System.out.print("SqlPrincipal.equals()");
    boolean ret = false;
    if (another != null)
    {
      if (another instanceof SqlPrincipal)
      {
    	  SqlPrincipal other = (SqlPrincipal)another;
        if (this.username.equals(other.username)) {
          ret = true;
        }
      }
    }
    return ret;
  }

  public String getName()
  {
    System.out.println("SqlPrincipal.getName()");
    return username;
  }

  public Connection getConnection()
  {
	  System.out.println("SqlPrincipal.getConnection()");
	  Connection con = null;
	    try {
	    	Class.forName("oracle.jdbc.driver.OracleDriver");
	    	con = DriverManager.getConnection("jdbc:oracle:thin:"+username+"/"+password+"@145.89.193.70:1521:cursus1");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	  return con;
  }

  public int hashCode()
  {
    System.out.println("SqlPrincipal.hashCode()");
    return username.hashCode();
  }

  public String toString()
  {
    System.out.println("SqlPrincipal.toString()");
    return username;
  }
  
  public String getUsername()
  {
	  return username;
  }
}