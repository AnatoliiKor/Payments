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
            <form method="post" action="${pageContext.request.contextPath}/wallet/active_account">
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
            <form method="post" action="${pageContext.request.contextPath}/admin/active_account">
                <input type="hidden" name="id_to_do" value="${account.id}"/>
                <input type="hidden" name="action" value="active"/>
                <c:choose>
                    <c:when test="${account.active}">
                        <input type="hidden" name="is_active" value="false"/>
                        <button class="btn btn-outline-success mt-2"
                                type="submit"><fmt:message key="block"/>
                        </button>
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="is_active" value="true"/>
                        <button class="btn btn-outline-success mt-2"
                                type="submit"><fmt:message key="unblock"/>
                        </button>
                    </c:otherwise>
                </c:choose>
            </form>
        </c:if>
    </div>
</div>