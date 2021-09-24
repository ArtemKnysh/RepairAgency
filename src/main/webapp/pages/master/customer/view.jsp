<%--@elvariable id="loggedUser" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>
<%--@elvariable id="customer" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="customerRepairRequests" type="java.util.List"--%>
<%--@elvariable id="customerFeedback" type="com.epam.rd.java.basic.repairagency.entity.Feedback"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fileTags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="libTags" uri="http://com.epam.rd.java.basic.repairagency/lib" %>
<html>
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="Customer Ditails"/>
</jsp:include>
<body>
<fileTags:navbarForRole role="${loggedUser.role}"/>
<div class="container col-md-11">
    <div class="card mb-3">
        <div class="card-body">
            <h1 class="mb-3 text-center">Customer Details</h1>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0">Full Name</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                    <c:out value="${customer.fullName}"/>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0">Email</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                    <c:out value="${customer.email}"/>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0">Phone Number</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                    <c:out value="${customer.phoneNumber}"/>
                </div>
            </div>
            <c:if test="${customerFeedback != null}">
                <hr>
                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0">Customer Feedback</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        <c:out value="${customerFeedback.text}"/>
                    </div>
                </div>
            </c:if>
            <hr>
            <h3 class="text-center">List of Repair Requests</h3>
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
            <table class="table table-bordered table-striped mb-0">
                <thead>
                <tr>
                    <th>Description</th>
                    <th>Cost</th>
                    <th>Status</th>
                    <th>Created at</th>
                    <th class="col-2">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="repairRequest" items="${customerRepairRequests}">
                    <tr>
                        <td><c:out value="${repairRequest.shortDescription}"/></td>
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
        </div>
    </div>
</div>
</body>
</html>