package sqlunplugged.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadException;
import javazoom.upload.UploadFile;
import sqlunplugged.beans.SqlEditorBean;
import sqlunplugged.jaas.SqlPrincipal;

public class SqlEditorServlet extends HttpServlet implements Cloneable{
	static final long serialVersionUID = 0L; 
	private static long ID = 0L;
	private SqlEditorBean seb = null;
	private SqlPrincipal sp = null;
	private HttpSession session = null;
	String rootFolder = "C:/Documents and Settings/Karel Manschot/Bureaublad/sql_editor_temp_dir";
	File userFolder = null;
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
	
	//do get
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException, ServletException
	{
		//default actions
		System.out.println("(" + id + ")SqlEditorServlet.doGet()");
		session = request.getSession();
		setSqlPrincipal(request);
		setUserFolder();
		
		//set bean
		seb = new SqlEditorBean();
		seb.setFileStatus("");	
		seb.setUploadedFiles(getUserFiles());
		
		//set session-attribute and dispatch
		dispatch(request, response);
	}
	
	//do post
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//default actions
		System.out.println("(" + id + ")SqlEditorServlet.doPost()");
		session = request.getSession();
		setSqlPrincipal(request);
		setUserFolder();
		
		//logout
		if(request.getParameter("logout") != null)
		{
			System.out.println("(" + id + ") Action: logout");
			seb = null;
			session.invalidate();
			response.sendRedirect("main");
		}
		
		//reset textarea
		else if(request.getParameter("reset") != null)
		{
			System.out.println("(" + id + ") Action: reset");
			seb.setQuery("");
			seb.setUploadedFiles(getUserFiles());
			seb.setFileStatus("");
		}
		
		//run query
		else if(request.getParameter("run") != null)
		{
			System.out.println(request.getParameter("run"));
			
			String query = request.getParameter("query");
			Connection con = sp.getConnection();
			//query wordt uitgevoerd; returned een boolean ter controle van goede uitvoer
			try{seb.setStatus(ExecuteQuery(con, query));con.close();} 
				catch (SQLException e){System.out.println("SQL error: " + e);seb.setStatus("Error: " + e.getMessage());}
			
			seb.setUploadedFiles(getUserFiles());
			seb.setFileStatus("");
			seb.setQuery(query);
		}
	
		//upload bestand
		else if(MultipartFormDataRequest.isMultipartFormData(request))
		{
			MultipartFormDataRequest mrequest = null;
			
			try{mrequest = new MultipartFormDataRequest(request);}
				catch (UploadException e1){e1.printStackTrace();}
			
			if(mrequest != null)
			{
				Hashtable files = mrequest.getFiles();
				if ((files != null) && (!files.isEmpty()))
				{
					System.out.println("(" + id + ") Action: upload file");
					UploadFile file = (UploadFile) files.get("uploadfile");
					if (file != null && file.getFileName() != null)
					{
						if(file.getContentType().equalsIgnoreCase("application/octet-stream") && file.getFileName().endsWith(".sql"))
						{
							try{seb.setFolderstore(userFolder.toString());
								seb.store(mrequest, "uploadfile");}
								catch (UploadException e){}
							seb.setFileStatus("Upload succeeded");
						}
						else seb.setFileStatus("You entered a file with a wrong file type or content");
						
					}
					else seb.setFileStatus("File doesn't exist or is empty");
				}
				else seb.setFileStatus("Failure in transfer");
			}
			else seb.setFileStatus("Unexpected error encouraged");
			
			seb.setUploadedFiles(getUserFiles());
		}
		
		//delete file
		else if(request.getParameter("delete") != null && request.getParameter("selected_file") != null)
		{
			File file = new File(userFolder+"/"+request.getParameter("selected_file"));
			if(file.exists())
			{
				file.delete();seb.setFileStatus("File "+ request.getParameter("selected_file") +" has been succesfully removed");
			}
			else
				seb.setFileStatus("Cannot remove non-existing file");
			
			seb.setUploadedFiles(getUserFiles());
			seb.setQuery("");
			seb.setStatus("");
		}
		
		//als gebruiker een file wilt deleten zonder een file te selecteren
		else if(request.getParameter("delete") != null && request.getParameter("selected_file") == null)
		{
			seb.setQueryResultData(new ArrayList<String[]>());
			seb.setQueryResultHeaders(new ArrayList<String[]>());
			seb.setUploadedFiles(getUserFiles());
			seb.setFileStatus("You must select a file before delete!");
		}	
		
		//execute file
		else if(request.getParameter("execute") != null && request.getParameter("selected_file") != null)
		{
			String file = new String(userFolder+"/"+request.getParameter("selected_file"));
			seb.setQuery("");
			seb.setQueryResultData(new ArrayList<String[]>());
			seb.setQueryResultHeaders(new ArrayList<String[]>());
			Connection con = sp.getConnection();
			start(con, file);
			
			try{con.close();}
				catch (SQLException e){seb.setStatus("Error: Failed to close the connection properly");}
				
			seb.setUploadedFiles(getUserFiles());
			
		}
		
		//als gebruiker een file wilt executeren zonder een file te selecteren
		else if(request.getParameter("execute") != null && request.getParameter("selected_file") == null)
		{
			seb.setQueryResultData(new ArrayList<String[]>());
			seb.setQueryResultHeaders(new ArrayList<String[]>());
			seb.setUploadedFiles(getUserFiles());
			seb.setFileStatus("You must select a file before execution!");
		}		
		
		//ongedefinieerde actie werd uitgevoerd
		else
		{
			seb = new SqlEditorBean();
			seb.setStatus("You tried a non-existing action");
		}
		
		if(request.getParameter("logout") == null)
			dispatch(request, response);
	}
			
	//start reading and executing file
	private void start(Connection con,String command)
	{
		File file = new File(command);
		
		if(file==null)
		{
			seb.setStatus("Error: File does not exists") ;
			return;
		}
		
		try 
		{
	        BufferedReader in = new BufferedReader(new FileReader(file.getAbsolutePath()));
	        String str;
	        String query = "";
	        String status = "";
	        boolean queryIsStarted = false;
	        boolean containingAtLeastOneQuery = false;
	        while ((str = in.readLine()) != null) 
	        {
	        	if(!queryIsStarted)
	        		if(str.trim().toUpperCase().startsWith("CREATE") || str.trim().toUpperCase().startsWith("ALTER") ||str.trim().toUpperCase().startsWith("DROP") || str.trim().toUpperCase().startsWith("INSERT") || str.trim().toUpperCase().startsWith("UPDATE") || str.trim().toUpperCase().startsWith("DELETE"))
	        		{
	        			queryIsStarted = true;
	        			containingAtLeastOneQuery = true;
	        		}
	        	
	        	if(queryIsStarted)
	        		query += str;
	        	
	        	if(str.endsWith(";") && queryIsStarted)
	        	{
	        		queryIsStarted = false;
	        		status += "<hr /><br />" + query;
	        		try 
	        		{
						status += "<br />" + ExecuteQuery(con, query.trim().substring(0, query.trim().length()-1));
					} 
	        		catch (SQLException e) 
					{
						System.out.println("Error: " + e);
						status += "<br />" + "Error: " + e.getMessage();
					}
	        		query = "";
	        	}
	        }
	        in.close();
	        seb.setStatus(status);
	        if(containingAtLeastOneQuery)
	        	seb.setFileStatus("File succesfully executed");
	        else
	        	seb.setFileStatus("File doesn't contain correct queries (only CREATE, ALTER, DROP, INSERT, UPDATE, DELETE are allowed).");
	    } catch (IOException e) {
			seb.setFileStatus("Error: File does not exists") ;
	    	
	    }
	}
			
	//destroy	
	public void destroy()
	{
		System.out.println("(" + id + ")SqlEditorServlet.destroy()");
	}
	
	//execute query
	public String ExecuteQuery(Connection con, String sql) throws SQLException
	{
		//default actions
		System.out.println("(" + id + ")SqlEditorServlet.getQuertResult()");
		System.out.println(con);
		System.out.println("SQL-query: "+sql);
		String ret = "";
		int columnLength = 0;
		
		//query is leeg
		if (sql==null || sql.equals(""))
			seb.setStatus("Error: no query entered");

		//vraag het sql-statement op
		Statement query = null;
		query = con.createStatement();
		
					
		//SELECT
		if (sql.trim().toUpperCase().startsWith("SELECT"))
		{
			System.out.println("SQL-query is een SELECT-statement");
			
			//opslag
			ArrayList<String[]> queryResultsHeaders = new ArrayList<String[]>();
			ArrayList<String[]> queryResultsData = new ArrayList<String[]>();
			ResultSet resultSet = null;
			
			//execute query
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
	        resultSet.close();
	        return queryResultsData.size()+" Items selected";
		}
		
		//Query anders dan SELECT
		else if(	sql.trim().toUpperCase().startsWith("INSERT") ||
					sql.trim().toUpperCase().startsWith("UPDATE") ||
					sql.trim().toUpperCase().startsWith("DELETE") ||
					sql.trim().toUpperCase().startsWith("CREATE") ||
					sql.trim().toUpperCase().startsWith("ALTER") ||
					sql.trim().toUpperCase().startsWith("DROP"))			
		{
			//to do - standard message forwarden
			int i = query.executeUpdate(sql);
			
			if(	sql.trim().toUpperCase().startsWith("INSERT") || sql.trim().toUpperCase().startsWith("UPDATE") || sql.trim().toUpperCase().startsWith("DELETE"))
				ret = i + " Row(s) affected";
			
			
			else if(sql.trim().toUpperCase().startsWith("CREATE") || sql.trim().toUpperCase().startsWith("ALTER") || sql.trim().toUpperCase().startsWith("DROP"))
				ret = "Query successful executed";
			
			return ret;
		}
		
		else
			return "You entered a non-valid query";	
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
	
	//set sql-principal
	private void setSqlPrincipal(HttpServletRequest request)
	{
		 sp = (SqlPrincipal)request.getUserPrincipal();
	}
	
	//set userfolder
	private void setUserFolder()
	{
		userFolder = new File(rootFolder+"/"+sp.getUsername());
	}
	
	//get arraylist userfiles
	private ArrayList<String> getUserFiles()
	{
		ArrayList<String> storedFiles = new ArrayList<String>();
		if(userFolder.list() != null)
		{
			for(String file : userFolder.list())
			{
				if(file != null);
				storedFiles.add(file);
			}
		}
					
		return storedFiles;
	}
	
	//dispatch
	private void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		session.setAttribute("bean", seb);		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/SqlEditor.jsp");
		dispatcher.forward(request, response);
	}
}