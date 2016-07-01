package hw5;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AccountCreationServlet
 */
@WebServlet("/AccountCreationServlet")
public class AccountCreationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountCreationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AccountManager accountManager = AccountManager.getInstance();

        if(accountManager.addNewAccount(request.getParameter("name"), request.getParameter("password"))){
            RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
            rd.forward(request,response);
        }
        else{
            RequestDispatcher rd = request.getRequestDispatcher("create_fail.jsp");
            rd.forward(request,response);
        }
	}

}
