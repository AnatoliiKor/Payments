<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">--%>
<%@include file="parts/navbar.jsp"%>

<html>
<head>
    <title>Wallet</title>
</head>
<body>
<div class="container mt-5">
<a class="btn btn-outline-primary mx-5" href="/wallet/new_account"><fmt:message key="new_account"/></a>


<a class="btn btn-outline-primary mx-5" href="/admin/users"><fmt:message key="users_list"/></a>
<a class="btn btn-outline-primary mx-5" href="/bikes"><fmt:message key="bikes_list"/></a>

</div>
</body>
</html>
