<%@include file="parts/header.jsp" %>
<%@include file="parts/navbar.jsp" %>

<html>
<head>
    <title><fmt:message key="make_payment"/></title>
</head>
<body>
<div class="container mt-3">
    <c:choose>
        <c:when test="${not empty payment}">
            <h1><fmt:message key="confirm_payment"/></h1><br/>
            ${payment.accountNumber}<br/>
            ${payment.accountName}<br/>
            ${payment.amount/100}<br/>
            ${payment.currency}<br/>
            ${payment.receiver}<br/>
            ${payment.destination}<br/>
            <form method="post" action="${pageContext.request.contextPath}/wallet/payment">
                <input type="hidden" name="action" value="confirm">
                <input class="btn btn-success mt-2" type="submit" value="<fmt:message key="confirm_payment"/>">
            </form>

            <form method="post" action="${pageContext.request.contextPath}/wallet/payment">
                <input type="hidden" name="action" value="cancel">
                <input class="btn btn-outline-warning mt-2" type="submit" value="<fmt:message key="cancel_payment"/>">
            </form>

        </c:when>
        <c:otherwise>
            <h1><fmt:message key="make_payment"/></h1><br/>
            <%@include file="payment/payment_form.jsp"%>
        </c:otherwise>
    </c:choose>


    <br/>

    <%@include file="parts/messages.jsp" %>

    <a href="${pageContext.request.contextPath}/"><fmt:message key="home_page"/></a>
</div>
</body>
</html>