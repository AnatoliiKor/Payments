<c:if test="${not empty user_id}"><c:set var="parameter" value="&user_id=${user_id}"/></c:if>
<c:if test="${not empty account_number}"><c:set var="parameter" value="&account_number=${account_number}"/></c:if>

<ul class="pagination mt-1">
    <li class="page-item ${pg == 1 ? 'disabled' : ''}"><a class="page-link"
                                                          href="${pageContext.request.contextPath}/wallet/transactions?pg=${pg-1}${parameter}&account_type=${account_type}&sort_by=${sort_by}&order=${order}"><fmt:message
            key="previous_main"/></a></li>
    <li class="page-item active"><a class="page-link">${pg}</a></li>
    <li class="page-item ${pg == pg_max? 'disabled' : ''}"><a class="page-link"
                                                              href="${pageContext.request.contextPath}/wallet/transactions?pg=${pg+1}${parameter}&account_type=${account_type}&sort_by=${sort_by}&order=${order}"><fmt:message
            key="next_main"/></a></li>
</ul>