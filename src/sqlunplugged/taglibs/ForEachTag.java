package sqlunplugged.taglibs;

//author: Gerben Strien + Karel M

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class ForEachTag extends TagSupport
{
  /*****************************************************************\
   Static
  \*****************************************************************/
  private static int ID = 0;
  private static int getID() { return ID++; }

  /*****************************************************************\
   Attributes
  \*****************************************************************/
  private int id = getID();
  private String var = null;
  private Collection<String> collection = null;
  private Iterator iter = null;

  /*****************************************************************\
   Construction
  \*****************************************************************/
  public ForEachTag()
  {
    System.out.println("("+id+")ForEachTag()");
  }

  /*****************************************************************\
   interface Tag
  \*****************************************************************/
  public int doStartTag() throws JspException
  {
    System.out.println("("+id+")ForEachTag.doStartTag()");
    iter = collection.iterator();
    return iter.hasNext() ? EVAL_BODY_INCLUDE : SKIP_BODY;
  }

  public int doEndTag()
  throws JspException
  {
    System.out.println("("+id+")ForEachTag.doEndTag()");
    return EVAL_PAGE;
  }

  public void setParent(Tag t)
  {
    System.out.println("("+id+")ForEachTag.setParent("+t+")");
  }

  public int doAfterBody()
  throws JspException
  {
    System.out.println("("+id+")ForEachTag.doAfterBody()");
    boolean next = iter.hasNext();
    if (next) pageContext.setAttribute(var, iter.next());
    return next ? EVAL_BODY_AGAIN : SKIP_BODY;
  }

  public void setCollection(String[] newArray)
  {
    System.out.println("("+id+")ForEachTag.setCollection("+newArray+")");
    for(int i = 1; i <= newArray.length; i++)
    {
    	collection.add(newArray[i]);
    }
  }
  
  public void setVar(String newVar)
  {
    System.out.println("("+id+")ForEachTag.setVar("+newVar+")");    
    var = newVar;
  }  
}