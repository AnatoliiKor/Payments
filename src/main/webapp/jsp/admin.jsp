<%@include file="parts/navbar.jsp" %>

<html>
<head>
    <title>Admin</title>
</head>
<body>

<div class="container mt-4">

    <%@include file="parts/messages.jsp" %>

    <div mt-2>
        <c:choose>
        <c:when test="${accounts != null}">
        <h3>
            <div class="text-warning fw-bold mt-5">
                <fmt:message key="to_do"/>
            </div>
        </h3>
        <table class="table table-striped auto__table table-condensed text-center">
            <thead>
            <tr>
                <th><fmt:message key="number"/></th>
                <th><fmt:message key="balance"/></th>
                <th><fmt:message key="account_name"/></th>
                <th><fmt:message key="currency"/></th>
                <th><fmt:message key="opened"/></th>
                <th><fmt:message key="is_active"/></th>
                <th><fmt:message key="to_do"/></th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="account" items="${accounts}">
                <tr>
                    <td>UA${account.number}</td>
                    <td>${account.balance/100}</td>
                    <td>${account.accountName}</td>
                    <td>${account.currency}</td>
                    <td>${account.getFormatedDate()}</td>
                    <td><input type="checkbox" ${account.active?'checked':""} disabled>
                        <div class="text-info fw-bold"><fmt:message
                                key="${account.active?'is_active':'blocked'}"/></div>
                    </td>

                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/admin/account">
                            <input type="hidden" name="id_to_do" value="${account.id}"/>
                            <input type="hidden" name="action" value="active"/>

                            <c:if test="${account.action==2}">
                                <input type="hidden" name="is_active" value="false"/>
                                <button class="btn btn-outline-danger mt-2"
                                        type="submit"><fmt:message key="block"/>
                                </button>
                            </c:if>
                            <c:if test="${account.action==1}">
                                <input type="hidden" name="is_active" value="true"/>
                                <button class="btn btn-outline-success mt-2"
                                        type="submit"><fmt:message key="unblock"/>
                                </button>
                            </c:if>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </c:when>
            <c:otherwise>
                <h4>
                    <div class="text-success fw-bold mt-5"><fmt:message key="no_accounts"/> <fmt:message key="to_do"/></div>
                </h4>
            </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>

</div>
</body>
</html>
