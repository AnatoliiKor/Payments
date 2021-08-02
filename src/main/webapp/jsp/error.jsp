<%@include file="navbar.jsp"%>

<html>
<head>
    <title>Errors</title>
</head>
<body>
<%--    <title><fmt:message key="registration"/></title>--%>
<h2>ERROR</h2>
<div class="alert alert-warning" role="alert">
    ${error} because ${error_reason}
</div>


</body>
</html>