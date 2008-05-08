package sqlunplugged.jaas;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;



public class SqlLoginModule implements LoginModule
{
	private Subject subject = null;
	private CallbackHandler callbackHandler = null;
	private Map sharedState = null;
	private Map options = null;

	private Set principals = null;

	private boolean succeeded = false;
	private boolean commitSucceeded = false;

	private SqlPrincipal principal = new SqlPrincipal("SqlLoginModule");
	private SqlGroup roles = null;
	private SqlGroup callerPrincipal = null;
	

	public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options)
	{
		System.out.println("SqlLoginModule.initialize()");

		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = sharedState;
		this.options = options;

		principals = subject.getPrincipals();
	}

	public boolean login() throws LoginException
	{
		System.out.println("SqlLoginModule.login()");
		boolean ret = false;
		SqlNameCallback nc = new SqlNameCallback("User name: ", "guest");
		SqlPasswordCallback pc = new SqlPasswordCallback("Password: ", false);
		Callback callbacks[] = {nc, pc};

		try
		{
			callbackHandler.handle(callbacks);
		}
			catch (IOException e)
			{
				System.out.println("IOException " + e);
				LoginException le = new LoginException("Failed to get username/password");
				le.initCause(e);
				throw le;
			}
			catch (UnsupportedCallbackException e)
			{
				System.out.println("UnsupportedCallbackException " + e);
				LoginException le = new LoginException("CallbackHandler does not support: " + e.getCallback());
				le.initCause(e);
				throw le;
			}

		String username = nc.getName();
		char[] pwd = pc.getPassword();
		String password = new String((char[])pwd.clone());
		pc.clearPassword();
		System.out.println("username=" + username);
		System.out.println("password=" + password);

		
		Connection con = null;
	    try {
	    	Class.forName("oracle.jdbc.driver.OracleDriver");
	    	con = DriverManager.getConnection("jdbc:oracle:thin:"+username+"/"+password+"@145.89.193.70:1521:cursus1");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
		//start problem
		if(con != null)
		{
			roles = new SqlGroup("Roles");
			roles.addMember(new SqlPrincipal("users"));
			callerPrincipal = new SqlGroup("CallerPrincipal");
			callerPrincipal.addMember(new SqlPrincipal(username, con));
			ret = succeeded = true;
			System.out.println("AccountManager True");
			return ret;
	
			
		}
		return ret;
	}
	
	public boolean commit()	throws LoginException
	{
		System.out.println("SqlLoginModule.commit()");
		boolean ret = false;
		if (succeeded)
		{
			principals.add(principal);
			principals.add(roles);
			principals.add(callerPrincipal);
			//principals.add(ao);
			System.out.println(subject);
			ret = commitSucceeded = true;
		}
		return ret;
	}

	public boolean abort() throws LoginException
	{
		System.out.println("SqlLoginModule.abort()");
		boolean ret = true;
		succeeded = false;
		if (commitSucceeded)
		{
			ret = ret && logout();
			commitSucceeded = false;
		}
		return ret;
	}

	public boolean logout()	throws LoginException
	{
		System.out.println("SqlLoginModule.logout()");
		return principals.remove(principal) && principals.remove(roles) && principals.remove(callerPrincipal);
	}
}