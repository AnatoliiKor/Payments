<%@include file="navbar.jsp" %>

<html>
<head>
    <title>Bikes</title>
</head>
<body>
<div class="container mt-2">
    <h2><fmt:message key="bikes_list"/></h2>

    <c:if test="${message != null}">
        <div class="p-3 mb-2 bg-info text-dark">${message}</div>
    </c:if>

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
                        <td <c:if test="${sort_by.equals('name')}">class="text-info fw-bold"</c:if>>${bike.name}</td>
                        <td <c:if test="${sort_by.equals('brand')}">class="text-info fw-bold"</c:if>>${bike.brand}</td>
                        <td
                                <c:if test="${sort_by.equals('colour')}">class="text-info fw-bold"</c:if>>${bike.colour}</td>
                        <td>${bike.description}</td>
                        <td
                                <c:if test="${sort_by.equals('category')}">class="text-info fw-bold"</c:if>>${bike.category}</td>
                        <td
                                <c:if test="${sort_by.equals('date')}">class="text-info fw-bold"</c:if>>${bike.registrationDateTime}</td>
                        <td <c:if test="${sort_by.equals('price')}">class="text-info fw-bold"</c:if>>${bike.price}</td>

                        <td>
                            <a href="/bike_edit?id=${bike.id}"><fmt:message key="edit"/></a>
                        </td>

                            <%--                <td>--%>
                            <%--                    <form method="post" action="/bike/delete?id=${bike.id}">--%>
                            <%--                        <button type="submit"><fmt:message key="delete"/></button>--%>
                            <%--                    </form>--%>
                            <%--                </td>--%>

                            <%--                <td>--%>
                            <%--                        ${bike.id}--%>
                            <%--                </td>--%>
                    </tr>
                </c:forEach>
            </c:when>

            <c:otherwise>
                No bikes
            </c:otherwise>
        </c:choose>
        </tbody>

    </table>

    <form method="get" action="/bike">
        <div>
            <th><fmt:message key="sort_by"/></th>
            <select name="sort_by" size="1">
                <%--            <option selected="selected"><b>${sort}</b></option>--%>
                <option value="" selected disabled hidden><fmt:message key="choose_here"/></option>
                <option value="price">
                    <th><fmt:message key="price_main"/></th>
                </option>
                <option value="name">
                    <th><fmt:message key="model_main"/></th>
                </option>
                <option value="category">
                    <th><fmt:message key="category_main"/></th>
                </option>
                <option value="colour">
                    <th><fmt:message key="colour_main"/></th>
                </option>
                <option value="brand">
                    <th><fmt:message key="brand_main"/></th>
                </option>
                <option value="date">
                    <th><fmt:message key="date_main"/></th>
                </option>
            </select>
            <th><fmt:message key="order_by"/></th>
            <select name="order" size="1">
                <%--                <option selected="selected"><b>${order}</b></option>--%>
                <option value="ASC">
                    <th><fmt:message key="ascending"/></th>
                </option>
                <option value="DESC">
                    <th><fmt:message key="descending"/></th>
                </option>
            </select>
            <button type="submit"><fmt:message key="apply"/></button>
        </div>
    </form>

    <br/>
    <a class="btn btn-outline-primary mx-5" href="${pageContext.request.contextPath}/"><fmt:message
            key="home_page"/></a>

</div>
</body>
</html>