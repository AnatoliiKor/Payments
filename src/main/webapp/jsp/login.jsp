<%@include file="navbar.jsp"%>


<html>
<head>
    <title>Login in system</title>

</head>
<body>

        <h1><fmt:message key="login_in"/></h1><br/>
        <form method="post" action="${pageContext.request.contextPath}/login">

            <input type="text" name="name"><fmt:message key="login"/><br/>
            <input type="password" name="pass"><fmt:message key="password"/><br/><br/>
            <input class="button" type="submit" value="<fmt:message key="sign_in"/>">
        </form>
        <br/>
        <a href="${pageContext.request.contextPath}/jsp/registration.jsp"><fmt:message key="registration"/></a>
        <br/>
        <br/>
        <a href="${pageContext.request.contextPath}/logout"><fmt:message key="home_page"/></a>

</body>
</html>