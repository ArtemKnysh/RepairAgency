<%--@elvariable id="loggedUser" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>
<%--@elvariable id="listRepairRequests" type="java.util.List"--%>
<%--@elvariable id="listMasters" type="java.util.List"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fileTags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="libTags" uri="http://com.epam.rd.java.basic.repairagency/lib" %>
<html>
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="List Of Repair Requests"/>
</jsp:include>
<body>
<fileTags:navbarForRole role="${loggedUser.role}" active="/manager/repair-request/list"/>
<div class="row">
    <div class="container col-md-11">
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
                <th class="col-2">Description</th>
                <th>Cost</th>
                <th class="col-2">Status</th>
                <th>Created at</th>
                <th>Customer</th>
                <th class="col-3">Master</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="repairRequest" items="${listRepairRequests}">
                <tr>
                    <td><c:out value="${repairRequest.shortDescription}"/></td>
                    <td class="form-group">
                        <c:choose>
                            <c:when test="${libTags:isRepairRequestCostCanBeChangedByRole(repairRequest, loggedUser.role)}">
                                <form action="${initParam['managerRepairRequestSetCostUrl']}" method="post">
                                    <input type="hidden" name="repairRequestId" value="${repairRequest.id}"/>
                                    <input type="number" name="cost" class="form-control mb-2" step="any" min="0"
                                           value="${repairRequest.cost}"/>
                                    <button type="submit" class="form-control btn btn-success">Set cost</button>
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
                                                <option value="0">--Choose master--</option>
                                                <c:forEach var="customer" items="${listMasters}">
                                                    <option value="${customer.id}"
                                                            <c:if test="${customer.id == repairRequest.masterId}">selected</c:if>>${customer.fullName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <c:if test="${repairRequest.master != null}">
                                            <div class="col-4">
                                                <a class="form-control btn btn-primary"
                                                   href="${initParam['managerMasterViewUrl']}?masterId=${repairRequest.masterId}">Details</a>
                                            </div>
                                        </c:if>
                                    </div>
                                    <button type="submit" class="form-control btn btn-success">Set master</button>
                                </form>
                            </c:when>
                            <c:when test="${repairRequest.master != null}">
                                <a href="${initParam['managerMasterViewUrl']}?masterId=${repairRequest.masterId}">${repairRequest.master.fullName}</a>
                            </c:when>
                            <c:otherwise>Master wasn't set</c:otherwise>
                        </c:choose>
                    </td>
                    <td class="form-group">
                        <div class="form-row">
                            <div class="col mb-1">
                                <a class="form-control btn btn-primary"
                                   href="${initParam['managerRepairRequestViewUrl']}?repairRequestId=${repairRequest.id}">Details</a>
                            </div>
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