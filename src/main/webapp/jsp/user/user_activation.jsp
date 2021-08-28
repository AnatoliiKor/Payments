<input type="checkbox" name="status" value="TRUE" ${user.active?'checked':""} disabled>

<div class="text-dangering fw-bold"><fmt:message key="blocked"/></div>

<form method="post" action="${pageContext.request.contextPath}/admin/user">
    <input type="hidden" name="id" value="${user.id}"/>
    <c:choose>
        <c:when test="${user.active}">
            <input type="hidden" name="status" value="false"/>
            <button class="btn btn-outline-danger mt-2"
                    type="submit"><fmt:message key="block"/>
            </button>
        </c:when>
        <c:otherwise>
            <input type="hidden" name="status" value="true"/>
            <button class="btn btn-outline-success mt-2"
                    type="submit"><fmt:message key="unblock"/>
            </button>
        </c:otherwise>
    </c:choose>
</form>