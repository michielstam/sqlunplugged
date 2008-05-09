package sqlunplugged.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sqlunplugged.beans.SqlEditorBean;
import sqlunplugged.jaas.SqlPrincipal;
import javax.servlet.ServletException;
import java.sql.Connection;
import java.sql.SQLException;

public class SqlEditorServlet extends HttpServlet implements Cloneable{
		static final long serialVersionUID = 0L; 
		private static long ID = 0L;
		private SqlEditorBean seb = null;
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
		
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			System.out.println("(" + id + ")SqlEditorServlet.doPost()");
			HttpSession session = request.getSession();
			
			String query = request.getParameter("query");
			SqlPrincipal sp = (SqlPrincipal)request.getUserPrincipal();
			Connection con = sp.getConnection();
			try {
				//altijd connectie sluiten nadat je gedaan hebt wat je wilt doen als je sp.getConnection(); aanroept maakt ie automatisch nieuwe connectie voor je aan
				con.close();
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("/SqlEditor.jsp");
			dispatcher.forward(request, response);
		}
		
		public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			System.out.println("(" + id + ")SqlEditorServlet.doGet()");
			HttpSession session = request.getSession();
			seb = new SqlEditorBean();
			session.setAttribute("key", seb);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/SqlEditor.jsp");
			dispatcher.forward(request, response);
		}
		public void destroy()
		{
			System.out.println("(" + id + ")AccountManager.destroy()");
		}
	}
