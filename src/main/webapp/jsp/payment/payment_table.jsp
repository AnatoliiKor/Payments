<table class="table table-striped auto__table table-condensed ">
    <tbody>
    <tr>
        <td><fmt:message key="receiver_account"/></td>
        <td>UA${payment.receiver}</td>
    </tr>
    <tr>
        <td><fmt:message key="receiver"/></td>
        <td>
            <c:if test="${not empty receiver_full_name}">
                ${receiver_full_name.lastName} ${receiver_full_name.name} ${not empty receiver_full_name.middleName ? receiver_full_name.middleName : ''}
            </c:if>
        </td>
    </tr>
    <tr>
        <td><fmt:message key="payment_amount"/></td>
        <td>${payment.amount/100}</td>
    </tr>
    <tr>
        <td><fmt:message key="currency"/></td>
        <td>${payment.currency}</td>
    </tr>
    <tr>
        <td><fmt:message key="account_number"/></td>
        <td>UA${payment.accountNumber}</td>
    </tr>
    <tr>
        <td><fmt:message key="account_name"/></td>
        <td>${payment.accountName}</td>
    </tr>
    <tr>
        <td><fmt:message key="payment_destination"/></td>
        <td>${payment.destination}</td>
    </tr>
    <c:if test="${not empty payment.registered}">
        <tr>
            <td><fmt:message key="date"/></td>
            <td>${payment.getFormatedDate()}</td>
        </tr>
    </c:if>
    </tbody>
</table>