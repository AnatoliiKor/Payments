<%@include file="parts/navbar.jsp" %>

<html>
<head>
    <title>Users</title>
</head>
<body>
<div class="container mt-2">
<h2><fmt:message key="users_list"/></h2>


<table class="table table-striped auto__table table-condensed text-center">
    <thead>
    <tr>
        <th><fmt:message key="name"/></th>
        <th>Password</th>
        <th><fmt:message key="email"/></th>
        <th><fmt:message key="is_active"/></th>
        <th><fmt:message key="registered"/></th>
        <th><fmt:message key="role"/></th>

    </tr>
    </thead>

    <tbody>
    <c:choose>
    <c:when test="${users != null}">
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td>${user.email}</td>
                <td>
<%--                    <input type="checkbox" ${user.active?'checked':""} disabled>--%>
                    <form method="post" action="/user/?id=${user.id} action='active'">
                        <input type="checkbox" ${user.active?'checked':""}>
                        <button type="submit"><fmt:message key="apply"/></button>
                    </form>
                </td>
                <td>${user.registrationDateTime}</td>
                <td>${user.role}</td>
                <td><a href="/user?id=${user.id}">edit</a></td>
                <td>
                    <form method="post" action="/user/delete?id=${user.id}">
                            <%--                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>--%>
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

<a class="btn btn-outline-primary mx-5" href="${pageContext.request.contextPath}/admin"><fmt:message key="home_page"/></a>
</div>
</body>

</html>