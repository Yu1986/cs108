package hw52;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ShoppingCartServlet
 */
@WebServlet("/ShoppingCartServlet")
public class ShoppingCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ShoppingCartServlet() {
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
		HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute(ShoppingCart.ATTRIBUTE_NAME);
        if (cart == null) cart = new ShoppingCart();
        
        //just added new param
        String id =  request.getParameter("productID");

        if (id != null) {
            cart.addProduct(id);

        } else {
            Enumeration<String> params = request.getParameterNames();
            while(params.hasMoreElements()){
                id = params.nextElement();
                cart.updateProductNum(id, Integer.parseInt(request.getParameter(id)));
            }
        }

        session.setAttribute(ShoppingCart.ATTRIBUTE_NAME, cart);

        RequestDispatcher rd = request.getRequestDispatcher("shopping-cart.jsp");
        rd.forward(request, response);

	}

}
