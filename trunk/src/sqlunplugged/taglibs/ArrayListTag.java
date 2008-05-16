package sqlunplugged.taglibs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class ArrayListTag extends TagSupport
{
	//attributes static
	private static int ID = 0;
	private static int getID() { return ID++; }
	
	//attributes default
	private int id = getID();
	private String var = null;
	private ArrayList<String[]> arrayList = null;
	private Iterator iter = null;
	
	//constructor
	public ArrayListTag()
	{
		System.out.println("("+id+")ArrayListTag()");
	}
	
	public int doStartTag()	throws JspException
	{
		System.out.println("("+id+")ArrayListTag.doStartTag()");
		iter = arrayList.iterator();
		return iter.hasNext() ? EVAL_BODY_INCLUDE : SKIP_BODY;
	}
	
	public int doEndTag() throws JspException
	{
		System.out.println("("+id+")ArrayListTag.doEndTag()");
		return EVAL_PAGE;
	}
	
	public void setParent(Tag t)
	{
		System.out.println("("+id+")ArrayListTag.setParent("+t+")");
	}
	
	public int doAfterBody() throws JspException
	{
		System.out.println("("+id+")ArrayListTag.doAfterBody()");
		boolean next = iter.hasNext();
		if (next) pageContext.setAttribute(var, iter.next());
		return next ? EVAL_BODY_AGAIN : SKIP_BODY;
	}

	public void setArrayList(ArrayList<String[]> newArrayList)
	{
		System.out.println("("+id+")ArrayListTag.setCollection("+newArrayList+")");
		arrayList = newArrayList;
	}
	
	public void setVar(String newVar)
	{
		System.out.println("("+id+")ArrayListTag.setVar("+newVar+")");    
		var = newVar;
	}  
}