<%@include file="parts/navbar.jsp" %>

<html>
<head>
    <title>Account</title>
</head>
<body>

<div class="container mt-2">

<%@include file="parts/messages.jsp" %>



    <div class="row">
        <div class="col">
        <c:if test="${not empty account}">
        <table class="table table-striped auto__table table-condensed ">
            <tbody>
            <tr>
                <td><fmt:message key="number"/></td>
                <td>${account.number}</td>
            </tr>
            <tr>
                <td><fmt:message key="balance"/></td>
                <td>${account.balance}</td>
            </tr>
            <tr>
                <td><fmt:message key="account_name"/></td>
                <td>${account.accountName}</td>
            </tr>
            <tr>
                <td><fmt:message key="currency"/></td>
                <td>${account.currency}</td>
            </tr>
            <tr>
                <td><fmt:message key="is_active"/></td>
                <td><input type="checkbox" ${account.active?'checked':""} disabled></td>
            </tr>
            <tr>
                <td><fmt:message key="opened"/></td>
                <td>${account.getFormatedDate()}</td>
            </tr>

            <c:if test="${role.equals('ADMIN')}">
                <tr>
                    <td><fmt:message key="role"/></td>
                    <td>${user.role}</td>
                </tr>
                <tr>
                    <td><fmt:message key="is_active"/></td>
                    <td>
                        <input type="checkbox" name="status" value="TRUE" ${user.active?'checked':""} disabled>
                        <form method="post" action="${pageContext.request.contextPath}/admin/user">
                            <input type="hidden" name="id" value="${user.id}"/>
                            <c:choose>
                                <c:when test="${user.active}">
                                    <input type="hidden" name="status" value="false"/>
                                    <button type="submit"><fmt:message key="block"/></button>
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" name="status" value="true"/>
                                    <button type="submit"><fmt:message key="unblock"/></button>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </td>

                </tr>
                <%--        <tr><a href="${pageContext.request.contextPath}/wallet/accounts?user_id=${user.id}"><fmt:message key="accounts"/></tr>--%>
                <tr><a class="btn btn-outline-primary mx-5" href="/bikes"><fmt:message key="my_payments"/></a></tr>
            </c:if>
            </tbody>
        </table>
        </c:if>
        </div>
        <div class="col">
            <button class="btn btn-outline-primary" type="button" data-bs-toggle="collapse"
                    data-bs-target="#collapseRefillBalance" aria-expanded="false" aria-controls="collapseRefillBalance">
                <fmt:message key="refill_balance"/>
            </button>
            <div class="collapse collapse-horizontal" id="collapseRefillBalance">
                <div class="card card-body" style="width: 250px;">
                    <form method="post" action="${pageContext.request.contextPath}/wallet/account">
                        <fmt:message key="amount"/>
                        <input type="hidden" name="action" value="refill">
                        <input type="number" step=".01" name="amount" required>
                        <br/>
                        <div class="m-1"><fmt:message key="currency"/>
                            <select name="currency" size="1" required>
                                <option value="UAH" selected><fmt:message key="uah"/></option>
                                <option value="USD"><fmt:message key="usd"/></option>
                                <option value="EURO"><fmt:message key="euro"/></option>
                            </select>
                        </div>
                        <br/>
                        <button class="mt-1" type="submit"><fmt:message key="refill_balance"/></button>
                    </form>
                </div>
            </div>

            <button class="btn btn-outline-primary" type="button" data-bs-toggle="collapse"
                    data-bs-target="#collapseChangeActive" aria-expanded="false" aria-controls="collapseChangeActive">
                <fmt:message key="block"/>/<fmt:message key="unblock"/>
            </button>
            <div class="collapse collapse-horizontal" id="collapseChangeActive">
                <div class="card card-body" style="width: 250px;">
                    <form method="post" action="${pageContext.request.contextPath}/wallet/account">
                        <input type="hidden" name="action" value="block">
                        <input type="hidden" name="action" value="unblok">
<%--                        <input type="text" name="reason" required>--%>
                        <br/>
                        <div class="m-1"><fmt:message key="currency"/>
                            <select name="currency" size="1" required>
                                <option value="UAH" selected><fmt:message key="uah"/></option>
                                <option value="USD"><fmt:message key="usd"/></option>
                                <option value="EURO"><fmt:message key="euro"/></option>
                            </select>
                        </div>
                        <br/>
                        <button class="mt-1" type="submit"><fmt:message key="refill_balance"/></button>
                    </form>
                </div>
            </div>


        </div>
    </div>



<a class="btn btn-outline-primary mx-5" href="${pageContext.request.contextPath}/wallet/"><fmt:message key="home_page"/></a>
</div>
</body>

</html>