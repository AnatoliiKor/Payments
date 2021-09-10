<%@include file="parts/header.jsp"%>
<%@include file="parts/navbar.jsp" %>

<html>
<head>
    <title>Transactions</title>
</head>
<body>
<div class="container mt-2">
    <h3><fmt:message key="payments"/></h3>

    <%@include file="parts/messages.jsp" %>

    <%@include file="payment/payment_sort.jsp" %>

    <c:choose>
    <c:when test="${not empty transactions}">
    <table class="table table-striped auto__table table-condensed text-center">
        <thead>
        <tr>
            <th>#</th>
            <th><fmt:message key="payer"/></th>
            <th><fmt:message key="date"/></th>
            <th><fmt:message key="payment_amount"/></th>
            <th><fmt:message key="currency"/></th>
            <th><fmt:message key="receiver"/></th>
            <th><fmt:message key="payment_destination"/></th>
            <th></th>
        </tr>
        </thead>

        <tbody>
                <c:forEach var="transaction" items="${transactions}"
                           varStatus="position" begin="${10 * (pg - 1)}" end="${10 * (pg - 1) + 9}">
                    <tr >
                        <td scope="row" ><c:out value="${10 * (pg - 1) + position.count}"/>
                        </td>
                        <td <c:if test="${transaction_sort_by.equals('payer')}">class="text-info fw-bold"</c:if>>UA${transaction.payer}</td>
                        <td <c:if test="${transaction_sort_by.equals('registered')}">class="text-info fw-bold"</c:if>>${transaction.getFormatedDate()}</td>
                        <td <c:if test="${transaction_sort_by.equals('amount')}">class="text-info fw-bold"</c:if>>${transaction.amount/100}</td>
                        <td <c:if test="${transaction_sort_by.equals('currency')}">class="text-info fw-bold"</c:if>>${transaction.currency}</td>
                        <td <c:if test="${transaction_sort_by.equals('receiver')}">class="text-info fw-bold"</c:if>>UA${transaction.receiver}</td>
                        <td>${transaction.destination}</td>
                        <td><a href="${pageContext.request.contextPath}/transaction_details"><fmt:message
                                key="details"/></a></td>
                    </tr>
                </c:forEach>
        </tbody>
    </table>
    </c:when>
        <c:otherwise>
            <div class="text-warning fw-bold"><fmt:message key="no_payments"/></div>

        </c:otherwise>
    </c:choose>



    <%
        String parameter = "";
        if (request.getParameter("user_id") != null) {
            parameter = "&user_id=" + request.getParameter("user_id");
            request.setAttribute("parameter", parameter);
        }
        if (request.getParameter("account_number") != null) {
            parameter = "&account_number=" + request.getParameter("account_number");
        }
        request.setAttribute("parameter", parameter);
    %>
    <ul class="pagination my-3">
<%--        <c:if test="${not empty user_id}">--%>
<%--            <li class="page-item ${pg == 1 ? 'disabled' : ''}"><a class="page-link" href="/transactions?pg=${pg-1}&user_id=${user_id}"><fmt:message key="previous_main"/></a></li>--%>
<%--            <li class="page-item active"><a class="page-link">${pg}</a></li>--%>
<%--            <li class="page-item ${pg == pg_max? 'disabled' : ''}"><a class="page-link" href="/transactions?pg=${pg+1}&user_id=${user_id}"><fmt:message key="next_main"/></a></li>--%>
<%--        </c:if>--%>

<%--        <c:if test="${not empty account_number}">--%>
<%--            <li class="page-item ${pg == 1 ? 'disabled' : ''}"><a class="page-link" href="/transactions?pg=${pg-1}&account_number=${account_number}"><fmt:message key="previous_main"/></a></li>--%>
<%--            <li class="page-item active"><a class="page-link">${pg}</a></li>--%>
<%--            <li class="page-item ${pg == pg_max? 'disabled' : ''}"><a class="page-link" href="/transactions?pg=${pg+1}&account_number=${account_number}"><fmt:message key="next_main"/></a></li>--%>
<%--        </c:if>--%>

<%--        <c:if test="${empty account_number}">--%>
<%--            <li class="page-item ${pg == 1 ? 'disabled' : ''}"><a class="page-link" href="/transactions?pg=${pg-1}"><fmt:message key="previous_main"/></a></li>--%>
<%--            <li class="page-item active"><a class="page-link">${pg}</a></li>--%>
<%--            <li class="page-item ${pg == pg_max? 'disabled' : ''}"><a class="page-link" href="/transactions?pg=${pg+1}"><fmt:message key="next_main"/></a></li>--%>
<%--        </c:if>--%>

            <li class="page-item ${pg == 1 ? 'disabled' : ''}"><a class="page-link" href="/transactions?pg=${pg-1}${parameter}&account_type=payer"><fmt:message key="previous_main"/></a></li>
            <li class="page-item active"><a class="page-link">${pg}</a></li>
            <li class="page-item ${pg == pg_max? 'disabled' : ''}"><a class="page-link" href="/transactions?pg=${pg+1}${parameter}&account_type=payer"><fmt:message key="next_main"/></a></li>

    </ul>

    <c:if test="${'CLIENT'.equals(role)}">
    <br/>
    <a class="btn btn-outline-primary mx-5" href="${pageContext.request.contextPath}/wallet"><fmt:message
            key="home_page"/></a>
    </c:if>
</div>
</body>
</html>