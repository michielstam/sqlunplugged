package sqlunplugged.beans;

import java.util.ArrayList;

import javazoom.upload.UploadBean;

public class SqlEditorBean extends UploadBean
{
	//attributes
	private String 				query 				= "";
	private String				status				= "";
	private String				fileStatus			= "";
	private ArrayList<String>   uploadedFiles 		= new ArrayList<String>();
	private int					columnLength		= 0;
	private ArrayList<String[]> queryResultHeaders	= new ArrayList<String[]>();
	private ArrayList<String[]> queryResultData		= new ArrayList<String[]>();
		
	//constructor
	public SqlEditorBean(){}
	
	//query
	public void setQuery(String newQuery)
		{query = newQuery;}
	
	public String getQuery()
		{return query;}
	
	//column length
	public void setColumnLength(int newColumnLength)
		{columnLength = newColumnLength;}
	
	public int getColumnLength()
		{return columnLength;}
	
	//query results headers
	public void setQueryResultHeaders(ArrayList<String[]> newQueryResultHeaders)
		{queryResultHeaders = newQueryResultHeaders;}

	public ArrayList<String[]> getQueryResultHeaders()
		{return queryResultHeaders;}
	
	//query results data
	public void setQueryResultData(ArrayList<String[]> newQueryResults)
		{queryResultData = newQueryResults;}

	public ArrayList<String[]> getQueryResultData()
		{return queryResultData;}

	public String getStatus() {
		return status;
	}

	public void setStatus(String newStatus) {
		status = newStatus;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	public ArrayList<String> getUploadedFiles() {
		return uploadedFiles;
	}

	public void setUploadedFiles(ArrayList<String> newUploadedFiles) {
		uploadedFiles = newUploadedFiles;
	}
}