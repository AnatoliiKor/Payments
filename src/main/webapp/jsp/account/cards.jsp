<c:choose>
    <c:when test="${not empty accounts}">
        <h3 class="text-info fw-bold"><fmt:message key="credit_card"/>:</h3>
        <c:forEach var="account" items="${accounts}">
            <div class="card text-dark bg-light border-info mb-2  height: 200px;" style="max-width: 25rem;">
                <div class="card-header"> ${account.cardNumber}</div>
                <div class="card-body">
                    <h6 class="card-title lh-1">${account.balance/100} ${account.currency}</h6>
                    <div class="card-text lh-1">${account.accountName} (UA${account.number})</div>
                </div>
            </div>
        </c:forEach>
    </c:when>
    <c:otherwise><fmt:message key="no_accounts"/></c:otherwise>
</c:choose>





