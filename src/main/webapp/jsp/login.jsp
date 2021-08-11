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
            <input class="mt-3" type="text" name="username" value="<c:if test="${username!=null}">${username}</c:if>">
                <label style="margin: 0 3px;"> <fmt:message key="login" /> </label>
            <br/>
            <input class="mt-3" type="password" name="password" value="1"><label style="margin: 0 3px;"> <fmt:message key="password"/></label>
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