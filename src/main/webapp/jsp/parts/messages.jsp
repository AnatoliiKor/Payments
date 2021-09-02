<c:if test="${warn!=null}">
    <div class="alert alert-warning" role="alert">
        <fmt:message key="${warn}" bundle="${mes}"/>
    </div>
</c:if>

<c:if test="${message!=null}">
    <div class="alert alert-info" role="alert">
        <fmt:message key="${message}" bundle="${mes}"/>
    </div>
</c:if>