<table class="table table-striped auto__table table-condensed ">
    <tbody>
    <tr>
        <td><fmt:message key="number"/></td>
        <td>UA${account.number}</td>
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
        <td><fmt:message key="credit_card"/></td>
        <td>${account.getCardNumberSpaces()}</td>
    </tr>
    <tr>
        <td><fmt:message key="opened"/></td>
        <td>${account.getFormatedDate()}</td>
    </tr>
    <tr>
        <td><fmt:message key="user"/></td>
        <td><a href="${pageContext.request.contextPath}/wallet/user?id=${account.userId}"><fmt:message
                key="profile_nav"/></a></td>
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
    </tbody>
</table>