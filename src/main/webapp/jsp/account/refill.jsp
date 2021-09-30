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
                <form method="post" action="${pageContext.request.contextPath}/wallet/refill_account">
                    <fmt:message key="amount"/>
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