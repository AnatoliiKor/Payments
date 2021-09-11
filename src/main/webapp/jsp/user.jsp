<%@include file="parts/header.jsp" %>
<%@include file="parts/navbar.jsp" %>

<html>
<head>
    <title>User</title>
</head>
<body>

<div class="container mt-2">

    <h2><fmt:message key="profile_nav"/></h2>

    <%@include file="parts/messages.jsp" %>

    <c:if test="${not empty user}">

        <table class="table table-striped auto__table table-condensed text-center">
            <tbody>
            <tr>
                <td><fmt:message key="last_name"/></td>
                <td>${user.lastName}</td>
            </tr>
            <tr>
                <td><fmt:message key="name"/></td>
                <td>${user.name}</td>
            </tr>
            <tr>
                <td><fmt:message key="middle_name"/></td>
                <td>${not empty user.middleName ? user.middleName : ''}</td>
            </tr>
            <tr>
                <td><fmt:message key="email"/></td>
                <td>${user.email}</td>
            </tr>
            <tr>
                <td><fmt:message key="phone_number"/></td>
                <td>+${user.phoneNumber}</td>
            </tr>
            <tr>
                <td><fmt:message key="registered"/></td>
                <td>${user.getFormatedDate()}</td>
            </tr>

            <c:if test="${!user.active}">


            </c:if>

            <c:if test="${role.equals('ADMIN') && user.id != user_auth.id}">
                <tr>
                    <td><fmt:message key="role"/></td>
                    <td>${user.role}</td>
                </tr>
                <tr>
                    <td><fmt:message key="is_active"/></td>
                    <td>
                        <input class="form-check-input" type="checkbox" name="status" value="TRUE" ${user.active?'checked':""} disabled>

                        <div class="text-info fw-bold"><fmt:message
                                key="${user.active?'is_active':'blocked'}"/></div>

                        <form method="post" action="${pageContext.request.contextPath}/admin/user">
                            <input type="hidden" name="id" value="${user.id}"/>
                            <c:choose>
                                <c:when test="${user.active}">
                                    <input type="hidden" name="status" value="false"/>
                                    <button class="btn btn-outline-danger mt-2"
                                            type="submit"><fmt:message key="block"/>
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" name="status" value="true"/>
                                    <button class="btn btn-outline-success mt-2"
                                            type="submit"><fmt:message key="unblock"/>
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </td>

                </tr>
                <tr><a class="btn btn-outline-primary m-2" href="${pageContext.request.contextPath}/wallet/accounts?user_id=${user.id}"><fmt:message
                        key="accounts"/></tr>
                <tr><a class="btn btn-outline-success m-2" href="${pageContext.request.contextPath}/wallet/payments?user_id=${user.id}"><fmt:message
                        key="payments"/></a></tr>
            </c:if>
            </tbody>
        </table>
    </c:if>

</div>
</body>

</html>