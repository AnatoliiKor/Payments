<%@include file="navbar.jsp" %>

<html>
<head>
    <title>User</title>
</head>
<body>
<div class="container mt-2">
<h2><fmt:message key="user"/></h2>


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
    <c:when test="${user_authenticated != null}">

        ${user = user_authenticated}
            <tr>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td>${user.email}</td>
                <td><input type="checkbox" ${user.active?'checked':""}></td>
                <td>${user.registrationDateTime}</td>
                <td>${user.role}</td>

                <td><a href="/user/${user.id}">edit</a></td>
                <td>
                    <form method="post" action="/user/delete?id=${user.id}">
                            <%--                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>--%>
                        <button type="submit">Delete</button>
                    </form>
                </td>
                <td><a href="/user/orders/${user.id}">Show user`s oders</a></td>
            </tr>
    </c:when>
    <c:otherwise>
        No user
    </c:otherwise>
    </c:choose>
    </tbody>

</table>

<br/>

<a class="btn btn-outline-primary mx-5" href="${pageContext.request.contextPath}/"><fmt:message key="home_page"/></a>
</div>
</body>

</html>