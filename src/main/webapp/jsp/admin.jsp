<%@include file="parts/navbar.jsp"%>

<html>
<head>
    <title>Admin</title>
</head>
<body>

<div class="container mt-2">
    <h2>Admin page</h2>

    <%@include file="parts/messages.jsp" %>

<a class="btn btn-outline-primary mx-5" href="/admin/users"><fmt:message key="users_list"/></a>
<a class="btn btn-outline-warning mx-5" href="/jsp/new_bike.jsp"><fmt:message key="new_pyment"/></a>
<a class="btn btn-outline-primary mx-5" href="/bikes"><fmt:message key="my_payments"/></a>

<div mt-2>

        <c:choose>
            <c:when test="${accounts != null}">
            <h3><fmt:message key="to_do"/></h3>
                <table class="table table-striped auto__table table-condensed text-center">
                    <thead>
                    <tr>
                        <th><fmt:message key="number"/></th>
                        <th><fmt:message key="balance"/></th>
                        <th><fmt:message key="account_name"/></th>
                        <th><fmt:message key="currency"/></th>
                        <th><fmt:message key="opened"/></th>
                        <th><fmt:message key="is_active"/></th>
                        <th><fmt:message key="to_do"/></th>
                    </tr>
                    </thead>

                    <tbody>
                <c:forEach var="account" items="${accounts}" >
                    <%--                           varStatus="position" begin="${10 * (pg - 1)}" end="${10 * (pg - 1) + 9}">--%>
                    <tr>
                            <%--                        <td scope="row">--%>
                            <%--                            <c:out value="${10 * (pg - 1) + position.count}" />--%>
                            <%--                        </td>--%>
                        <td>${account.number}</td>
                        <td>${account.balance/100}</td>
                        <td>${account.accountName}</td>
                        <td>${account.currency}</td>
                        <td>${account.getFormatedDate()}</td>
                        <td><input type="checkbox" ${account.active?'checked':""} disabled></td>
                        <td>
                            <form method="post" action="${pageContext.request.contextPath}/admin/account">
                                <input type="hidden" name="id_to_do" value="${account.id}"/>
                                <input type="hidden" name="action" value="active"/>
                                <c:if test="${account.action==2}">
                                    <input type="hidden" name="is_active" value="false"/>
                                        <button type="submit"><fmt:message key="block"/></button>
                                </c:if>
                                <c:if test="${account.action==1}">
                                        <input type="hidden" name="is_active" value="true"/>
                                        <button type="submit"><fmt:message key="unblock"/></button>
                                </c:if>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <h3><fmt:message key="no_accounts"/> <fmt:message key="to_do"/></h3>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>

    <ul class="pagination">
        <li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
        <li class="page-item ${pg == 1 ? 'active' : ''}"><a class="page-link" href="/bikes?pg=1">1</a></li>
        <li class="page-item ${pg == 2 ? 'active' : ''}"><a class="page-link" href="/bikes?pg=2">2</a></li>
        <li class="page-item ${pg == 3 ? 'active' : ''}"><a class="page-link" href="/bikes?pg=3">3</a></li>
        <li class="page-item ${pg == 4 ? 'active' : ''}"><a class="page-link" href="/bikes?pg=4">Next</a></li>
    </ul>










</div>
</body>
</html>
