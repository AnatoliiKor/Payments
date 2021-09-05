<%@include file="parts/header.jsp" %>
<%@include file="parts/navbar.jsp" %>

<html>
<head>
    <title>Account</title>
</head>
<body>

<div class="container mt-2">

    <%@include file="parts/messages.jsp" %>

    <h2><fmt:message key="account"/></h2>
    <div class="row">
        <div class="col">
            <c:if test="${not empty account}">
                <%@include file="account/account_table.jsp"%>
            </c:if>
        </div>
        <div class="col">

            <c:if test="${'CLIENT'.equals(role)}">
                <%@include file="account/refill.jsp" %>
            </c:if>

            <%@include file="account/blocking.jsp" %>

            <div><a class="btn btn-outline-success mt-4" href="wallet/payments?account_number=${account.number}"><fmt:message key="payments"/></a></div>
        </div>
    </div>
</div>
</body>

</html>