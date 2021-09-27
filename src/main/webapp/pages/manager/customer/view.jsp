<%--@elvariable id="loggedUser" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>
<%--@elvariable id="customer" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="customerRepairRequests" type="java.util.List"--%>
<%--@elvariable id="listMasters" type="java.util.List"--%>
<%--@elvariable id="activeTab" type="java.lang.String"--%>
<%--@elvariable id="customerFeedbacks" type="java.util.List"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fileTags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="libTags" uri="http://com.epam.rd.java.basic.repairagency/lib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="localeKeys"/>
<html lang="${sessionScope.lang}">
<c:set var="title">
    <fmt:message key="label.customer.details"/>
</c:set>
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="${title}"/>
</jsp:include>
<body>
<fileTags:navbarForRole role="${loggedUser.role}"/>
<div class="container col-md-11">
    <div class="card mb-3">
        <div class="card-body">
            <h1 class="mb-3 text-center">${title}</h1>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0"><fmt:message key="label.user.full_name"/></h6>
                </div>
                <div class="col-sm-9 text-secondary">
                    <c:out value="${customer.fullName}"/>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0"><fmt:message key="label.user.email"/></h6>
                </div>
                <div class="col-sm-9 text-secondary">
                    <c:out value="${customer.email}"/>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0"><fmt:message key="label.user.phone_number"/></h6>
                </div>
                <div class="col-sm-9 text-secondary">
                    <c:out value="${customer.phoneNumber}"/>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0"><fmt:message key="label.account_actions"/></h6>
                </div>
                <div class="col-sm-9 text-secondary">
                    <form action="${initParam['managerCustomerAccountTransferUrl']}" method="post" class="form-row">
                        <input type="hidden" name="customerId" value="${customer.id}"/>
                        <div class="col">
                            <input type="number" name="amount" class="form-control" step="any" min="1" value="1"/>
                        </div>
                        <div class="col-sm-4">
                            <button type="submit" class="form-control btn btn-success"><fmt:message
                                    key="label.manager.transfer"/></button>
                        </div>
                    </form>
                </div>
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
            <ul class="nav nav-tabs">
                <li class="nav-item">
                    <a class="nav-link <c:if test="${activeTab == 'repairRequests'}">active</c:if>" data-toggle="tab"
                       href="#repairRequests"><fmt:message key="label.repair_requests"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <c:if test="${activeTab == 'feedbacks'}">active</c:if>" data-toggle="tab"
                       href="#feedbacks"><fmt:message key="label.feedbacks"/></a>
                </li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane fade <c:if test="${activeTab == 'repairRequests'}">active show</c:if>"
                     id="repairRequests">
                    <h3 class="text-center mt-3"><fmt:message key="label.repair_request.list"/></h3>
                    <hr>
                    <table class="table table-bordered table-striped mb-0">
                        <thead>
                        <tr>
                            <th class="col-2"><fmt:message key="label.repair_request.description"/></th>
                            <th><fmt:message key="label.repair_request.cost"/></th>
                            <th class="col-2"><fmt:message key="label.repair_request.status"/></th>
                            <th><fmt:message key="label.created_at"/></th>
                            <th class="col-3"><fmt:message key="label.master"/></th>
                            <th class="col-1"><fmt:message key="label.actions"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="repairRequest" items="${customerRepairRequests}">
                            <tr>
                                <td><c:out value="${repairRequest.shortDescription}"/></td>
                                <td class="form-group">
                                    <c:choose>
                                        <c:when test="${libTags:isRepairRequestCostCanBeChangedByRole(repairRequest, loggedUser.role)}">
                                            <form action="${initParam['managerRepairRequestSetCostUrl']}" method="post">
                                                <input type="hidden" name="repairRequestId"
                                                       value="${repairRequest.id}"/>
                                                <input type="number" name="cost" class="form-control mb-2" step="any"
                                                       min="0" value="${repairRequest.cost}"/>
                                                <button type="submit" class="form-control btn btn-success"><fmt:message
                                                        key="label.repair_request.cost.set"/></button>
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
                                            <form action="${initParam['managerRepairRequestSetStatusUrl']}"
                                                  method="post">
                                                <input type="hidden" name="repairRequestId"
                                                       value="${repairRequest.id}"/>
                                                <libTags:selectStatus status="${repairRequest.status}"/>
                                                <button type="submit" class="form-control btn btn-success"><fmt:message key="label.repair_request.status.set"/> </button>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${repairRequest.status}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${libTags:formatLocalDateTime(repairRequest.createdAt)}</td>
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
                </div>
                <div class="tab-pane fade <c:if test="${activeTab == 'feedbacks'}">active show</c:if>" id="feedbacks">
                    <h3 class="text-center mt-3"><fmt:message key="label.feedback.list"/></h3>
                    <hr>
                    <table class="table table-bordered table-striped mb-0">
                        <thead>
                        <tr>
                            <th><fmt:message key="label.feedback.text"/></th>
                            <th><fmt:message key="label.created_at"/></th>
                            <th><fmt:message key="label.master"/></th>
                            <th><fmt:message key="label.actions"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="feedback" items="${customerFeedbacks}">
                            <tr>
                                <td><c:out value="${feedback.text}"/></td>
                                <td>${libTags:formatLocalDateTime(feedback.createdAt)}</td>
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
                                                        <input type="hidden" name="activeTab" value="feedbacks"/>
                                                        <button type="submit" class="form-control btn btn-primary"><fmt:message key="label.feedback.show"/></button>
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    <form action="${initParam['managerFeedbackHideUrl']}" method="post">
                                                        <input type="hidden" name="activeTab" value="feedbacks"/>
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
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>