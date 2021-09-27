<%--@elvariable id="loggedUser" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>
<%--@elvariable id="currentPage" type="java.lang.Integer"--%>
<%--@elvariable id="activeParam" type="java.lang.String"--%>
<%--@elvariable id="activeType" type="java.lang.String"--%>
<%--@elvariable id="recordsOnPage" type="java.lang.Integer"--%>
<%--@elvariable id="statuses" type="java.util.List"--%>
<%--@elvariable id="filterName" type="java.lang.String"--%>
<%--@elvariable id="filterValue" type="java.lang.String"--%>
<%--@elvariable id="listMasters" type="java.util.List"--%>
<%--@elvariable id="entities" type="java.util.List"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fileTags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="libTags" uri="http://com.epam.rd.java.basic.repairagency/lib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="localeKeys"/>
<html lang="${sessionScope.lang}">
<c:set var="title">
    <fmt:message key="label.repair_request.list"/>
</c:set>
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="${title}"/>
</jsp:include>
<body>
<fileTags:navbarForRole role="${loggedUser.role}" active="${initParam['managerRepairRequestListUrl']}"/>
<div class="row">
    <div class="container col-md-11">
        <h3 class="text-center">${title}</h3>
        <hr>
        <div class="form-row">
            <div class="col-auto">
                <a class="form-control btn btn-primary"
                   href="<fileTags:hrefWithParameters href="${initParam['managerRepairRequestListUrl']}" page="${currentPage}"/>"><fmt:message key="label.filter.clear"/></a>
            </div>
            <form action="${initParam['managerRepairRequestListUrl']}" method="get" class="col">
                <div class="form-row">
                    <input type="hidden" name="page" value="${currentPage}">
                    <input type="hidden" name="activeParam" value="${activeParam}">
                    <input type="hidden" name="activeType" value="${activeType}">
                    <input type="hidden" name="recordsOnPage" value=${recordsOnPage}>
                    <input type="hidden" name="filterName" value="statusId">
                    <div class="col-auto">
                        <h6><fmt:message key="label.filter.by.status"/></h6>
                    </div>
                    <div class="col-4">
                        <select class="form-control" name="filterValue">
                            <c:forEach var="status" items="${statuses}">
                                <option
                                        <c:if test="${filterName == 'statusId' && status.id == filterValue}">selected</c:if>
                                        value="${status.id}">${status}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="form-control btn btn-success"><fmt:message key="label.apply"/></button>
                    </div>
                </div>
            </form>
            <form action="${initParam['managerRepairRequestListUrl']}" method="get" class="col">
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
        </div>
        <hr>
        <c:if test="${successMessage != null}">
            <div class="alert alert-success alert-dismissible">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                    ${successMessage}
            </div>
        </c:if>
        <c:if test="${errorMessage != null}">
            <div class="alert alert-danger alert-dismissible fade show">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                    ${errorMessage}
            </div>
        </c:if>
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <th class="col-auto">
                    <a href="<fileTags:hrefWithParameters href="${initParam['managerRepairRequestListUrl']}" activeFilterName="${filterName}" activeFilterValue="${filterValue}"
                       page="${currentPage}" sortingParam="description" />"><fmt:message key="label.repair_request.description"/></a>
                    <fileTags:showSortIcon sortingParam="description"/>
                </th>
                <th class="col-auto">
                    <a href="<fileTags:hrefWithParameters href="${initParam['managerRepairRequestListUrl']}" activeFilterName="${filterName}" activeFilterValue="${filterValue}"
                       page="${currentPage}" sortingParam="cost"/>"><fmt:message key="label.repair_request.cost"/></a>
                    <fileTags:showSortIcon sortingParam="cost"/>
                </th>
                <th class="col-auto">
                    <a href="<fileTags:hrefWithParameters href="${initParam['managerRepairRequestListUrl']}" activeFilterName="${filterName}" activeFilterValue="${filterValue}"
                       page="${currentPage}" sortingParam="status"/>"><fmt:message key="label.repair_request.status"/></a>
                    <fileTags:showSortIcon sortingParam="status"/>
                </th>
                <th class="col-1">
                    <a href="<fileTags:hrefWithParameters href="${initParam['managerRepairRequestListUrl']}" activeFilterName="${filterName}" activeFilterValue="${filterValue}"
                       page="${currentPage}" sortingParam="createdAt"/>"><fmt:message key="label.created_at"/></a>
                    <fileTags:showSortIcon sortingParam="createdAt"/>
                </th>
                <th class="col-1">
                    <a href="<fileTags:hrefWithParameters href="${initParam['managerRepairRequestListUrl']}" activeFilterName="${filterName}" activeFilterValue="${filterValue}"
                       page="${currentPage}" sortingParam="customerFullName"/>"><fmt:message key="label.customer"/></a>
                    <fileTags:showSortIcon sortingParam="customerFullName"/>
                </th>
                <th class="col-3">
                    <a href="<fileTags:hrefWithParameters href="${initParam['managerRepairRequestListUrl']}" activeFilterName="${filterName}" activeFilterValue="${filterValue}"
                       page="${currentPage}" sortingParam="masterFullName"/>"><fmt:message key="label.master"/></a>
                    <fileTags:showSortIcon sortingParam="masterFullName"/>
                </th>
                <th class="col-1"><fmt:message key="label.actions"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="repairRequest" items="${entities}">
                <tr>
                    <td><c:out value="${repairRequest.shortDescription}"/></td>
                    <td class="form-group">
                        <c:choose>
                            <c:when test="${libTags:isRepairRequestCostCanBeChangedByRole(repairRequest, loggedUser.role)}">
                                <form action="${initParam['managerRepairRequestSetCostUrl']}" method="post">
                                    <input type="hidden" name="repairRequestId" value="${repairRequest.id}"/>
                                    <input type="number" name="cost" class="form-control mb-2" step="any" min="0"
                                           value="${repairRequest.cost}"/>
                                    <button type="submit" class="form-control btn btn-success"><fmt:message key="label.repair_request.cost.set"/></button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${repairRequest.cost}"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="form-group">
                        <c:choose>
                            <c:when test="${libTags:isRepairRequestStatusCanBeChangedByRole(repairRequest, loggedUser.role)}">
                                <form action="${initParam['managerRepairRequestSetStatusUrl']}" method="post">
                                    <input type="hidden" name="repairRequestId" value="${repairRequest.id}"/>
                                    <libTags:selectStatus status="${repairRequest.status}"/>
                                    <button type="submit" class="form-control btn btn-success"><fmt:message key="label.repair_request.status.set"/></button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${repairRequest.status}"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${libTags:formatLocalDateTime(repairRequest.createdAt)}</td>
                    <td>
                        <a href="${initParam['managerCustomerViewUrl']}?customerId=${repairRequest.customerId}">${repairRequest.customer.fullName}</a>
                    </td>
                    <td class="form-group">
                        <c:choose>
                            <c:when test="${libTags:isRepairRequestMasterCanBeChangedByRole(repairRequest, loggedUser.role)}">
                                <form action="${initParam['managerRepairRequestSetMasterUrl']}" method="post">
                                    <input type="hidden" name="repairRequestId" value="${repairRequest.id}"/>
                                    <div class="form-row">
                                        <div class="col">
                                            <select class="form-control mb-2" name="masterId">
                                                <option value="0">--<fmt:message key="label.master.choose"/>--</option>
                                                <c:forEach var="customer" items="${listMasters}">
                                                    <option value="${customer.id}"
                                                            <c:if test="${customer.id == repairRequest.masterId}">selected</c:if>>${customer.fullName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <c:if test="${repairRequest.master != null}">
                                            <div class="col-4">
                                                <a class="form-control btn btn-primary"
                                                   href="${initParam['managerMasterViewUrl']}?masterId=${repairRequest.masterId}"><fmt:message key="label.details"/></a>
                                            </div>
                                        </c:if>
                                    </div>
                                    <button type="submit" class="form-control btn btn-success"><fmt:message key="label.repair_request.master.set"/></button>
                                </form>
                            </c:when>
                            <c:when test="${repairRequest.master != null}">
                                <a href="${initParam['managerMasterViewUrl']}?masterId=${repairRequest.masterId}">${repairRequest.master.fullName}</a>
                            </c:when>
                            <c:otherwise><fmt:message key="label.master.was_not_set"/></c:otherwise>
                        </c:choose>
                    </td>
                    <td class="form-group">
                        <div class="form-row">
                            <div class="col mb-1">
                                <a class="form-control btn btn-primary"
                                   href="${initParam['managerRepairRequestViewUrl']}?repairRequestId=${repairRequest.id}"><fmt:message key="label.details"/></a>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <hr>
        <jsp:include page="/pages/common/layouts/_table-footer.jsp">
            <jsp:param name="href" value="${initParam['managerRepairRequestListUrl']}"/>
            <jsp:param name="activeFilterName" value="${filterName}"/>
            <jsp:param name="activeFilterValue" value="${filterValue}"/>
        </jsp:include>
    </div>
</div>
</body>
</html>