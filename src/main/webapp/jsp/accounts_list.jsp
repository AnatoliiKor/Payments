<%@include file="parts/navbar.jsp" %>

<html>
<head>
    <title>Accounts</title>
</head>
<body>
<div class="container mt-2">
    <h3><fmt:message key="accounts"/></h3>

    <%@include file="parts/messages.jsp" %>

    <%@include file="account/sort.jsp" %>

    <table class="table table-striped auto__table table-condensed text-center">
        <thead>
        <tr>
            <th>#</th>
            <th><fmt:message key="number"/></th>
            <th><fmt:message key="balance"/></th>
            <th><fmt:message key="account_name"/></th>
            <th><fmt:message key="currency"/></th>
            <th><fmt:message key="is_active"/></th>
            <%--            <th><fmt:message key="opened"/></th>--%>
        </tr>
        </thead>

        <tbody>
        <c:choose>
            <c:when test="${not empty accounts}">
                <c:forEach var="account" items="${accounts}"
                    varStatus="position" begin="${5 * (pg - 1)}" end="${5 * (pg - 1) + 4}">
                    <tr >
                        <td scope="row" ><c:out value="${5 * (pg - 1) + position.count}"/>
                        </td>
                        <td <c:if test="${sort_by.equals('number')}">class="text-info fw-bold"</c:if>>UA${account.number}</td>
                        <td <c:if test="${sort_by.equals('balance')}">class="text-info fw-bold"</c:if>>${account.balance/100}</td>
                        <td <c:if test="${sort_by.equals('account_name')}">class="text-info fw-bold"</c:if>>${account.accountName}</td>
                        <td <c:if test="${sort_by.equals('currency')}">class="text-info fw-bold"</c:if>>${account.currency}</td>
                        <td <c:if test="${sort_by.equals('active')}">class="text-info fw-bold"</c:if>><input
                                type="checkbox" ${account.active?'checked':""} disabled></td>
                        <td>
                            <a href="/wallet/account?id=${account.id}"><fmt:message
                                    key="${'ADMIN'.equals(role)?'edit':'refill_balance'}"/></a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <fmt:message key="no_accounts"/>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>

    <ul class="pagination">
        <li class="page-item ${pg == 1 ? 'disabled' : ''}"><a class="page-link" href="/accounts?pg=${pg-1}&user_id=${user_id}"><fmt:message key="previous_main"/></a></li>
        <li class="page-item active"><a class="page-link">${pg}</a></li>
        <li class="page-item ${pg == pg_max? 'disabled' : ''}"><a class="page-link" href="/accounts?pg=${pg+1}&user_id=${user_id}"><fmt:message key="next_main"/></a></li>
    </ul>

    <c:if test="${'CLIENT'.equals(role)}">
    <br/>
    <a class="btn btn-outline-primary mx-5" href="${pageContext.request.contextPath}/wallet"><fmt:message
            key="home_page"/></a>
    </c:if>
</div>
</body>
</html>