<%--@elvariable id="loggedUser" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>
<%--@elvariable id="repairRequest" type="com.epam.rd.java.basic.repairagency.entity.RepairRequest"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fileTags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="libTags" uri="http://com.epam.rd.java.basic.repairagency/lib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="localeKeys"/>
<html lang="${sessionScope.lang}">
<c:set var="title">
    <fmt:message key="label.repair_request.details"/>
</c:set>
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="${title}"/>
</jsp:include>
<body>
<fileTags:navbarForRole role="${loggedUser.role}"/>
<div class="container col-md-8">
    <div class="card mb-3">
        <div class="card-body">
            <h1 class="mb-3 text-center">${title}</h1>
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
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0"><fmt:message key="label.repair_request.description"/></h6>
                </div>
                <div class="col-sm-9 text-secondary form-group mb-0">
                    <c:choose>
                        <c:when test="${libTags:isRepairRequestCanBeEdited(repairRequest)}">
                            <form action="${initParam['customerRepairRequestEditDescriptionUrl']}" method="post">
                                <input type="hidden" name="repairRequestId" value="${repairRequest.id}"/>
                                <textarea name="description" id="edit-repair-request-description"
                                          rows="6" class="form-control mb-2" required
                                ><c:out value="${repairRequest.description}"/></textarea>
                                <button type="submit" class="form-control btn btn-success"><fmt:message key="label.save"/></button>
                            </form>
                        </c:when>
                        <c:otherwise><c:out value="${repairRequest.description}"/></c:otherwise>
                    </c:choose>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0"><fmt:message key="label.repair_request.cost"/></h6>
                </div>
                <div class="col text-secondary"><c:out value="${repairRequest.cost}"/></div>
                <c:if test="${libTags:isRepairRequestCanBePaidByCustomer(repairRequest)}">
                    <form action="${initParam['customerRepairRequestPayUrl']}" method="post" class="col-3">
                        <input type="hidden" name="repairRequestId" value="${repairRequest.id}"/>
                        <button type="submit" class="form-control btn btn-success"><fmt:message key="label.repair_request.pay"/></button>
                    </form>
                </c:if>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0"><fmt:message key="label.repair_request.status"/></h6>
                </div>
                <div class="col text-secondary">
                    <c:out value="${repairRequest.status}"/>
                </div>
                <c:if test="${libTags:isRepairRequestCanBeCanceled(repairRequest)}">
                    <div class="col-3">
                        <form action="${initParam['customerRepairRequestCancelUrl']}" method="post">
                            <input type="hidden" name="repairRequestId" value="${repairRequest.id}"/>
                            <button type="submit" class="form-control btn btn-danger"><fmt:message key="label.repair_request.status.cancel"/></button>
                        </form>
                    </div>
                </c:if>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0"><fmt:message key="label.created_at"/></h6>
                </div>
                <div class="col-sm-9 text-secondary">
                    ${libTags:formatLocalDateTime(repairRequest.createdAt)}
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0"><fmt:message key="label.master"/></h6>
                </div>
                <div class="col-sm-9 text-secondary form-group mb-0">
                    <c:choose>
                        <c:when test="${repairRequest.master != null}">
                            <a href="${initParam['customerMasterViewUrl']}?masterId=${repairRequest.masterId}">${repairRequest.master.fullName}</a>
                        </c:when>
                        <c:otherwise><fmt:message key="label.master.was_not_set"/></c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>