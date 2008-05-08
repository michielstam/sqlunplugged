package sqlunplugged.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sqlunplugged.jaas.SqlPrincipal;
import javax.servlet.ServletException;
import java.sql.Connection;

public class SqlEditorServlet extends HttpServlet implements Cloneable{
		static final long serialVersionUID = 0L; 
		private static long ID = 0L;
		private static synchronized long nextID()
		{
			System.out.println("SqlEditorServlet.nextID");
			return ID++;
		}
		private long id = nextID();
		public SqlEditorServlet()
		{
			System.out.println("SqlEditorServlet()");
		}
		public void init() throws ServletException
		{
			System.out.println("(" + id + ")SqlEditorServlet.init()");
		}
		/*
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			HttpSession session = request.getSession();
			RequestDispatcher dispatcher = request.getRequestDispatcher("/SqlInloggen.jsp");
			dispatcher.forward(request, response);
		}
		*/
		public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			SqlPrincipal sp = (SqlPrincipal)request.getUserPrincipal();
			Connection con = sp.getConnection();
			System.out.println("(" + id + ")SqlEditorServlet.doGet()");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/SqlEditor.jsp");
			dispatcher.forward(request, response);
		}
		public void destroy()
		{
			System.out.println("(" + id + ")AccountManager.destroy()");
		}
	}
