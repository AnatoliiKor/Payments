        <%
            String parameter = "";
            if (request.getParameter("user_id") != null) {
                 parameter = "&user_id=" + request.getParameter("user_id");
            }
            if (request.getParameter("account_number") != null) {
                parameter = "&account_number=" + request.getParameter("account_number");
            }
            request.setAttribute("parameter", parameter);
        %>

        <ul class="pagination mt-1">
            <li class="page-item ${pg == 1 ? 'disabled' : ''}"><a class="page-link" href="${pageContext.request.contextPath}/wallet/transactions?pg=${pg-1}${parameter}&account_type=${account_type}&sort_by=${sort_by}&order=${order}"><fmt:message key="previous_main"/></a></li>
            <li class="page-item active"><a class="page-link">${pg}</a></li>
            <li class="page-item ${pg == pg_max? 'disabled' : ''}"><a class="page-link" href="${pageContext.request.contextPath}/wallet/transactions?pg=${pg+1}${parameter}&account_type=${account_type}&sort_by=${sort_by}&order=${order}"><fmt:message key="next_main"/></a></li>
        </ul>