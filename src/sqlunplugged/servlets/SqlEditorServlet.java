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
		private String sqlErrorMessage = "";
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("/SqlEditor.jsp");
			dispatcher.forward(request, response);
		}
		
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			System.out.println("(" + id + ")SqlEditorServlet.doPost()");
			HttpSession session = request.getSession();
			seb = new SqlEditorBean();
			SqlPrincipal sp = (SqlPrincipal)request.getUserPrincipal();
			Connection con = sp.getConnection();
			//query wordt uitgevoerd; returned een boolean ter controle van goede uitvoer
			boolean querySuccessfulExecuted = getQueryResults(con, request.getParameter("query"));
			
			if(querySuccessfulExecuted)
			{
				System.out.println(seb.getQueryResultData());
			}
			
			if(!querySuccessfulExecuted)
			{
				String[] sqlErrorMessageAsArray = new String[1];
				sqlErrorMessageAsArray[0] = sqlErrorMessage;
				ArrayList<String[]> arraylist = new ArrayList<String[]>();
				arraylist.add(sqlErrorMessageAsArray);
				seb.setQueryResultData(arraylist);
			}
			
			seb.setQuery(request.getParameter("query"));
			
			session.setAttribute("key", seb);
			//altijd connectie sluiten nadat je gedaan hebt wat je wilt 
			//doen als je sp.getConnection(); aanroept maakt ie automatisch 
			//nieuwe connectie voor je aan
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/SqlEditor.jsp");
			dispatcher.forward(request, response);
		}
		
		public void destroy()
		{
			System.out.println("(" + id + ")SqlEditorServlet.destroy()");
		}
		
		public boolean getQueryResults(Connection con, String sql)
		{
			System.out.println("(" + id + ")SqlEditorServlet.getQuertResult()");
			System.out.println(con);
			System.out.println("SQL-query: "+sql);
			
			int columnLength = 0;
			
			if (sql==null || sql.equals(""))
				return false;

			//vraag het statement op
			Statement query = null;
			try
				{query = con.createStatement();}
			
			catch(SQLException e)
			{
				sqlErrorMessage = e.getMessage();
				return false;
			}
			
			
			//als de statement een SELECT query is...
			if (sql.trim().toUpperCase().startsWith("SELECT"))
			{
				System.out.println("SQL-query is een SELECT-statement");
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
		        		System.out.println(resultMetaData.getColumnName(i));
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
			        		System.out.println(resultSet.getString(i));
			        		data[i] = encodeHtmlTag(resultSet.getString(i));
			        	}
			        	queryResultsData.add(data);
			        }
			        seb.setQueryResultData(queryResultsData);
			        resultSet.close();
			        con.close();
				}
				
				catch(SQLException e)
				{
					System.out.println("SQL error: " + e);
					sqlErrorMessage = e.getMessage();
					return false;
				}
			}
			
			//query is geen SELECT maar een 'DO' query, schrijf feedback van oracle in header bean
			else
			{
				try
				{
					//to do - standard message forwarden
					int i = query.executeUpdate(sql);
					con.close();
				}
				
				catch(SQLException e)
				{
					sqlErrorMessage = e.getMessage();
					return false;
				}
			}
			
	        //close connections
	        return true;
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
