<%@include file="parts/navbar.jsp" %>

<div class="container mt-2">
    <h2>
        <c:choose>
            <c:when test="${bike!=null}"><fmt:message key="update_bike"/>
                <input type="hidden" name="id" value="${bike.id}"/>
                <form method="post" action="/admin/bike_delete">
                    <input type="hidden" name="id" value="${bike.id}"/>
<%--                    <input type="hidden" name="action" value="delete"/>--%>
                    <button type="submit" class="btn btn-secondary"><fmt:message key="delete"/></button>
<%--                    <button type="submit"><fmt:message key="delete"/></button>--%>
                </form>
            </c:when>
            <c:otherwise><fmt:message key="new_bike"/></c:otherwise>
        </c:choose>

    </h2>

    <%@include file="parts/messages.jsp" %>

    <form method="post" action="${pageContext.request.contextPath}/admin/bike_edit">

        <div> model = <input type="text" name="name" value="<c:if test="${bike!=null}">${bike.name}</c:if>" required/>
        </div>
        <div> brand = <select name="brand" size="1">
            <c:if test="${bike!=null}">
                <option selected>${bike.brand}</option>
            </c:if>
            <option>Ardis</option>
            <option>Comanche</option>
            <option>Cube</option>
            <option>Discovery</option>
            <option>Focus</option>
            <option>Santa Cruz</option>
            <option>other</option>
        </select></div>
        <div> colour = <select name="colour" size="1">
            <c:if test="${bike!=null}">
                <option selected>${bike.colour}</option>
            </c:if>
            <option>red</option>
            <option>blue</option>
            <option>green</option>
            <option>black</option>
            <option>yellow</option>
            <option>white</option>
        </select></div>
        <div>description <input type="text" name="description"
                                value="<c:choose><c:when test="${bike!= null}">${bike.description}</c:when><c:otherwise>No description</c:otherwise></c:choose>"
                                required/></div>
        <div> category = <select name="category" size="1">
            <c:if test="${bike!=null}">
                <option selected>${bike.category}</option>
            </c:if>
            <option>utility bicycle</option>
            <option>mountain bicycle</option>
            <option>racing bicycle</option>
            <option>touring bicycle</option>
        </select></div>
        <div class="col-md-2">price <input type="number" name="price"
                                           value="<c:if test="${bike!=null}">${bike.price}</c:if>" required/>
        </div>
        <c:choose>
            <c:when test="${bike!=null}">
                <input type="hidden" name="id" value="${bike.id}"/>
                <button type="submit" class="btn btn-primary"><fmt:message key="update"/></button>
            </c:when>

            <c:otherwise>
                <button type="submit" class="btn btn-primary"><fmt:message key="add"/></button>
            </c:otherwise>
        </c:choose>

    </form>

    <c:if test="${warn!=null}">
        <div class="alert alert-warning" role="alert">
                ${warn}
        </div>
    </c:if>

    <a class="btn btn-outline-primary mx-5" href="${pageContext.request.contextPath}/admin"><fmt:message
            key="home_page"/></a>

</div>

