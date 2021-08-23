<%@include file="parts/navbar.jsp" %>

<html>
<head>
    <title>Accounts</title>
</head>
<body>
<div class="container mt-2">
    <h2><fmt:message key="my_accounts"/></h2>

    <%@include file="parts/messages.jsp" %>

    <form method="get" action="/wallet/accounts">
        <div>
            <th><fmt:message key="sort_by"/></th>
            <select name="sort_by" size="1">
                <c:if test="${sort_by!=null}">
                    <option selected>${sort_by}</option>
                </c:if>
<%--                <option value="" selected disabled hidden><fmt:message key="choose_here"/></option>--%>
                <option value="number" <c:if test="${sort_by.equals('number')}">selected</c:if>>
                    <th><fmt:message key="number"/></th></option>
                <option value="balance" <c:if test="${sort_by.equals('balance')}">selected</c:if>>
                    <th><fmt:message key="balance"/></th></option>
                <option value="accountName" <c:if test="${sort_by.equals('accountName')}">selected</c:if>>
                    <th><fmt:message key="account_name"/></th></option>
                <option value="currency" <c:if test="${sort_by.equals('currency')}">selected</c:if>>
                    <th><fmt:message key="currency"/></th></option>
                <option value="state" <c:if test="${sort_by.equals('state')}">selected</c:if>>
                    <th><fmt:message key="is_active"/></th></option>
                <option value="date" <c:if test="${sort_by.equals('date')}">selected</c:if>>
                    <th><fmt:message key="date_main"/></th></option>
            </select>

            <th><fmt:message key="order_by"/></th>
            <select name="order" size="1">
            <option value="ASC" <c:if test="${order.equals('ASC')}">selected</c:if>>
                <th><fmt:message key="ascending"/></th></option>
            <option value="DESC" <c:if test="${order.equals('DESC')}">selected</c:if>><th>
                <fmt:message key="descending"/></th></option>
            </select>
            <button type="submit"><fmt:message key="apply"/></button>
        </div>
    </form>

    <table class="table table-striped auto__table table-condensed text-center">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th><fmt:message key="number"/></th>
            <th><fmt:message key="balance"/></th>
            <th><fmt:message key="account_name"/></th>
            <th><fmt:message key="currency"/></th>
            <th><fmt:message key="is_active"/></th>
            <th><fmt:message key="date_main"/></th>
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
<%--                        <td <c:if test="${sort_by.equals('currency')}">class="text-info fw-bold"</c:if>>${account.carrency}</td>--%>
                        <td <c:if test="${sort_by.equals('state')}">class="text-info fw-bold"</c:if>>${account.state}</td>
                        <td <c:if test="${sort_by.equals('date')}">class="text-info fw-bold"</c:if>>${account.registrationDateTime}</td>
                        <c:choose>
                            <c:when test="${role != null && role.equals('ADMIN')}"><td><a href="/admin/bike_edit?id=${account.id}"><fmt:message key="edit"/></a></td></c:when>
                            <c:otherwise><td><a href="/buy?id=${account.id}"><fmt:message key="buy_main"/></a></td></c:otherwise>
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
    <a class="btn btn-outline-primary mx-5" href="${pageContext.request.contextPath}/payments"><fmt:message
            key="home_page"/></a>
</div>
</body>
</html>