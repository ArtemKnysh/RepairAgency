<%--@elvariable id="loggedUser" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>
<%--@elvariable id="entities" type="java.util.List"--%>
<%--@elvariable id="currentPage" type="java.lang.Integer"--%>
<%--@elvariable id="activeParam" type="java.lang.String"--%>
<%--@elvariable id="activeType" type="java.lang.String"--%>
<%--@elvariable id="recordsOnPage" type="java.lang.Integer"--%>
<%--@elvariable id="statuses" type="java.util.List"--%>
<%--@elvariable id="filterName" type="java.lang.String"--%>
<%--@elvariable id="filterValue" type="java.lang.String"--%>
<%--@elvariable id="listMasters" type="java.util.List"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fileTags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="libTags" uri="http://com.epam.rd.java.basic.repairagency/lib" %>
<html>
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="List Of Created Repair Requests"/>
</jsp:include>
<body>
<fileTags:navbarForRole role="${loggedUser.role}" active="${initParam['customerRepairRequestListUrl']}"/>
<div class="row">
    <div class="container">
        <h3 class="text-center">List Of Created Repair Requests</h3>
        <hr>
        <div class="row">
            <div class="col-sm-4">
                <h6 class="mb-0">Description Of New Repair Request</h6>
            </div>
            <div class="col-sm-8 text-secondary form-group mb-3">
                <form action="${initParam['customerRepairRequestNewUrl']}" method="post">
                    <textarea name="description" rows="4" class="form-control mb-2" required></textarea>
                    <button type="submit" class="form-control btn btn-success">Create repair request</button>
                </form>
            </div>
        </div>
        <hr>
        <div class="form-row">
            <div class="col-auto">
                <a class="form-control btn btn-primary"
                   href="<fileTags:hrefWithParameters href="${initParam['customerRepairRequestListUrl']}" page="${currentPage}"/>">Clear
                    Filter</a>
            </div>
            <form action="${initParam['customerRepairRequestListUrl']}" method="get" class="col">
                <div class="form-row">
                    <input type="hidden" name="page" value="${currentPage}">
                    <input type="hidden" name="activeParam" value="${activeParam}">
                    <input type="hidden" name="activeType" value="${activeType}">
                    <input type="hidden" name="recordsOnPage" value=${recordsOnPage}>
                    <input type="hidden" name="filterName" value="statusId">
                    <div class="col-auto">
                        <h6>Filter By Status</h6>
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
                        <button type="submit" class="form-control btn btn-success">Apply</button>
                    </div>
                </div>
            </form>
            <form action="${initParam['customerRepairRequestListUrl']}" method="get" class="col">
                <div class="form-row">
                    <input type="hidden" name="page" value="${currentPage}">
                    <input type="hidden" name="activeParam" value="${activeParam}">
                    <input type="hidden" name="activeType" value="${activeType}">
                    <input type="hidden" name="recordsOnPage" value=${recordsOnPage}>
                    <input type="hidden" name="filterName" value="masterId">
                    <div class="col-auto">
                        <h6>Filter By Master</h6>
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
                        <button type="submit" class="form-control btn btn-success">Apply</button>
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
                <th>
                    <a href="<fileTags:hrefWithParameters href="${initParam['customerRepairRequestListUrl']}" activeFilterName="${filterName}" activeFilterValue="${filterValue}"
                       page="${currentPage}" sortingParam="description"/>">Description</a>
                    <fileTags:showSortIcon sortingParam="description"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${initParam['customerRepairRequestListUrl']}" activeFilterName="${filterName}" activeFilterValue="${filterValue}"
                       page="${currentPage}" sortingParam="cost"/>">Cost</a>
                    <fileTags:showSortIcon sortingParam="cost"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${initParam['customerRepairRequestListUrl']}" activeFilterName="${filterName}" activeFilterValue="${filterValue}"
                       page="${currentPage}" sortingParam="status"/>">Status</a>
                    <fileTags:showSortIcon sortingParam="status"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${initParam['customerRepairRequestListUrl']}" activeFilterName="${filterName}" activeFilterValue="${filterValue}"
                       page="${currentPage}" sortingParam="createdAt"/>">Created At</a>
                    <fileTags:showSortIcon sortingParam="createdAt"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${initParam['customerRepairRequestListUrl']}" activeFilterName="${filterName}" activeFilterValue="${filterValue}"
                       page="${currentPage}" sortingParam="masterFullName"/>">Master</a>
                    <fileTags:showSortIcon sortingParam="masterFullName"/>
                </th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="repairRequest" items="${entities}">
                <tr>
                    <td><c:out value="${repairRequest.shortDescription}"/></td>
                    <td class="form-group">
                        <div class="form-row">
                            <div class="col"><c:out value="${repairRequest.cost}"/></div>
                            <c:if test="${libTags:isRepairRequestCanBePaidByCustomer(repairRequest)}">
                                <form action="${initParam['customerRepairRequestPayUrl']}" method="post" class="col">
                                    <input type="hidden" name="repairRequestId" value="${repairRequest.id}"/>
                                    <button type="submit" class="form-control btn btn-success">Pay</button>
                                </form>
                            </c:if>
                        </div>
                    </td>
                    <td><c:out value="${repairRequest.status}"/></td>
                    <td><c:out value="${repairRequest.createdAt}"/></td>
                    <td class="form-group">
                        <c:choose>
                            <c:when test="${repairRequest.master != null}">
                                <a href="${initParam['customerMasterViewUrl']}?masterId=${repairRequest.masterId}">${repairRequest.master.fullName}</a>
                            </c:when>
                            <c:otherwise>Master wasn't set</c:otherwise>
                        </c:choose>
                    </td>
                    <td class="form-group">
                        <div class="form-row">
                            <div class="col mb-1">
                                <a class="form-control btn btn-primary"
                                   href="${initParam['customerRepairRequestViewUrl']}?repairRequestId=${repairRequest.id}">Details</a>
                            </div>
                            <c:if test="${libTags:isRepairRequestCanBeCanceled(repairRequest)}">
                                <div class="col mb-1">
                                    <form action="${initParam['customerRepairRequestCancelUrl']}" method="post">
                                        <input type="hidden" name="repairRequestId" value="${repairRequest.id}"/>
                                        <button type="submit" class="form-control btn btn-danger">Cancel</button>
                                    </form>
                                </div>
                            </c:if>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <hr>
        <jsp:include page="/pages/common/layouts/_table-footer.jsp">
            <jsp:param name="href" value="${initParam['customerRepairRequestListUrl']}"/>
            <jsp:param name="activeFilterName" value="${filterName}"/>
            <jsp:param name="activeFilterValue" value="${filterValue}"/>
        </jsp:include>
    </div>
</div>
</body>
</html>