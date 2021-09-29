<%@include file="parts/header.jsp" %>
<%@include file="parts/navbar.jsp" %>

<html>
<head>
    <title><fmt:message key="registration"/></title>
</head>
<body>
<div class="container mt-3">
    <h1><fmt:message key="registration"/></h1><br/>

    <form method="post" action="${pageContext.request.contextPath}/registration">

        <input class="mt-3" type="text" name="last_name" value="i<c:if test="${last_name!=null}">${last_name}</c:if>"
               required>
        <label style="margin: 0 3px;"> <fmt:message key="last_name"/> </label>
        <br/>

        <input class="mt-3" type="text" name="name" value="i<c:if test="${name!=null}">${name}</c:if>" required>
        <label style="margin: 0 3px;"> <fmt:message key="name"/> </label>
        <br/>

        <input class="mt-3" type="text" name="middle_name"
               value="<c:if test="${middle_name!=null}">${middle_name}</c:if>">
        <label style="margin: 0 3px;"> <fmt:message key="middle_name"/> </label>
        <br/>

        +38 <input class="mt-3" type="tel" name="phone_number" pattern="[0-9]{10}"
                   value="<c:if test="${phone_number!=null}">${phone_number}</c:if>"
                   minlength="10" maxlength="10" size="10" placeholder="0000000000" required>
        <label style="margin: 0 3px;"> <fmt:message key="phone_number"/> </label>
        <br/>

        <input class="mt-3" type="email" name="email" value="email@c.com"
        >
        <label style="margin: 0 3px;"><fmt:message key="email"/></label>
        <br/>

        <input class="mt-3" type="password" name="password" value="1" required>
        <label style="margin: 0 3px;"><fmt:message key="password"/></label>
        <br/>

        <input class="btn btn-outline-primary mt-3" type="submit" value="<fmt:message key="registration"/>">

    </form>
    <br/>

    <%@include file="parts/messages.jsp" %>

    <a href="${pageContext.request.contextPath}/"><fmt:message key="home_page"/></a>
</div>
</body>
</html>