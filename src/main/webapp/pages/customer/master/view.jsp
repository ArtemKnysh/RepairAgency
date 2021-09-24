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
<html>
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="Master Ditails"/>
</jsp:include>
<body>
<fileTags:navbarForRole role="${loggedUser.role}"/>
<div class="container col-md-11">
    <div class="card mb-3">
        <div class="card-body">
            <h1 class="mb-3 text-center">Master Details</h1>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0">Full Name</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                    <c:out value="${master.fullName}"/>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0">Email</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                    <c:out value="${master.email}"/>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0">Phone Number</h6>
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
                       href="#repairRequests">Repair Requests</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <c:if test="${activeTab == 'feedbacks'}">active</c:if>" data-toggle="tab"
                       href="#feedbacks">Feedbacks</a>
                </li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane fade <c:if test="${activeTab == 'repairRequests'}">active show</c:if>"
                     id="repairRequests">
                    <h3 class="text-center mt-3">List of Repair Requests</h3>
                    <hr>
                    <table class="table table-bordered table-striped mb-0">
                        <thead>
                        <tr>
                            <th>Description</th>
                            <th>Cost</th>
                            <th>Status</th>
                            <th>Created at</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="repairRequest" items="${masterRepairRequests}">
                            <tr>
                                <td><c:out value="${repairRequest.shortDescription}"/></td>
                                <td><c:out value="${repairRequest.cost}"/></td>
                                <td><c:out value="${repairRequest.status}"/></td>
                                <td><c:out value="${repairRequest.createdAt}"/></td>
                                <td class="form-group">
                                    <div class="form-row">
                                        <div class="col mb-1">
                                            <a class="form-control btn btn-primary"
                                               href="${initParam['customerRepairRequestViewUrl']}?repairRequestId=${repairRequest.id}">Details</a>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="tab-pane fade <c:if test="${activeTab == 'feedbacks'}">active show</c:if>" id="feedbacks">
                    <h3 class="text-center mt-3">List of Feedbacks</h3>
                    <hr>
                    <c:if test="${isCustomerCanLeaveFeedbackForMaster}">
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Your Feedback</h6>
                            </div>
                            <div class="col-sm-9 text-secondary form-group mb-2">
                                <form action="${initParam['customerFeedbackEditUrl']}" method="post">
                                    <input type="hidden" name="masterId" value="${master.id}"/>
                                    <input type="hidden" name="feedbackId" value="${customerFeedback.id}"/>
                                    <input type="hidden" name="activeTab" value="feedbacks"/>
                                    <textarea name="text" rows="4" class="form-control mb-2" required
                                    ><c:out value="${customerFeedback.text}"/></textarea>
                                    <button type="submit" class="form-control btn btn-success">Save</button>
                                </form>
                            </div>
                        </div>
                    </c:if>
                    <table class="table table-bordered table-striped mb-0">
                        <thead>
                        <tr>
                            <th>Text</th>
                            <th>Created At</th>
                            <th>Customer</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="feedback" items="${masterFeedbacks}">
                            <tr>
                                <td><c:out value="${feedback.text}"/></td>
                                <td><c:out value="${feedback.createdAt}"/></td>
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