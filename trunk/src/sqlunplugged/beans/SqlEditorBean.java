package sqlunplugged.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class SqlEditorBean
{
	//attributes
	private String 				query 				= "";
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
}