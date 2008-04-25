package sqlunplugged.servlets;

//author: Remi Ham

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sqlunplugged.jaas.SqlPrincipal;


public class SqlInloggen extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public SqlInloggen()
	  {
	    System.out.println("SqlAccess()");
	  }

  public void init() throws ServletException
  {
    System.out.println("SqlAccess.init()");
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    String s = "";
    HttpSession session = request.getSession();
    RequestDispatcher dispatcher = null;

    SqlPrincipal bp = (SqlPrincipal)request.getUserPrincipal();
	if(bp!=null&&bp.getConnection()!=null)
	{
		s = "SqlEditor.jsp";
		//haal de database connection uit the principal en stop hem in de session
		Connection con = bp.getConnection();
		session.setAttribute("connection", con);
	}
	else
		s = "Login.jsp";
    dispatcher = request.getRequestDispatcher(s);
    dispatcher.forward(request, response);
  }

  public void destroy()
  {
    System.out.println("SqlAccess.destroy()");
  }

  protected void finalize() throws Throwable
  {
    System.out.println("SqlAccess.finalize()");
    super.finalize();
  }
}