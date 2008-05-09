package sqlunplugged.beans;

public class SqlEditorBean{
	private String query = "";
	
	public SqlEditorBean()
	{
	}
	public void setQuery(String query)
	{
		this.query = query;
	}
	public String getQuery()
	{
		return query;
	}
}