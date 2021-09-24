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
                <th class="col-3">Description</th>
                <th class="col-1">Cost</th>
                <th class="col-3">Status</th>
                <th>Created at</th>
                <th>Customer</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="repairRequest" items="${listRepairRequests}">
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
    </div>
</div>
</body>
</html>