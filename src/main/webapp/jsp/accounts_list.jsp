<%@include file="parts/header.jsp" %>
<%@include file="parts/navbar.jsp" %>

<html>
<head>
    <title>Accounts</title>
</head>
<body>
<div class="container mt-2">
    <h3><fmt:message key="accounts"/></h3>

    <%@include file="parts/messages.jsp" %>


    <c:choose>
    <c:when test="${not empty accounts}">

    <%@include file="account/sort.jsp" %>

    <ul class="pagination">
        <li class="page-item ${page == 1 ? 'disabled' : ''}"><a class="page-link"
                                                                href="${pageContext.request.contextPath}/wallet/accounts?page=${page-1}&user_id=${user_id}&sort_by=${sort_by}&order=${order}"><fmt:message
                key="previous_main"/></a></li>
        <li class="page-item active"><a class="page-link">${page}</a></li>
        <li class="page-item ${page == pg_max? 'disabled' : ''}"><a class="page-link"
                                                                      href="${pageContext.request.contextPath}/wallet//accounts?page=${page+1}&user_id=${user_id}&sort_by=${sort_by}&order=${order}"><fmt:message
                key="next_main"/></a></li>
    </ul>

    <table class="table table-striped auto__table table-condensed text-center">
        <thead>
        <tr>
            <th>#</th>
            <th><fmt:message key="number"/></th>
            <th><fmt:message key="balance"/></th>
            <th><fmt:message key="account_name"/></th>
            <th><fmt:message key="currency"/></th>
            <th><fmt:message key="is_active"/></th>
            <th></th>
            <th></th>
            <th></th>

        </tr>
        </thead>

        <tbody>
        <c:forEach var="account" items="${accounts}"
                   varStatus="position" begin="${10 * (page - 1)}" end="${10 * (page - 1) + 9}">
            <tr>
                <td scope="row"><c:out value="${10 * (page - 1) + position.count}"/>
                </td>
                <td <c:if test="${sort_by.equals('number')}">class="text-info fw-bold"</c:if>>UA${account.number}</td>
                <td
                        <c:if test="${sort_by.equals('balance')}">class="text-info fw-bold"</c:if>>${account.balance/100}</td>
                <td
                        <c:if test="${sort_by.equals('account_name')}">class="text-info fw-bold"</c:if>>${account.accountName}</td>
                <td <c:if test="${sort_by.equals('currency')}">class="text-info fw-bold"</c:if>>${account.currency}</td>
                <td <c:if test="${sort_by.equals('active')}">class="text-info fw-bold"</c:if>><input
                        class="form-check-input" type="checkbox" ${account.active?'checked':""} disabled></td>
                <td>
                    <a href="${pageContext.request.contextPath}/wallet/transactions?account_number=${account.number}&account_type=payer"><fmt:message
                            key="payments"/></a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/wallet/transactions?account_number=${account.number}&account_type=receiver"><fmt:message
                            key="inflows"/></a>
                </td>

                <td>
                    <a href="${pageContext.request.contextPath}/wallet/account?id=${account.id}"><fmt:message
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

    <c:if test="${'CLIENT'.equals(role)}">
        <br/>
        <a class="btn btn-outline-primary mx-5" href="${pageContext.request.contextPath}/wallet"><fmt:message
                key="home_page"/></a>
    </c:if>
</div>
</body>
</html>