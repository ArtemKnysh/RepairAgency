<%@ tag body-content="empty" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="activeParam" rtexprvalue="true" required="true" type="java.lang.String" %>
<%@ attribute name="activeType" rtexprvalue="true" required="true" type="java.lang.String" %>
<%@ attribute name="sortingParam" rtexprvalue="true" required="true" type="java.lang.String" %>
<c:choose>
    <c:when test="${activeParam == sortingParam && activeType=='desc'}">
        <i class="fas fa-sort-down"></i>
    </c:when>
    <c:when test="${activeParam == sortingParam && activeType=='asc'}">
        <i class="fas fa-sort-up"></i>
    </c:when>
    <c:otherwise>
        <i class="fas fa-sort"></i>
    </c:otherwise>
</c:choose>