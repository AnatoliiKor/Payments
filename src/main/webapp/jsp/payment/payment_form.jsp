<form method="post" action="${pageContext.request.contextPath}/wallet/prepare_payment">

    <input type="hidden" name="action" value="prepare">
    UA<input class="m-2" type="tel" name="receiver" pattern="[0-9]{11}" minlength="11" maxlength="11"
             size="11" placeholder="00000000000"
             <c:if test="${not empty receiver}"> value="${receiver}"</c:if> required>
    <label> <fmt:message key="receiver_account"/> </label>
    <br/>

    <%@include file="account_select.jsp" %>

    <br/>
    <input class="my-2" type="number" step=".01" min="0.01" name="amount"
           <c:if test="${not empty amount}"> value="${amount/100}"</c:if> required>
    <label> <fmt:message key="payment_amount"/> </label>
    <br/>
    <textarea name="destination" cols="40" rows="4" placeholder="<fmt:message key="payment_destination"/>"></textarea>
    <br/>

    <input class="btn btn-outline-success mt-2" type="submit" value="<fmt:message key="prepare_payment"/>">
</form>