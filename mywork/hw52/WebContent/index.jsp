<%@ page import="hw52.ProductUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Student Store</title>
</head>
<body>
    <h1>Student Store</h1>
<p>Items available:</p>
    <%!
        public String liDecorator(String id, String name){
            return "<li><a href=\"product.jsp?id=" + id + "\">" + name + "</a></li>";
        }
    %>
<ul>
    <%
    	ProductUtils pu = ProductUtils.getInstance();
        for(ProductUtils.Product p: pu.getProductList())
            out.print(liDecorator(p.id, p.name));
    %>

</ul>
</body>
</html>