<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:directive.include file="/WEB-INF/layouts/includes.jsp"/>
<div id="cssmenu">
<ul>
   <li class="active"><a href="${pageContext.request.contextPath}/user/catalog.htm"><span>Catalogue</span></a></li>
   <li class="last"><a href="${pageContext.request.contextPath}/user/cart.htm"><span>Cart</span></a></li>
</ul>
</div>