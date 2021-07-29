<%@include file="navbar.jsp"%>

<html>
<head>

    <title><fmt:message key="registration"/></title>

</head>
<body>

        <h1><fmt:message key="registration"/></h1><br/>
        <form method="post" action="${pageContext.request.contextPath}/registration">

            <input type="text" name="name"><fmt:message key="login"/><br/>
            <input type="password" name="pass"><fmt:message key="password"/><br/><br/>
            <input class="button" type="submit" value="<fmt:message key="registration"/>">
        </form>
        <br/>
        <a href="${pageContext.request.contextPath}/logout"><fmt:message key="home_page"/></a>

</body>
</html>