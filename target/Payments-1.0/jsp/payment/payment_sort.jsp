    <form method="get" action="/wallet/payments">
    <c:choose>
        <c:when test="${not empty account_number}">
            <input type="hidden" name="account_number" value="${account_number}"/>
        </c:when>
        <c:otherwise>
            <input type="hidden" name="user_id" value="${user_id}"/>
        </c:otherwise>
    </c:choose>

        <div>
                <th><fmt:message key="sort_by"/></th>
            <select name="payment_sort_by" size="1">
                <c:if test="${payment_sort_by!=null}">
                    <option selected>${payment_sort_by}</option>
                </c:if>
                <option value="account_number" <c:if test="${payment_sort_by.equals('account_number')}">selected</c:if>>
                    <th><fmt:message key="account_number"/></th></option>
                <option value="account_name" <c:if test="${payment_sort_by.equals('account_name')}">selected</c:if>>
                    <th><fmt:message key="account_name"/></th></option>
                <option value="registered" <c:if test="${payment_sort_by.equals('registered')}">selected</c:if>>
                    <th><fmt:message key="date"/></th></option>
                <option value="amount" <c:if test="${payment_sort_by.equals('amount')}">selected</c:if>>
                    <th><fmt:message key="amount"/></th></option>
                <option value="currency" <c:if test="${payment_sort_by.equals('currency')}">selected</c:if>>
                    <th><fmt:message key="currency"/></th></option>
                <option value="receiver" <c:if test="${payment_sort_by.equals('receiver')}">selected</c:if>>
                <th><fmt:message key="receiver_account"/></th></option>
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