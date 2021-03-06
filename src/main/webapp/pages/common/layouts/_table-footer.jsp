<%--@elvariable id="recordsOnPage" type="java.lang.Integer"--%>
<%--@elvariable id="activeParam" type="java.lang.String"--%>
<%--@elvariable id="activeType" type="java.lang.String"--%>
<%--@elvariable id="currentPage" type="java.lang.Integer"--%>
<%--@elvariable id="numberOfPages" type="java.lang.Integer"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fileTags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="localeKeys"/>
<div class="row mb-3">
    <form action="${param.href}" method="get" class="col form-row mr-auto">
        <input type="hidden" name="page" value="${currentPage}">
        <input type="hidden" name="activeParam" value="${activeParam}">
        <input type="hidden" name="activeType" value="${activeType}">
        <c:if test="${param.activeFilterName != null && param.activeFilterValue != null}">
            <input type="hidden" name="filterName" value="${param.activeFilterName}">
            <input type="hidden" name="filterValue" value="${param.activeFilterValue}">
        </c:if>
        <div class="col-auto"><fmt:message key="label.pagination.records_on_page_message.begin"/></div>
        <div class="col-auto">
            <select name="recordsOnPage" class="custom-select">
                <option value="5" <c:if test="${recordsOnPage == 5}">selected</c:if>>5</option>
                <option value="10" <c:if test="${recordsOnPage == 10}">selected</c:if>>10</option>
                <option value="25" <c:if test="${recordsOnPage == 25}">selected</c:if>>25</option>
                <option value="50" <c:if test="${recordsOnPage == 50}">selected</c:if>>50</option>
            </select>
        </div>
        <div class="col-auto"><fmt:message key="label.pagination.records_on_page_message.end"/></div>
        <div class="col-auto">
            <button type="submit" class="form-control btn btn-success"><fmt:message
                    key="label.apply"/></button>
        </div>
    </form>
    <div class="col-auto">
        <ul class="pagination mb-0 float-right">
            <li class="page-item
                    <c:if test="${numberOfPages == 1 || currentPage == 1}">disabled</c:if>">
                <a class="page-link"
                   href="<fileTags:hrefWithParameters href="${param.href}" page="${currentPage - 1}" activeFilterName="${param.activeFilterName}" activeFilterValue="${param.activeFilterValue}"/>"
                ><fmt:message key="label.pagination.previous_button"/></a>
            </li>
            <c:forEach begin="1" end="${numberOfPages}" var="num">
                <li class="page-item <c:if test="${currentPage == num}">active</c:if>">
                    <a class="page-link"
                       href="<fileTags:hrefWithParameters href="${param.href}" page="${num}" activeFilterName="${param.activeFilterName}" activeFilterValue="${param.activeFilterValue}"/>">${num}</a>
                </li>
            </c:forEach>
            <li class="page-item <c:if test="${currentPage == numberOfPages}">disabled</c:if>">
                <a class="page-link"
                   href="<fileTags:hrefWithParameters href="${param.href}" page="${currentPage + 1}" activeFilterName="${param.activeFilterName}" activeFilterValue="${param.activeFilterValue}"/>"
                ><fmt:message key="label.pagination.next_button"/></a>
            </li>
        </ul>
    </div>
</div>
