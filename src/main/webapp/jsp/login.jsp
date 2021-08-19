<%@include file="parts/navbar.jsp"%>


<html>
<head>
    <title>Login in system</title>

</head>
<body>

<%@include file="parts/messages.jsp" %>

<div class="container mt-5">
        <h1><fmt:message key="login_in"/></h1><br/>

        <form method="post" action="${pageContext.request.contextPath}/login">
            +38 <input class="mt-3" type="tel" name="phone_number" pattern="[0-9]{10}"
                       value="<c:if test="${phone_number!=null}">${phone_number}</c:if>"
                       minlength="10" maxlength="10" size="10" placeholder="0000000000" required>
                <label style="margin: 0 3px;"> <fmt:message key="phone_number"/> </label>
            <br/>
            <input class="mt-3" type="password" name="password" value="1">
                <label style="margin: 0 3px;"> <fmt:message key="password"/></label>
            <br/>
            <input class="button mt-3" type="submit" value="<fmt:message key="sign_in"/>">
        </form>
        <br/>
        <a href="${pageContext.request.contextPath}/jsp/registration.jsp"><fmt:message key="registration"/></a>
        <br/>
        <br/>
        <a href="${pageContext.request.contextPath}/"><fmt:message key="home_page"/></a>
</div>
</body>
</html>