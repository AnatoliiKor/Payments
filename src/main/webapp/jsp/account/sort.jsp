    <form method="get" action="/wallet/accounts">
        <input type="hidden" name="user_id" value="${user_id}"/>
        <div>
            <th><fmt:message key="sort_by"/></th>
            <select name="sort_by" size="1">
                <c:if test="${sort_by!=null}">
                    <option selected>${sort_by}</option>
                </c:if>
                <option value="number" <c:if test="${sort_by.equals('number')}">selected</c:if>>
                    <th><fmt:message key="number"/></th></option>
                <option value="balance" <c:if test="${sort_by.equals('balance')}">selected</c:if>>
                    <th><fmt:message key="balance"/></th></option>
                <option value="account_name" <c:if test="${sort_by.equals('account_name')}">selected</c:if>>
                    <th><fmt:message key="account_name"/></th></option>
                <option value="currency" <c:if test="${sort_by.equals('currency')}">selected</c:if>>
                    <th><fmt:message key="currency"/></th></option>
                <option value="active" <c:if test="${sort_by.equals('active')}">selected</c:if>>
                    <th><fmt:message key="is_active"/></th></option>
            </select>

            <th><fmt:message key="order_by"/></th>
            <select name="order" size="1">
            <option value="ASC" <c:if test="${order.equals('ASC')}">selected</c:if>>
                <th><fmt:message key="ascending"/></th></option>
            <option value="DESC" <c:if test="${order.equals('DESC')}">selected</c:if>><th>
                <fmt:message key="descending"/></th></option>
            </select>
            <button type="submit"><fmt:message key="apply"/></button>
        </div>
    </form>