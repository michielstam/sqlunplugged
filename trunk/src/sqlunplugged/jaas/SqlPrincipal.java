package sqlunplugged.jaas;


import java.security.Principal;
import java.sql.Connection;
import java.io.Serializable;


public class SqlPrincipal implements Principal, Serializable
{
  protected String name = null;
  protected Connection con = null;

  public SqlPrincipal(String name)
  {
    System.out.println("SqlPrincipal(" + name + ")");
    if (name == null)
    {
      throw new NullPointerException("illegal null input");
    }
    this.name = name;
  }

  public SqlPrincipal(String name, Connection con)
  {
    System.out.println("SqlPrincipal(" + name + ", " + con + ")");
    if (name == null)
    {
      throw new NullPointerException("illegal null input");
    }
    this.name = name;
    this.con = con;
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
        if (this.name.equals(other.name)) {
          ret = true;
        }
      }
    }
    return ret;
  }

  public String getName()
  {
    System.out.println("SqlPrincipal.getName()");
    return name;
  }

  public Connection getConnection()
  {
	  System.out.println("SqlPrincipal.getConnection()");
	  return con;
  }

  public int hashCode()
  {
    System.out.println("SqlPrincipal.hashCode()");
    return name.hashCode();
  }

  public String toString()
  {
    System.out.println("SqlPrincipal.toString()");
    return name;
  }
}