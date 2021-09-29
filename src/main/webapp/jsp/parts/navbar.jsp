<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/"><fmt:message key="title"/></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">

            <c:if test="${not empty role}">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <c:choose>
                        <c:when test="${role.equals('ADMIN')}">
                            <a class="btn btn-outline-primary mx-1" href="/admin"><fmt:message key="admin_nav"/></a>
                            <a class="btn btn-outline-primary mx-1" href="/admin/users"><fmt:message
                                    key="users_list"/></a>
                            <a class="btn btn-outline-primary mx-1" href="/admin/accounts"><fmt:message
                                    key="accounts"/></a>
                            <a class="btn btn-outline-primary mx-1" href="/admin/transactions"><fmt:message
                                    key="payments"/></a>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/wallet">
                                    <fmt:message key="home_nav"/></a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </c:if>

            <a class="btn btn-outline-primary " href="${pageContext.request.contextPath}/lang?language=uk"
               role="button">
                <fmt:message key="uk_button"/></a>
            <a class="btn btn-outline-warning mx-1" href="${pageContext.request.contextPath}/lang?language=en"
               role="button">
                <fmt:message key="en_button"/></a>


            <c:choose>
                <c:when test="${not empty user_auth}">
                    <a class="btn btn-outline-secondary mx-2" href="${pageContext.request.contextPath}/wallet/user"
                       role="button">
                        <fmt:message key="profile_nav"/>
                    </a>
                    <a class="btn btn-outline-secondary" href="/logout" role="button">
                            ${user_auth.lastName}.<fmt:message key="logout"/>
                    </a>
                </c:when>
                <c:otherwise>
                    <fmt:message key="guest"/>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>

