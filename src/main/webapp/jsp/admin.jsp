<%@include file="parts/navbar.jsp"%>

<html>
<head>
    <title>Admin</title>
</head>
<body>

<div class="container mt-5">
    <h2>Admin page</h2>
<a class="btn btn-outline-primary mx-5" href="/admin/users"><fmt:message key="users_list"/></a>
<a class="btn btn-outline-warning mx-5" href="/jsp/new_bike.jsp"><fmt:message key="new_pyment"/></a>
<a class="btn btn-outline-primary mx-5" href="/bikes"><fmt:message key="my_payments"/></a>

</div>
</body>
</html>
