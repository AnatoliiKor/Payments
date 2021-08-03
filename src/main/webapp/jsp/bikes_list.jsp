<%@include file="navbar.jsp" %>

<html>
<head>
    <title>Bikes</title>
</head>
<body>
<div class="container mt-2">
<h2><fmt:message key="bikes_list"/></h2>


<table class="table table-striped auto__table table-condensed text-center">
    <thead>
    <tr>
        <th><fmt:message key="model_main"/></th>
        <th><fmt:message key="brand_main"/></th>
        <th><fmt:message key="colour_main"/></th>
        <th><fmt:message key="description_main"/></th>
        <th><fmt:message key="category_main"/></th>
        <th><fmt:message key="date_main"/></th>
        <th><fmt:message key="price_main"/></th>
    </tr>
    </thead>

    <tbody>
    <c:choose>
    <c:when test="${bikes != null}">
        <c:forEach var="bike" items="${bikes}">
            <tr>
                <td>${bike.name}</td>
                <td>${bike.brand}</td>
                <td>${bike.colour}</td>
                <td>${bike.description}</td>
                <td>${bike.category}</td>
                <td>${bike.registrationDateTime}</td>
                <td>${bike.price}</td>

                <td><a href="/user/${user.id}">edit</a></td>
                <td>
                    <form method="post" action="/user/delete?id=${user.id}">
                        <button type="submit">Delete</button>
                    </form>
                </td>
                <td><a href="/user/orders/${user.id}">Show user`s oders</a></td>
            </tr>
        </c:forEach>
    </c:when>

    <c:otherwise>
        No users
    </c:otherwise>
    </c:choose>
    </tbody>

</table>

<br/>
<a class="btn btn-outline-primary mx-5" href="${pageContext.request.contextPath}/"><fmt:message key="home_page"/></a>

</div>
</body>
</html>