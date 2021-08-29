<%@include file="parts/navbar.jsp"%>

<html>
<head>
    <title>Wallet</title>
</head>
<body>

<%@include file="parts/messages.jsp" %>

<div class="container mt-5">
    <div class="row">
        <div class="col">
            <%@include file="account/cards.jsp" %>
        </div>

        <div class="col-sm-3">
            <div><a class="btn btn-outline-primary m-2" href="/admin/users"><fmt:message key="my_payments"/></a></div>
            <div><a class="btn btn-outline-primary m-2" href="/wallet/accounts?user_id=${user_auth.id}"><fmt:message key="my_accounts"/></a></div>
            <div>
                <button class="btn btn-outline-primary m-2" type="button" data-bs-toggle="collapse"
                        data-bs-target="#collapseNewAccount" aria-expanded="false" aria-controls="collapseNewAccount">
                    <fmt:message key="new_account"/>
                </button>
                <div class="collapse collapse-horizontal" id="collapseNewAccount">
                        <div class="card card-body" style="width: 300px;">
                            <form method="post" action="${pageContext.request.contextPath}/wallet/account">
                                <fmt:message key="account_name"/>
                                <input type="hidden" name="action" value="new">
<%--                                <input type="hidden" name="id" value=${user_auth.id}>--%>
                                <input type="text" name="account_name" required>
                                <br/>
                                <div class="m-1"><fmt:message key="currency"/>
                                    <select name="currency" size="1" required>
                                        <option value="UAH" selected><fmt:message key="uah"/></option>
                                        <option value="USD"><fmt:message key="usd"/></option>
                                        <option value="EURO"><fmt:message key="euro"/></option>
                                    </select>
                                </div>
                                    <br/>
                                <button class="mt-1" type="submit"><fmt:message key="send_request"/></button>
                            </form>
                        </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
