package sqlunplugged.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

public class SqlEditorServlet extends HttpServlet implements Cloneable{
		static final long serialVersionUID = 0L; 
		private static long ID = 0L;
		private static synchronized long nextID()
		{
			System.out.println("SqlEditorServlet.nextID");
			return ID++;
		}
		private long id = nextID();
		public SqlEditorServlet()
		{
			System.out.println("NewAccount.NewAccount()");
		}
		public void init() throws ServletException
		{
			System.out.println("(" + id + ")NewAccountControl.init()");
		}
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			HttpSession session = request.getSession();
			/*
			MyPrincipal mp = (MyPrincipal)request.getUserPrincipal();
			AccountManager am = mp.getAccountManager();
			efg.bank.AccountOffice ao = mp.getAccountOffice();
			NewAccountBean nab = new NewAccountBean(session.getId());
			nab.populate(request);
			if (nab.validate())
			{
				try
				{
					String newAccount = am.newAccount(Double.parseDouble(nab.getLimiet()), nab.getNaam(), nab.getPincode());
					nab.setRekNr(newAccount);
					System.out.println("Nieuwe reknr :" + newAccount);
				}
				catch (Exception e)
				{
					System.out.println(e);
				}
			}
			request.setAttribute("key", nab);
			AccountInfoBean aib = new AccountInfoBean(session.getId());
			aib.populate(ao);
			request.setAttribute("accountinfo", aib);
			*/
			RequestDispatcher dispatcher = request.getRequestDispatcher("/NewAccount.jsp");
			dispatcher.forward(request, response);
		}
		public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			HttpSession session = request.getSession();
			/*
			MyPrincipal mp = (MyPrincipal)request.getUserPrincipal();
			efg.bank.AccountManager am = mp.getAccountManager();
			AccountOffice ao = mp.getAccountOffice();
			if (am != null)
			{
				NewAccountBean nab = new NewAccountBean(session.getId());
				request.setAttribute("key", nab);
				AccountInfoBean aib = new AccountInfoBean(session.getId());
				aib.populate(ao);
				request.setAttribute("accountinfo", aib);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/NewAccount.jsp");
				dispatcher.forward(request, response);
			}
			*/
		}
		public void destroy()
		{
			System.out.println("(" + id + ")AccountManager.destroy()");
		}
	}
