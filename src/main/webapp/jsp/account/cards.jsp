<c:choose>
    <c:when test="${not empty accounts}">
        <div class="row row-cols-auto">
            <c:forEach var="account" items="${accounts}">
                <div class="card text-dark bg-light border-info mb-2  height: 200px;" style="width: 20rem;">
                    <div class="card-header text-info fw-bold"><fmt:message
                            key="credit_card"/>: ${account.getCardNumberSpaces()}</div>
                    <div class="card-body">
                        <h6 class="card-title lh-1">${account.balance/100} ${account.currency}</h6>
                        <div class="card-text lh-1">${account.accountName} (UA${account.number})</div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise><fmt:message key="no_accounts"/></c:otherwise>
</c:choose>





