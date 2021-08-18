<c:if test="${warn!=null}">
    <div class="alert alert-warning" role="alert">
            ${warn}
    </div>
</c:if>

<c:if test="${message!=null}">
    <div class="alert alert-info" role="alert">
            ${message}
    </div>
</c:if>