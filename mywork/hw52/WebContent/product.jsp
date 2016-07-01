<%@ page import="hw52.ProductUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	ProductUtils pu = ProductUtils.getInstance();
	ProductUtils.Product product =  pu.getProductById(request.getParameter("id"));
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=product.name%></title>
</head>
<body>

<h1><%= product.name %></h1>
<img src="<%="store-images/"+product.img%>" alt="<%=product.name%>">


<form action="ShoppingCartServlet" method="post">
    $<%=product.price%> <input name="productID" type="hidden" value="<%= product.id %>"/>
    <input type="submit" value="Add to cart"/>
</form>

</body>
</html>