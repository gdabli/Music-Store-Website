<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:directive.include file="/WEB-INF/layouts/includes.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" type="text/css" href="css/normalize.css" />
<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/component.css" />



<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PRODUCT</title>
</head>
<body>
	<div class="container">

		<div id="cssmenu">
			<ul>
				<li class="active"><a
					href="${pageContext.request.contextPath}/user/home.htm"><span>Home</span></a></li>
				<li class="last"><a
					href="${pageContext.request.contextPath}/user/catalog.htm"><span>Catalogue</span></a></li>
				<li class="last"><a
					href="${pageContext.request.contextPath}/user/cart.htm?prodCode=${product.code}"><span>Order</span></a></li>
				<li class="last"><a
					href="${pageContext.request.contextPath}/user/cart.htm"><span>Cart</span></a></li>
				<li class="last"><a
					href="${pageContext.request.contextPath}/sound/${product.code}/sound.html"><span>Show Tracks</span></a></li>
			</ul>
		</div>

		<display:table id="product.tracks" name="product.tracks"
			requestURI="${product.tracks }">
			<display:column property="trackNumber" title="Number" />
			<display:column property="title" title="Track" />
		</display:table>

	</div>

</body>
</html>