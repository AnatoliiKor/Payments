<select name="account_id" size="1">
    <c:choose>
        <c:when test="${not empty accounts}">
            <c:forEach var="account" items="${accounts}">
                <option value="${account.id}">
                        ${account.balance/100} ${account.currency} - ${account.accountName}
                </option>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <option selected><fmt:message key="no_accounts"/></option>
        </c:otherwise>
    </c:choose>
</select>