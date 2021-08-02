<%@include file="navbar.jsp"%>

<html>
<head>

    <title><fmt:message key="registration"/></title>

</head>
<body>
<div class="container mt-5">
        <h1><fmt:message key="registration"/></h1><br/>
        <form method="post" action="${pageContext.request.contextPath}/registration">
<%--TODO--%>
            <input  type="text" name="username" required><label style="margin: 0px 3px;"> <fmt:message key="login"/> </label>
            <br/>
            <input class="mt-3"type="email" name="email" required><label style="margin: 0px 3px;"><fmt:message key="email"/></label>
            <br/>
            <input class="mt-3" type="password" name="password" required><label style="margin: 0px 3px;"><fmt:message key="password"/></label>
            <br/>
            <input class="button mt-3" type="submit" value="<fmt:message key="registration"/>">
        </form>
        <br/>
        <a href="${pageContext.request.contextPath}/"><fmt:message key="home_page"/></a>
    </div>
</body>
</html>