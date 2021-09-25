<%--@elvariable id="loggedUser" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>
<%--@elvariable id="entities" type="java.util.List"--%>
<%--@elvariable id="currentPage" type="java.lang.Integer"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fileTags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="libTags" uri="http://com.epam.rd.java.basic.repairagency/lib" %>
<html>
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="List Of Repair Requests"/>
</jsp:include>
<body>
<fileTags:navbarForRole role="${loggedUser.role}" active="${initParam['masterRepairRequestListUrl']}"/>
<div class="row">
    <div class="container">
        <h3 class="text-center">List of repair requests</h3>
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
                <th class="col-3">
                    <a href="<fileTags:hrefWithParameters href="${initParam['masterRepairRequestListUrl']}"
                       page="${currentPage}" sortingParam="description"/>">Description</a>
                    <fileTags:showSortIcon sortingParam="description"/>
                </th>
                <th class="col-1">
                    <a href="<fileTags:hrefWithParameters href="${initParam['masterRepairRequestListUrl']}"
                       page="${currentPage}" sortingParam="cost"/>">Cost</a>
                    <fileTags:showSortIcon sortingParam="cost"/>
                </th>
                <th class="col-3">
                    <a href="<fileTags:hrefWithParameters href="${initParam['masterRepairRequestListUrl']}"
                       page="${currentPage}" sortingParam="status"/>">Status</a>
                    <fileTags:showSortIcon sortingParam="status"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${initParam['masterRepairRequestListUrl']}"
                       page="${currentPage}" sortingParam="createdAt"/>">Created At</a>
                    <fileTags:showSortIcon sortingParam="createdAt"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${initParam['masterRepairRequestListUrl']}"
                       page="${currentPage}" sortingParam="customerFullName"/>">Customer</a>
                    <fileTags:showSortIcon sortingParam="customerFullName"/>
                </th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="repairRequest" items="${entities}">
                <tr>
                    <td class="col-3"><c:out value="${repairRequest.description}"/></td>
                    <td><c:out value="${repairRequest.cost}"/></td>
                    <td class="form-group">
                        <c:choose>
                            <c:when test="${libTags:isRepairRequestStatusCanBeChangedByRole(repairRequest, loggedUser.role)}">
                                <form action="${initParam['masterRepairRequestSetStatusUrl']}" method="post">
                                    <input type="hidden" name="repairRequestId" value="${repairRequest.id}"/>
                                    <libTags:selectStatus status="${repairRequest.status}"/>
                                    <button type="submit" class="form-control btn btn-success">Set status</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${repairRequest.status}"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td><c:out value="${repairRequest.createdAt}"/></td>
                    <td>
                        <a href="${initParam['masterCustomerViewUrl']}?customerId=${repairRequest.customerId}">${repairRequest.customer.fullName}</a>
                    </td>
                    <td class="form-group">
                        <div class="form-row">
                            <div class="col mb-1">
                                <a class="form-control btn btn-primary"
                                   href="${initParam['masterRepairRequestViewUrl']}?repairRequestId=${repairRequest.id}">Details</a>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <hr>
        <jsp:include page="/pages/common/layouts/_table-footer.jsp">
            <jsp:param name="href" value="${initParam['masterRepairRequestListUrl']}"/>
        </jsp:include>
    </div>
</div>
</body>
</html>