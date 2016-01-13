<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <jsp:directive.include file="/WEB-INF/layouts/includes.jsp"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/animate.css">
<!-- Custom Stylesheet -->
<link rel="stylesheet" href="css/style.css">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>catalogue</title>
</head>
<body>
<div id="cssmenu">
<ul>
   <li class="active"><a href="${pageContext.request.contextPath}/user/home.htm"><span>Home</span></a></li>
   <li class="last"><a href="${pageContext.request.contextPath}/user/cart.htm"><span>Cart</span></a></li>
</ul>
</div>
         
        
        <display:table id="products" name="products" requestURI="${products}"  >
            <display:column   sortName="description" title="Description" ><a href="${pageContext.request.contextPath}/user/product.htm?prodCode=${products.code }">${products.description}</a></display:column>
            <display:column  property="price" sortName="price"/>
        </display:table>
       
</body>
</html>

