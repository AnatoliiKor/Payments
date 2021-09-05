<%@include file="parts/header.jsp"%>
<%@include file="parts/navbar.jsp" %>

<html>
<head>
    <title>Payments</title>
</head>
<body>
<div class="container mt-2">
    <h3><fmt:message key="payments"/></h3>

    <%@include file="parts/messages.jsp" %>

    <%@include file="payment/payment_sort.jsp" %>

    <table class="table table-striped auto__table table-condensed text-center">
        <thead>
        <tr>
            <th>#</th>
            <th><fmt:message key="account_number"/></th>
            <th><fmt:message key="account_name"/></th>
            <th><fmt:message key="date"/></th>
            <th><fmt:message key="payment_destination"/></th>
            <th><fmt:message key="payment_amount"/></th>
            <th><fmt:message key="currency"/></th>
            <th><fmt:message key="receiver_account"/></th>
        </tr>
        </thead>

        <tbody>
        <c:choose>
            <c:when test="${not empty payments}">
                <c:forEach var="payment" items="${payments}"
                    varStatus="position" begin="${5 * (pg - 1)}" end="${5 * (pg - 1) + 4}">
                    <tr >
                        <td scope="row" ><c:out value="${5 * (pg - 1) + position.count}"/>
                        </td>
                        <td <c:if test="${sort_by.equals('account_number')}">class="text-info fw-bold"</c:if>>UA${payment.accountNumber}</td>
                        <td <c:if test="${sort_by.equals('account_name')}">class="text-info fw-bold"</c:if>>${payment.accountName}</td>
                        <td <c:if test="${sort_by.equals('registered')}">class="text-info fw-bold"</c:if>>${payment.getFormatedDate()}</td>
                        <td>${payment.destination}</td>
                        <td <c:if test="${sort_by.equals('amount')}">class="text-info fw-bold"</c:if>>${payment.amount/100}</td>
                        <td <c:if test="${sort_by.equals('currency')}">class="text-info fw-bold"</c:if>>${payment.currency}</td>
                        <td <c:if test="${sort_by.equals('receiver')}">class="text-info fw-bold"</c:if>>${payment.receiver}</td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <fmt:message key="no_payments"/>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>

    <ul class="pagination">
        <li class="page-item ${pg == 1 ? 'disabled' : ''}"><a class="page-link" href="/payments?pg=${pg-1}&user_id=${user_id}"><fmt:message key="previous_main"/></a></li>
        <li class="page-item active"><a class="page-link">${pg}</a></li>
        <li class="page-item ${pg == pg_max? 'disabled' : ''}"><a class="page-link" href="/payments?pg=${pg+1}&user_id=${user_id}"><fmt:message key="next_main"/></a></li>
    </ul>

    <c:if test="${'CLIENT'.equals(role)}">
    <br/>
    <a class="btn btn-outline-primary mx-5" href="${pageContext.request.contextPath}/wallet"><fmt:message
            key="home_page"/></a>
    </c:if>
</div>
</body>
</html>