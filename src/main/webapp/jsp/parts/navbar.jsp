<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language" scope="session"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n/messages"/>
<html lang="${language}">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
      rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
      crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

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
                            <a class="btn btn-outline-primary mx-1" href="/admin/users"><fmt:message key="users_list"/></a>
                            <a class="btn btn-outline-primary mx-1" href="/admin/accounts"><fmt:message key="accounts"/></a>
                            <a class="btn btn-outline-primary mx-1" href="/admin/payments"><fmt:message key="payments"/></a>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/wallet">
                                    <fmt:message key="home_nav"/></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/cart">
                                    <fmt:message key="cart_nav"/></a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </c:if>

            <a class="btn btn-outline-primary " href="${pageContext.request.contextPath}/lang?language=uk" role="button">
                <fmt:message key="uk_button"/></a>
            <a class="btn btn-outline-warning mx-1" href="${pageContext.request.contextPath}/lang?language=en" role="button">
                <fmt:message key="en_button"/></a>


            <c:choose>
                <c:when test="${not empty user_auth}">
                    <a class="btn btn-outline-secondary mx-2" href="${pageContext.request.contextPath}/wallet/user" role="button">
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

