<select name="account_id" size="1">
    <c:choose>
        <c:when test="${not empty accounts}">
            <c:forEach var="account" items="${accounts}">
                <c:if test="${account.active}">
                <option value="${account.id}">
                        ${account.balance/100} ${account.currency} - ${account.accountName} (UA${account.number})
                </option>
                </c:if>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <option selected><fmt:message key="no_accounts"/></option>
        </c:otherwise>
    </c:choose>
</select>