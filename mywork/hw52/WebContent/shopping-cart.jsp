<%@ page import="java.math.BigDecimal" %>
<%@ page import="hw52.ProductUtils" %>
<%@ page import="hw52.ShoppingCart" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%!
    private String liDecorator(ProductUtils.Product product, int quantityOf) {
        return "<li> <input type ='number' value='" + quantityOf + "' name='" + product.id + "'>"
                + product.name + ", " + product.price + "</li>";
    }
%>
<%
    BigDecimal total = new BigDecimal(0);
    ShoppingCart cart = (ShoppingCart) session.getAttribute(ShoppingCart.ATTRIBUTE_NAME);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Shopping Cart</title>
</head>
<body>
<h1>Shopping Cart</h1>

<form action="ShoppingCartServlet" method="post">
    <ul>
        <%
            for (ShoppingCart.Cart c : cart.getCartList()) {
            	float t= c.prod.price * c.num;
                total = total.add(new BigDecimal(t));
                out.print(liDecorator(c.prod, c.num));
            }
        %>
    </ul>
    Total: $ <%= total %> <input type="submit" value="Update Cart"/>
</form>
<a href="/hw52">Continue shopping</a>
</body>
</html>