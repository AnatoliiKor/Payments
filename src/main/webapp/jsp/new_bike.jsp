<%@include file="navbar.jsp" %>



<div class="container mt-2">
    <h2> Add new BIKE</h2>
    <form method="post" action="${pageContext.request.contextPath}/bike">

        <div> model = <input type="text" name="name" value="<c:if test="${bike!=null}">${bike.name}</c:if>" required/>
        </div>
        <div> brand = <select name="brand" size="1" value="<c:if test="${bike!=null}">${bike.brand}</c:if>">
            <option>Ardis</option>
            <option>Comanche</option>
            <option>Cube</option>
            <option>Discovery</option>
            <option>Focus</option>
            <option>Santa Cruz</option>
            <option>other</option>
        </select></div>
        <div> colour = <select name="colour" size="1" value="<c:if test="${bike!=null}">${bike.colour}</c:if>">
            <option>red</option>
            <option>blue</option>
            <option>green</option>
            <option>black</option>
            <option>yellow</option>
            <option>white</option>
        </select></div>
        <div>description <input type="text" name="description" value="<c:choose><c:when test="${bike!= null}">${bike.description}</c:when><c:otherwise>No description</c:otherwise></c:choose>" required/>
        </div>
        <div> category = <select name="category" size="1" value="<c:if test="${bike!=null}">${bike.category}</c:if>">
            <option>utility bicycle</option>
            <option>mountain bicycle</option>
            <option>racing bicycle</option>
            <option>touring bicycle</option>
        </select></div>
        <div class="col-md-2">price <input type="number" name="price"
                                           value="<c:if test="${bike!=null}">${bike.category}</c:if>" required/></div>
        <button type="submit">Add</button>

    </form>

    <c:if test="${error!=null}">
        <div class="alert alert-warning" role="alert">
                ${error} because ${error_reason}
        </div>
    </c:if>

    <a class="btn btn-outline-primary mx-5" href="${pageContext.request.contextPath}/"><fmt:message key="home_page"/></a>

</div>

