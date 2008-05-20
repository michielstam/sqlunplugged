package sqlunplugged.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sqlunplugged.beans.SqlEditorBean;
import sqlunplugged.jaas.SqlPrincipal;

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
		
		public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			System.out.println("(" + id + ")SqlEditorServlet.doGet()");
			HttpSession session = request.getSession();
			seb = new SqlEditorBean();
			seb.setQuery("SELECT * FROM klant");
			session.setAttribute("bean", seb);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/SqlEditor.jsp");
			dispatcher.forward(request, response);
		}
		
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			System.out.println("(" + id + ")SqlEditorServlet.doPost()");
			HttpSession session = request.getSession();
						
			if(request.getParameter("logout") != null)
			{
				session.invalidate();
				seb = null;
				response.sendRedirect("main");
			}
			
			else if(request.getParameter("execute") != null)
			{
				SqlPrincipal sp = (SqlPrincipal)request.getUserPrincipal();
				Connection con = sp.getConnection();
				//query wordt uitgevoerd; returned een boolean ter controle van goede uitvoer
				ExecuteQuery(con, request.getParameter("query"));
				seb.setQuery(request.getParameter("query"));
				session.setAttribute("bean", seb);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/SqlEditor.jsp");
				dispatcher.forward(request, response);
			}
		}
		
		public void destroy()
		{
			System.out.println("(" + id + ")SqlEditorServlet.destroy()");
		}
		
		public void ExecuteQuery(Connection con, String sql)
		{
			System.out.println("(" + id + ")SqlEditorServlet.getQuertResult()");
			System.out.println(con);
			System.out.println("SQL-query: "+sql);
			
			int columnLength = 0;
			
			//query is leeg
			if (sql==null || sql.equals(""))
				seb.setStatus("Error: no query entered");

			//vraag het sql-statement op
			Statement query = null;
			try
				{query = con.createStatement();}
			
			catch(SQLException e)
			{
				System.out.println(e);
				seb.setStatus("Error: " + e.getMessage());
			}
			
			
			//SELECT
			if (sql.trim().toUpperCase().startsWith("SELECT"))
			{
				System.out.println("SQL-query is een SELECT-statement");
				
				//opslag
				ArrayList<String[]> queryResultsHeaders = new ArrayList<String[]>();
				ArrayList<String[]> queryResultsData = new ArrayList<String[]>();
				ResultSet resultSet = null;
				
				//execute query
				try
				{	
					resultSet = query.executeQuery(sql);
					ResultSetMetaData resultMetaData = resultSet.getMetaData();
			        columnLength = resultMetaData.getColumnCount();
			        
			        //write columnLength to bean for dynamic table in jsp
			        seb.setColumnLength(columnLength);
			        
			        //write headers to header arraylist
			        String[] headers = new String[columnLength+1];
			        for (int i=1; i<=columnLength; i++)
		        	{
		        		System.out.println("Column name: " + resultMetaData.getColumnName(i));
		        		headers[i] = resultMetaData.getColumnName(i);
		        	}
			        queryResultsHeaders.add(headers);
			        seb.setQueryResultHeaders(queryResultsHeaders);
			        
			        //write results to data arraylist
			        while (resultSet.next())
			        {
			        	String[] data = new String[columnLength+1];
			        	for (int i=1; i<=columnLength; i++)
			        	{
			        		System.out.println("Recordattribuut: "+resultSet.getString(i));
			        		data[i] = encodeHtmlTag(resultSet.getString(i));
			        	}
			        	queryResultsData.add(data);
			        }
			        seb.setQueryResultData(queryResultsData);
			        seb.setStatus(""+queryResultsData.size()+" Items selected");
			        resultSet.close();
			        con.close();
				}
				
				catch(SQLException e)
				{
					System.out.println("SQL error: " + e);
					seb.setStatus("Error: " + e.getMessage());
				}
			}
			
			//Query anders dan SELECT
			else
			{
				try
				{
					//to do - standard message forwarden
					int i = query.executeUpdate(sql);
					
					if(	sql.trim().toUpperCase().startsWith("INSERT") ||
						sql.trim().toUpperCase().startsWith("UPDATE") ||
						sql.trim().toUpperCase().startsWith("DELETE"))
					{	
						seb.setStatus("" + i + " Row(s) affected");
						con.close();
					}
					
					else if(
						sql.trim().toUpperCase().startsWith("CREATE") ||
						sql.trim().toUpperCase().startsWith("ALTER") ||
						sql.trim().toUpperCase().startsWith("DROP"))
					{
						seb.setStatus("Query successful executed");
						con.close();						
					}
					
					else
					{
						seb.setStatus("Query successful executed");
						con.close();
					}
				}
				
				catch(SQLException e)
				{
					System.out.println("SQL error: " + e);
					seb.setStatus("Error: " + e.getMessage());
				}
			}
		}

		//encode htmltag, special characters
		private static String encodeHtmlTag(String tag)
		{
			System.out.println("SqlEditorServlet.endcodeHtmlTag("+ tag+")");
			if (tag==null)
				return null;
			
			int length = tag.length();
			StringBuffer encodedTag = new StringBuffer(2 * length);
			for (int i=0; i<length; i++)
			{
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
