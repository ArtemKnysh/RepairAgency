<%--@elvariable id="loggedUser" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>
<%--@elvariable id="currentPage" type="java.lang.Integer"--%>
<%--@elvariable id="listMasters" type="java.util.List"--%>
<%--@elvariable id="entities" type="java.util.List"--%>
<%--@elvariable id="activeParam" type="java.lang.String"--%>
<%--@elvariable id="activeType" type="java.lang.String"--%>
<%--@elvariable id="recordsOnPage" type="java.lang.Integer"--%>
<%--@elvariable id="filterName" type="java.lang.String"--%>
<%--@elvariable id="filterValue" type="java.lang.String"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fileTags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="libTags" uri="http://com.epam.rd.java.basic.repairagency/lib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="localeKeys"/>
<html lang="${sessionScope.lang}">
<c:set var="title">
    <fmt:message key="label.feedback.list"/>
</c:set>
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="${title}"/>
</jsp:include>
<body>
<fileTags:navbarForRole role="${loggedUser.role}" active="${initParam['managerFeedbackListUrl']}"/>
<div class="row">
    <div class="container col-md-10">
        <h3 class="text-center">${title}</h3>
        <hr>
        <div class="form-row">
            <div class="col-auto">
                <a class="form-control btn btn-primary"
                   href="<fileTags:hrefWithParameters href="${initParam['managerFeedbackListUrl']}" page="${currentPage}"/>"><fmt:message key="label.filter.clear"/></a>
            </div>
            <form action="${initParam['managerFeedbackListUrl']}" method="get" class="col">
                <div class="form-row">
                    <input type="hidden" name="page" value="${currentPage}">
                    <input type="hidden" name="activeParam" value="${activeParam}">
                    <input type="hidden" name="activeType" value="${activeType}">
                    <input type="hidden" name="recordsOnPage" value=${recordsOnPage}>
                    <input type="hidden" name="filterName" value="masterId">
                    <div class="col-auto">
                        <h6><fmt:message key="label.filter.by.master"/></h6>
                    </div>
                    <div class="col-4">
                        <select class="form-control" name="filterValue">
                            <c:forEach var="master" items="${listMasters}">
                                <option
                                        <c:if test="${filterName == 'masterId'  && master.id == filterValue}">selected</c:if>
                                        value="${master.id}">${master.fullName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="form-control btn btn-success"><fmt:message key="label.apply"/></button>
                    </div>
                </div>
            </form>
            <form action="${initParam['managerFeedbackListUrl']}" method="get" class="col">
                <div class="form-row">
                    <input type="hidden" name="page" value="${currentPage}">
                    <input type="hidden" name="activeParam" value="${activeParam}">
                    <input type="hidden" name="activeType" value="${activeType}">
                    <input type="hidden" name="recordsOnPage" value=${recordsOnPage}>
                    <input type="hidden" name="filterName" value="isHidden">
                    <div class="col-auto">
                        <h6><fmt:message key="label.filter.by.is_hidden"/></h6>
                    </div>
                    <div class="col-4">
                        <select class="form-control" name="filterValue">
                            <option
                                    <c:if test="${filterName == 'isHidden' && filterValue == true}">selected</c:if>
                                    value="true"><fmt:message key="label.feedback.hidden"/>
                            </option>
                            <option
                                    <c:if test="${filterName == 'isHidden' && filterValue == false}">selected</c:if>
                                    value="false"><fmt:message key="label.feedback.shown"/>
                            </option>
                        </select>
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="form-control btn btn-success"><fmt:message key="label.apply"/></button>
                    </div>
                </div>
            </form>
        </div>
        <hr>
        <c:if test="${errorMessage != null}">
            <div class="alert alert-danger alert-dismissible fade show">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                    ${errorMessage}
            </div>
        </c:if>
        <c:if test="${successMessage != null}">
            <div class="alert alert-success alert-dismissible">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                    ${successMessage}
            </div>
        </c:if>
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${initParam['managerFeedbackListUrl']}" activeFilterName="${filterName}" activeFilterValue="${filterValue}"
                       page="${currentPage}" sortingParam="text"/>"><fmt:message key="label.feedback.text"/></a>
                    <fileTags:showSortIcon sortingParam="text"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${initParam['managerFeedbackListUrl']}" activeFilterName="${filterName}" activeFilterValue="${filterValue}"
                       page="${currentPage}" sortingParam="createdAt"/>"><fmt:message key="label.created_at"/></a>
                    <fileTags:showSortIcon sortingParam="createdAt"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${initParam['managerFeedbackListUrl']}" activeFilterName="${filterName}" activeFilterValue="${filterValue}"
                       page="${currentPage}" sortingParam="customerFullName"/>"><fmt:message key="label.customer"/></a>
                    <fileTags:showSortIcon sortingParam="customerFullName"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${initParam['managerFeedbackListUrl']}" activeFilterName="${filterName}" activeFilterValue="${filterValue}"
                       page="${currentPage}" sortingParam="masterFullName"/>"><fmt:message key="label.master"/></a>
                    <fileTags:showSortIcon sortingParam="masterFullName"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${initParam['managerFeedbackListUrl']}" activeFilterName="${filterName}" activeFilterValue="${filterValue}"
                       page="${currentPage}" sortingParam="isHidden"/>"><fmt:message key="label.actions"/></a>
                    <fileTags:showSortIcon sortingParam="isHidden"/>
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="feedback" items="${entities}">
                <tr>
                    <td><c:out value="${feedback.text}"/></td>
                    <td>${libTags:formatLocalDateTime(feedback.createdAt)}</td>
                    <td>
                        <a href="${initParam['managerCustomerViewUrl']}?customerId=${feedback.customerId}">${feedback.customer.fullName}</a>
                    </td>
                    <td>
                        <a href="${initParam['managerMasterViewUrl']}?masterId=${feedback.masterId}">${feedback.master.fullName}</a>
                    </td>
                    <td class="form-group">
                        <div class="form-row">
                            <div class="col mb-1">
                                <c:choose>
                                    <c:when test="${feedback.isHidden}">
                                        <form action="${initParam['managerFeedbackShowUrl']}" method="post">
                                            <input type="hidden" name="feedbackId" value="${feedback.id}"/>
                                            <button type="submit" class="form-control btn btn-success"><fmt:message key="label.feedback.show"/></button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="${initParam['managerFeedbackHideUrl']}" method="post">
                                            <input type="hidden" name="feedbackId" value="${feedback.id}"/>
                                            <button type="submit" class="form-control btn btn-danger"><fmt:message key="label.feedback.hide"/></button>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <hr>
        <jsp:include page="/pages/common/layouts/_table-footer.jsp">
            <jsp:param name="href" value="${initParam['managerFeedbackListUrl']}"/>
            <jsp:param name="activeFilterName" value="${filterName}"/>
            <jsp:param name="activeFilterValue" value="${filterValue}"/>
        </jsp:include>
    </div>
</div>
</body>
</html>