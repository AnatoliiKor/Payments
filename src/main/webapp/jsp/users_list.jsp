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
        <th>#</th>
        <th><fmt:message key="name"/></th>
        <th><fmt:message key="last_name"/>,<fmt:message key="middle_name"/></th>
        <th>Password</th>
        <th><fmt:message key="email"/></th>
        <th><fmt:message key="phone_number"/></th>
        <th><fmt:message key="registered"/></th>
        <th><fmt:message key="role"/></th>
        <th><fmt:message key="is_active"/></th>
    </tr>
    </thead>

    <tbody>
    <c:choose>
    <c:when test="${not empty users}">
        <c:forEach var="user" items="${users}" varStatus="position" begin="${5 * (pg - 1)}" end="${5 * (pg - 1) + 4}">
            <tr>
                <td scope="row" ><c:out value="${5 * (pg - 1) + position.count}"/>
                </td>
                <td>${user.lastName}</td>
                <td>${user.name}
                    ${not empty user.middleName ? user.middleName : ''}
                </td>
                <td>${user.password}</td>
                <td>${user.email}</td>
                <td>+${user.phoneNumber}</td>
                <td>${user.getFormatedDate()}</td>
                <td>${user.role}</td>
                <td>
                    <input type="checkbox" ${user.active?'checked':""} disabled>
                </td>
                <td><a href="${pageContext.request.contextPath}/user?id=${user.id}"><fmt:message key="profile_nav"/></a></td>
                <td><a href="${pageContext.request.contextPath}/wallet/accounts?user_id=${user.id}"><fmt:message key="accounts"/></a></td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        No users
    </c:otherwise>
    </c:choose>
    </tbody>

</table>

    <ul class="pagination">
        <li class="page-item ${pg == 1 ? 'disabled' : ''}"><a class="page-link" href="/users?pg=${pg-1}"><fmt:message key="previous_main"/></a></li>
        <li class="page-item active"><a class="page-link">${pg}</a></li>
        <li class="page-item ${pg == pg_max? 'disabled' : ''}"><a class="page-link" href="/users?pg=${pg+1}"><fmt:message key="next_main"/></a></li>
    </ul>

    <%@include file="parts/messages.jsp" %>
<br/>

<a class="btn btn-outline-primary mx-5" href="${pageContext.request.contextPath}/admin"><fmt:message key="home_page"/></a>
</div>
</body>

</html>