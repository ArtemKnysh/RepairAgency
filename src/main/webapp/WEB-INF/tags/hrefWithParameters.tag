<%--@elvariable id="recordsOnPage" type="java.lang.Integer"--%>
<%--@elvariable id="activeParam" type="java.lang.String"--%>
<%--@elvariable id="activeType" type="java.lang.String"--%>
<%--@elvariable id="filterName" type="java.lang.String"--%>
<%--@elvariable id="filterValue" type="java.lang.String"--%>
<%@ tag body-content="empty" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="href" rtexprvalue="true" required="true" type="java.lang.String" %>
<%@ attribute name="page" rtexprvalue="true" required="true" type="java.lang.Integer" %>
<%@ attribute name="sortingParam" rtexprvalue="true" required="false" type="java.lang.String" %>
<%@ attribute name="activeFilterName" rtexprvalue="true" required="false" type="java.lang.String" %>
<%@ attribute name="activeFilterValue" rtexprvalue="true" required="false" type="java.lang.String" %>
<c:out value="${href}?page=${page}"/><c:if test="${sortingParam != null}"><c:out value="&param=${sortingParam}"/></c:if><c:out
        value="&activeParam=${activeParam}&activeType=${activeType}&recordsOnPage=${recordsOnPage}"/><c:if
        test="${activeFilterName != null && activeFilterValue != null}"><c:out
        value="&filterName=${activeFilterName}&filterValue=${activeFilterValue}"/></c:if>

