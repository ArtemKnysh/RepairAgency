<%--@elvariable id="loggedUser" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>
<%--@elvariable id="listRepairRequests" type="java.util.List"--%>
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
        <h3 class="text-center">List of created repair requests</h3>
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
                <th>Description</th>
                <th>Cost</th>
                <th>Status</th>
                <th>Created at</th>
                <th>Master</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="repairRequest" items="${listRepairRequests}">
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
                    <td class="form-group">
                        <c:choose>
                            <c:when test="${repairRequest.master != null}">
                                <a href="${initParam['customerMasterViewUrl']}?masterId=${repairRequest.masterId}">${repairRequest.master.fullName}</a>
                            </c:when>
                            <c:otherwise>Master wasn't set</c:otherwise>
                        </c:choose>
                    </td>
                    <td><c:out value="${repairRequest.createdAt}"/></td>
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
    </div>
</div>
</body>
</html>