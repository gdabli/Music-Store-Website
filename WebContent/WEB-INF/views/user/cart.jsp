<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:directive.include file="/WEB-INF/layouts/includes.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CART</title>
<style type="text/css">

table, th, td {
	border: 1px solid black;
	border-bottom: 1px solid #ddd;
}

}
table {
	width: 100%;
	height: 50px;
}

th, td {
	text-align: center;
	padding: 15px;
}

tr:hover {background-color: #f5f5f5}

th {
    background-color: #4CAF50;
    color: white;
}

</style>
</head>
<body>


	<div id="cssmenu">
		<ul>
			<li class="active"><a
				href="${pageContext.request.contextPath}/user/home.htm"><span>Home</span></a></li>
			<li class="last"><a
				href="${pageContext.request.contextPath}/user/catalog.htm"><span>Catalogue</span></a></li>
			<li class="last"><a
				href="${pageContext.request.contextPath}/user/checkOut.htm"><span>Create
						Invoice</span></a></li>


		</ul>
	</div>


	<c:if test="${cart.items.size()!=0}">


		<table>


			<thead>
				<tr>
					<th>Album</th>
					<th>Cost</th>
					<th>Quantity</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${cart.items}" var="item">
					<tr>
						<td class="user-name"><c:out value="${item.product.description}" /></td>
						<td class="user-email"><c:out value="${item.product.price}" /></td>
						<td class="user-phone"><c:out value="${item.quantity}" /></td>

					</tr>

				</c:forEach>
			</tbody>
		</table>
	</c:if>





</body>
</html>