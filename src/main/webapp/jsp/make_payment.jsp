<%@include file="parts/header.jsp" %>
<%@include file="parts/navbar.jsp" %>

<html>
<head>
    <title><fmt:message key="make_payment"/></title>
</head>
<body>
<div class="container mt-3">
    <h1><fmt:message key="make_payment"/></h1><br/>

    <form method="post" action="${pageContext.request.contextPath}/wallet/payment">
        <input type="hidden" name="action" value="prepare">

        UA<input class="m-2" max="99999999999" min="10000000000" name="recipient_account" required
                 type="number" value="<c:if test="${recipient_account!=null}">${recipient_account}</c:if>">
        <label> <fmt:message key="recipient_account"/> </label>
        <br/>

        <%@include file="payment/account_select.jsp"%>


        <input class="m-2" type="number" step=".01" min="0.01" name="payment_amount"
               value="i<c:if test="${payment_amount!=null}">${payment_amount}</c:if>" required>
        <label> <fmt:message key="payment_amount"/> </label>
        <br/>
        <input class="button mt-3" type="submit" value="<fmt:message key="make_payment"/>">
    </form>
    <br/>

    <%@include file="parts/messages.jsp" %>

    <a href="${pageContext.request.contextPath}/"><fmt:message key="home_page"/></a>
</div>
</body>
</html>