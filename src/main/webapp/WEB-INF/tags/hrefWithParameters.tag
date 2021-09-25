<%--@elvariable id="recordsOnPage" type="java.lang.Integer"--%>
<%--@elvariable id="activeParam" type="java.lang.String"--%>
<%--@elvariable id="activeType" type="java.lang.String"--%>
<%@ tag body-content="empty" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="href" rtexprvalue="true" required="true" type="java.lang.String" %>
<%@ attribute name="page" rtexprvalue="true" required="true" type="java.lang.Integer" %>
<%@ attribute name="sortingParam" rtexprvalue="true" required="false" type="java.lang.String" %>
<c:choose>
    <c:when test="${sortingParam != null}">
        <c:out value="${href}?page=${page}&param=${sortingParam}&activeParam=${activeParam}&activeType=${activeType}&recordsOnPage=${recordsOnPage}"/>
    </c:when>
    <c:otherwise>
        <c:out value="${href}?page=${page}&activeParam=${activeParam}&activeType=${activeType}&recordsOnPage=${recordsOnPage}"/>
    </c:otherwise>
</c:choose>
