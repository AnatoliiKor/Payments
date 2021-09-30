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

            <%@include file="payment/payment_table.jsp" %>

            <form method="post" action="${pageContext.request.contextPath}/wallet/confirm_payment">
                <input class="btn btn-success mt-2" type="submit" value="<fmt:message key="confirm_payment"/>">
            </form>

            <form method="post" action="${pageContext.request.contextPath}/wallet/cancel_payment">
                <input class="btn btn-outline-warning mt-2" type="submit" value="<fmt:message key="cancel_payment"/>">
            </form>

        </c:when>
        <c:otherwise>
            <h1><fmt:message key="make_payment"/></h1><br/>
            <%@include file="payment/payment_form.jsp" %>
            <a class="btn btn-outline-warning mb-4" href="/wallet"><fmt:message
                    key="cancel_payment"/></a>
        </c:otherwise>
    </c:choose>
    <br/>

    <%@include file="parts/messages.jsp" %>

</div>
</body>
</html>