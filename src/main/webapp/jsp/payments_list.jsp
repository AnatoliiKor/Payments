<%@include file="parts/header.jsp" %>
<%@include file="parts/navbar.jsp" %>

<html>
<head>
    <title>Payments</title>
</head>
<body>
<div class="container mt-2">
    <h3><fmt:message key="payments"/></h3>

    <%@include file="parts/messages.jsp" %>

    <%@include file="payment/transaction_sort.jsp" %>

    <c:choose>
        <c:when test="${not empty payments}">
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
                <c:forEach var="payment" items="${payments}"
                           varStatus="position" begin="${10 * (page - 1)}" end="${10 * (page - 1) + 9}">
                    <tr>
                        <td scope="row"><c:out value="${10 * (page - 1) + position.count}"/>
                        </td>
                        <td <c:if test="${payment_sort_by.equals('account_number')}">class="text-info fw-bold"</c:if>>
                            UA${payment.accountNumber}</td>
                        <td
                                <c:if test="${payment_sort_by.equals('account_name')}">class="text-info fw-bold"</c:if>>${payment.accountName}</td>
                        <td
                                <c:if test="${payment_sort_by.equals('registered')}">class="text-info fw-bold"</c:if>>${payment.getFormatedDate()}</td>
                        <td>${payment.destination}</td>
                        <td
                                <c:if test="${payment_sort_by.equals('amount')}">class="text-info fw-bold"</c:if>>${payment.amount/100}</td>
                        <td
                                <c:if test="${payment_sort_by.equals('currency')}">class="text-info fw-bold"</c:if>>${payment.currency}</td>
                        <td
                                <c:if test="${payment_sort_by.equals('receiver')}">class="text-info fw-bold"</c:if>>${payment.receiver}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="text-warning fw-bold"><fmt:message key="no_payments"/></div>

        </c:otherwise>
    </c:choose>

    <ul class="pagination my-3">
        <c:if test="${not empty user_id}">
            <li class="page-item ${page == 1 ? 'disabled' : ''}"><a class="page-link"
                                                                  href="/payments?page=${page-1}&user_id=${user_id}"><fmt:message
                    key="previous_main"/></a></li>
            <li class="page-item active"><a class="page-link">${page}</a></li>
            <li class="page-item ${page == pg_max? 'disabled' : ''}"><a class="page-link"
                                                                      href="/payments?page=${page+1}&user_id=${user_id}"><fmt:message
                    key="next_main"/></a></li>
        </c:if>

        <c:if test="${not empty account_number}">
            <li class="page-item ${page == 1 ? 'disabled' : ''}"><a class="page-link"
                                                                  href="/payments?page=${pg-1}&account_number=${account_number}"><fmt:message
                    key="previous_main"/></a></li>
            <li class="page-item active"><a class="page-link">${page}</a></li>
            <li class="page-item ${page == pg_max? 'disabled' : ''}"><a class="page-link"
                                                                      href="/payments?page=${pg+1}&account_number=${account_number}"><fmt:message
                    key="next_main"/></a></li>
        </c:if>

    </ul>

    <c:if test="${'CLIENT'.equals(role)}">
        <br/>
        <a class="btn btn-outline-primary mx-5" href="${pageContext.request.contextPath}/wallet"><fmt:message
                key="home_page"/></a>
    </c:if>
</div>
</body>
</html>