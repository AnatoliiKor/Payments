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
            <c:when test="${accounts != null}">
                <c:forEach var="account" items="${accounts}" >
<%--                           varStatus="position" begin="${10 * (pg - 1)}" end="${10 * (pg - 1) + 9}">--%>
                    <tr>
<%--                        <td scope="row">--%>
<%--                            <c:out value="${10 * (pg - 1) + position.count}" />--%>
<%--                        </td>--%>
                        <td <c:if test="${sort_by.equals('number')}">class="text-info fw-bold"</c:if>>${account.number}</td>
                        <td <c:if test="${sort_by.equals('balance')}">class="text-info fw-bold"</c:if>>${account.balance/100}</td>
                        <td <c:if test="${sort_by.equals('account_name')}">class="text-info fw-bold"</c:if>>${account.accountName}</td>
                        <td <c:if test="${sort_by.equals('currency')}">class="text-info fw-bold"</c:if>>${account.currency}</td>
                        <td <c:if test="${sort_by.equals('active')}">class="text-info fw-bold"</c:if>><input type="checkbox" ${account.active?'checked':""} disabled></td>
<%--                        <td <c:if test="${sort_by.equals('registered')}">class="text-info fw-bold"</c:if>>${account.getFormatedDate()}</td>--%>
                        <td> <a href="/wallet/account?id=${account.id}"><fmt:message key="refill_balance"/></a></td>
                        <c:choose>
                            <c:when test="${role != null && role.equals('ADMIN')}"><td><a href="/admin/bike_edit?id=${account.id}"><fmt:message key="edit"/></a></td></c:when>
                            <c:otherwise>
                                <td>

                                </td>
                            </c:otherwise>
                        </c:choose>
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
        <li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
        <li class="page-item ${pg == 1 ? 'active' : ''}"><a class="page-link" href="/bikes?pg=1">1</a></li>
        <li class="page-item ${pg == 2 ? 'active' : ''}"><a class="page-link" href="/bikes?pg=2">2</a></li>
        <li class="page-item ${pg == 3 ? 'active' : ''}"><a class="page-link" href="/bikes?pg=3">3</a></li>
        <li class="page-item ${pg == 4 ? 'active' : ''}"><a class="page-link" href="/bikes?pg=4">Next</a></li>
    </ul>

    <br/>
    <a class="btn btn-outline-primary mx-5" href="${pageContext.request.contextPath}/wallet"><fmt:message
            key="home_page"/></a>
</div>
</body>
</html>