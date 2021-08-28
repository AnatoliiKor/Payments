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
                        <td>${account.balance/100}</td>
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
                        <td><fmt:message key="opened"/></td>
                        <td>${account.getFormatedDate()}</td>
                    </tr>
                    <tr>
                        <td><fmt:message key="is_active"/></td>
                        <td><fmt:message key="${account.active?'is_active':'blocked'}"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <c:if test="${account.action==2}"><fmt:message key="wait_to_block"/></c:if>
                            <c:if test="${account.action==1}"><fmt:message key="wait_to_unblock"/></c:if>
                        </td>
                    </tr>


                    <c:if test="${role.equals('ADMIN')}">
                        <tr>
                            <td><fmt:message key="role"/></td>
                            <td>${user.role}</td>
                        </tr>
                        <tr>
                            <td><fmt:message key="is_active"/></td>
                            <td>
                                <fmt:message key="${user.active?'is_active':'blocked'}"/>
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
                    </c:if>
                    </tbody>
                </table>
            </c:if>
        </div>
        <div class="col">
            <div>
                <button class="btn btn-outline-primary mt-2" type="button" data-bs-toggle="collapse"
                        data-bs-target="#collapseRefillBalance" aria-expanded="false"
                        aria-controls="collapseRefillBalance">
                    <fmt:message key="refill_balance"/>
                </button>
                <div class="collapse collapse-horizontal mt-2" id="collapseRefillBalance">
                    <div class="card card-body" style="width: 250px;">
                        <c:if test="${!account.active}">
                            <div class="text-danger fw-bold"><fmt:message key="blocked"/></div>
                        </c:if>

                        <c:if test="${account.active}">
                            <form method="post" action="${pageContext.request.contextPath}/wallet/account">
                                <fmt:message key="amount"/>
                                <input type="hidden" name="action" value="refill">
                                <input type="hidden" name="id_to_do" value="${account.id}"/>
                                <input type="number" step=".01" min="0.01" name="amount" required>
                                ${account.currency}
                                <br/>
                                <button class="mt-1" type="submit"><fmt:message key="refill_balance"/></button>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>

            <button class="btn btn-outline-warning mt-4" type="button" data-bs-toggle="collapse"
                    data-bs-target="#collapseChangeActive" aria-expanded="false" aria-controls="collapseChangeActive">
                <fmt:message key="block"/>/<fmt:message key="unblock"/>
            </button>
            <div class="collapse collapse-horizontal mt-2" id="collapseChangeActive">
                <div class="card card-body" style="width: 300px;">

                    <c:if test="${account.action==2}"><fmt:message key="wait_to_block"/></c:if>
                    <c:if test="${account.action==1}"><fmt:message key="wait_to_unblock"/></c:if>
                    <c:if test="${account.action==0 && 'CLIENT'.equals(role)}">
                        <div class="text-info fw-bold">${account.accountName} - <fmt:message
                                key="${account.active?'is_active':'blocked'}"/></div>
                        <form method="post" action="${pageContext.request.contextPath}/wallet/account">
                            <input type="hidden" name="id_to_do" value="${account.id}"/>
                            <input type="hidden" name="action" value="action"/>
                            <c:if test="${account.active}">
                                <input type="hidden" name="to_do" value="2"/>
                                <button class="btn btn-outline-danger mt-2"
                                        type="submit"><fmt:message key="block"/>-<fmt:message key="send_request"/>
                                </button>
                            </c:if>
                            <c:if test="${!account.active}">
                                <input type="hidden" name="to_do" value="1"/>
                                <button class="btn btn-outline-success mt-2"
                                        type="submit"><fmt:message key="unblock"/>-<fmt:message key="send_request"/>
                                </button>
                            </c:if>
                        </form>
                    </c:if>
                    <c:if test="${'ADMIN'.equals(role)}">
                        <div class="text-info fw-bold">${account.accountName} - <fmt:message
                                key="${account.active?'is_active':'blocked'}"/></div>
                        <form method="post" action="${pageContext.request.contextPath}/admin/user">
                            <input type="hidden" name="id" value="${user.id}"/>
                            <c:choose>
                                <c:when test="${user.active}">
                                    <input type="hidden" name="status" value="false"/>
                                    <button class="btn btn-outline-success mt-2"
                                            type="submit"><fmt:message key="block"/>
                                    </button>
                                    <button type="submit"><fmt:message key="block"/></button>
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" name="status" value="true"/>
                                    <button class="btn btn-outline-success mt-2"
                                            type="submit"><fmt:message key="unblock"/>
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </c:if>


                </div>
            </div>
            <div><a class="btn btn-outline-success mt-4" href="wallet/payments"><fmt:message key="payments"/></a></div>


        </div>
    </div>

    <a class="btn btn-outline-primary mx-5" href="${pageContext.request.contextPath}/wallet/"><fmt:message
            key="home_page"/></a>
</div>
</body>

</html>