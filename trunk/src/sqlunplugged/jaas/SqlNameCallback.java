package sqlunplugged.jaas;


import javax.security.auth.callback.NameCallback;

public class SqlNameCallback extends NameCallback
{
	public SqlNameCallback(String prompt)
	{
		super(prompt);
		System.out.println("SqlNameCallback()");
	}

	public SqlNameCallback(String prompt, String defaultName)
	{
		super(prompt, defaultName);
		System.out.println("SqlNameCallback()");
	}

	public String getPrompt()
	{
		System.out.println("SqlNameCallback.getPrompt()");
		return super.getPrompt();
	}

	public String getDefaultName()
	{
		System.out.println("SqlNameCallback.getDefaultName()");
		return super.getDefaultName();
	}

	public void setName(String name)
	{
		System.out.println("SqlNameCallback.setName()");
		super.setName(name);
	}

	public String getName()
	{
		System.out.println("SqlNameCallback.getName()");
		return super.getName();
	}
}