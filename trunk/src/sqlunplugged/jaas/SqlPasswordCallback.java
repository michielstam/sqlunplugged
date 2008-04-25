package sqlunplugged.jaas;

//author: Remi Ham

import javax.security.auth.callback.PasswordCallback;

public class SqlPasswordCallback extends PasswordCallback
{
	public SqlPasswordCallback(String prompt, boolean echoOn)
	{
		super(prompt, echoOn);
		System.out.println("SqlPasswordCallback()");
	}

	public String getPrompt()
	{
		System.out.println("SqlPasswordCallback.getPrompt()");
		return super.getPrompt();
	}

	public boolean isEchoOn()
	{
		System.out.println("SqlPasswordCallback.isEchoOn()");
		return super.isEchoOn();
	}

	public void setPassword(char[] password)
	{
		System.out.println("SqlPasswordCallback.setPassword()");
		super.setPassword(password);
	}

	public char[] getPassword()
	{
		System.out.println("SqlPasswordCallback.getPassword()");
		return super.getPassword();
	}

	public void clearPassword()
	{
		System.out.println("SqlPasswordCallback.clearPassword()");
		super.clearPassword();
	}
}