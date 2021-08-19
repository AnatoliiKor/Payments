<%@include file="parts/navbar.jsp" %>

<html>
<head>
    <title>User</title>
</head>
<body>
<div class="container mt-2">
<h2><fmt:message key="user"/> ${user.username}</h2>


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
    <c:when test="${user!= null}">
            <tr>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td>${user.email}</td>
                <td>
                    ${user.email}
                            <form method="post" action="/user/?id=${user.id} action='email'">
                                <button type="submit">Change email or password</button>
                            </form>
                </td>
                <c:if test="${role != null && role.equals('ADMIN')}">
                <td>${user.registrationDateTime}</td>
                <td>${user.role}</td>
                </c:if>
                <td><a href="/user/orders?orders=${user.id}">Show oders</a></td>
            </tr>
    </c:when>
    <c:otherwise>
        No user
    </c:otherwise>
    </c:choose>
    </tbody>

</table>

    <a class="btn btn-outline-primary mx-5" href="/bikes"><fmt:message key="my_payments"/></a>

<br/>

<a class="btn btn-outline-primary mx-5" href="${pageContext.request.contextPath}/"><fmt:message key="home_page"/></a>
</div>
</body>

</html>