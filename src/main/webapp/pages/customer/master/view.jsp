<%--@elvariable id="loggedUser" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="master" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>
<%--@elvariable id="masterRepairRequests" type="java.util.List"--%>
<%--@elvariable id="isCustomerCanLeaveFeedbackForMaster" type="java.lang.Boolean"--%>
<%--@elvariable id="masterFeedbacks" type="java.util.List"--%>
<%--@elvariable id="activeTab" type="java.lang.String"--%>
<%--@elvariable id="customerFeedback" type="com.epam.rd.java.basic.repairagency.entity.Feedback"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fileTags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="libTags" uri="http://com.epam.rd.java.basic.repairagency/lib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="localeKeys"/>
<html lang="${sessionScope.lang}">
<c:set var="title">
    <fmt:message key="label.master.details"/>
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
                    <c:out value="${master.fullName}"/>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0"><fmt:message key="label.user.email"/></h6>
                </div>
                <div class="col-sm-9 text-secondary">
                    <c:out value="${master.email}"/>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0"><fmt:message key="label.user.phone_number"/></h6>
                </div>
                <div class="col-sm-9 text-secondary">
                    <c:out value="${master.phoneNumber}"/>
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
                            <th><fmt:message key="label.repair_request.description"/></th>
                            <th><fmt:message key="label.repair_request.cost"/></th>
                            <th><fmt:message key="label.repair_request.status"/></th>
                            <th><fmt:message key="label.created_at"/></th>
                            <th><fmt:message key="label.actions"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="repairRequest" items="${masterRepairRequests}">
                            <tr>
                                <td><c:out value="${repairRequest.shortDescription}"/></td>
                                <td><c:out value="${repairRequest.cost}"/></td>
                                <td><c:out value="${repairRequest.status}"/></td>
                                <td>${libTags:formatLocalDateTime(repairRequest.createdAt)}</td>
                                <td class="form-group">
                                    <div class="form-row">
                                        <div class="col mb-1">
                                            <a class="form-control btn btn-primary"
                                               href="${initParam['customerRepairRequestViewUrl']}?repairRequestId=${repairRequest.id}"><fmt:message key="label.details"/></a>
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
                    <c:if test="${isCustomerCanLeaveFeedbackForMaster}">
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0"><fmt:message key="label.customer.feedback"/></h6>
                            </div>
                            <div class="col-sm-9 text-secondary form-group mb-2">
                                <form action="${initParam['customerFeedbackEditUrl']}" method="post">
                                    <input type="hidden" name="masterId" value="${master.id}"/>
                                    <input type="hidden" name="feedbackId" value="${customerFeedback.id}"/>
                                    <input type="hidden" name="activeTab" value="feedbacks"/>
                                    <textarea name="text" rows="4" class="form-control mb-2" required
                                    ><c:out value="${customerFeedback.text}"/></textarea>
                                    <button type="submit" class="form-control btn btn-success"><fmt:message key="label.save"/></button>
                                </form>
                            </div>
                        </div>
                    </c:if>
                    <table class="table table-bordered table-striped mb-0">
                        <thead>
                        <tr>
                            <th><fmt:message key="label.feedback.text"/></th>
                            <th><fmt:message key="label.created_at"/></th>
                            <th><fmt:message key="label.customer"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="feedback" items="${masterFeedbacks}">
                            <tr>
                                <td><c:out value="${feedback.text}"/></td>
                                <td>${libTags:formatLocalDateTime(feedback.createdAt)}</td>
                                <td>
                                    <a href="${initParam['customerCustomerViewUrl']}?customerId=${feedback.customerId}">${feedback.customer.fullName}</a>
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