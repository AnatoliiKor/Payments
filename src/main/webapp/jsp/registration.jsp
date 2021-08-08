<%@include file="navbar.jsp"%>

<html>
<head>
    <title><fmt:message key="registration"/></title>
</head>
<body>
<div class="container mt-5">
        <h1><fmt:message key="registration"/></h1><br/>

        <form method="post" action="${pageContext.request.contextPath}/registration">
            <input  type="text" name="username" value="<c:if test="${username!=null}">${username}</c:if>" required>
                <label style="margin: 0 3px;"> <fmt:message key="login"/> </label>
            <br/>
            <input class="mt-3" type="email" name="email" value="<c:if test="${email!=null}">${email}</c:if>" required>
                <label style="margin: 0 3px;"><fmt:message key="email"/></label>
            <br/>
            <input class="mt-3" type="password" name="password" required>
                <label style="margin: 0 3px;"><fmt:message key="password"/></label>
            <br/>
            <input class="button mt-3" type="submit" value="<fmt:message key="registration"/>">
        </form>
        <br/>
    <c:if test="${error!=null}">
        <div class="alert alert-warning" role="alert">
                ${error}
        </div>
    </c:if>
        <a href="${pageContext.request.contextPath}/"><fmt:message key="home_page"/></a>
    </div>
</body>
</html>