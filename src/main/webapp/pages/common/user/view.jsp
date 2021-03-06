<%--@elvariable id="loggedUser" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>
<%--@elvariable id="balance" type="java.lang.Double"--%>
<%--@elvariable id="entities" type="java.util.List"--%>
<%--@elvariable id="currentPage" type="java.lang.Integer"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fileTags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="libTags" uri="http://com.epam.rd.java.basic.repairagency/lib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="localeKeys"/>
<html lang="${sessionScope.lang}">
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="${param.title}"/>
</jsp:include>
<body>
<fileTags:navbarForRole role="${loggedUser.role}" active="${param.userViewUrl}"/>
<div class="container col-md-6">
    <div class="card mb-3">
        <div class="card-body">
            <div class="row mb-3">
                <div class="col">
                    <h1>${param.title}</h1>
                </div>
                <div class="col-3 pt-2">
                    <a class="form-control btn btn-primary" href="${param.userEditUrl}"><fmt:message key="label.edit"/></a>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4">
                    <h6 class="mb-0"><fmt:message key="label.user.full_name"/></h6>
                </div>
                <div class="col-sm-8 text-secondary">
                    <c:out value="${loggedUser.fullName}"/>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-4">
                    <h6 class="mb-0"><fmt:message key="label.user.email"/></h6>
                </div>
                <div class="col-sm-8 text-secondary">
                    <c:out value="${loggedUser.email}"/>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-4">
                    <h6 class="mb-0"><fmt:message key="label.user.phone_number"/></h6>
                </div>
                <div class="col-sm-8 text-secondary">
                    <c:out value="${loggedUser.phoneNumber}"/>
                </div>
            </div>
            <hr>
            <h3 class="text-center"><fmt:message key="label.account_details"/></h3>
            <hr>
            <div class="row">
                <div class="col-sm-4">
                    <h6 class="mb-0"><fmt:message key="label.account_details.balance"/></h6>
                </div>
                <div class="col-sm-8 text-secondary">
                    <c:out value="${balance}"/>
                </div>
            </div>
            <hr>
            <form action="${param.userTopUpAccountUrl}" method="post" class="form-row">
                <div class="col-sm-8">
                    <input type="number" name="amount" class="form-control" step="any" min="1" value="1"/>
                </div>
                <div class="col-sm-4">
                    <button type="submit" class="form-control btn btn-success"><fmt:message key="label.account_details.top_up"/></button>
                </div>
            </form>
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
                    <th>
                        <a href="<fileTags:hrefWithParameters href="${param.userViewUrl}" page="${currentPage}"
                           sortingParam="amount"/>"><fmt:message key="label.account_details.amount"/></a>
                        <fileTags:showSortIcon sortingParam="amount"/>
                    </th>
                    <th>
                        <a href="<fileTags:hrefWithParameters href="${param.userViewUrl}" page="${currentPage}"
                           sortingParam="createdAt"/>"><fmt:message key="label.created_at"/></a>
                        <fileTags:showSortIcon sortingParam="createdAt"/>
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="accountTransaction" items="${entities}">
                    <tr>
                        <td><c:out value="${accountTransaction.amountWithSign}"/></td>
                        <td>${libTags:formatLocalDateTime(accountTransaction.createdAt)}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <hr>
            <jsp:include page="/pages/common/layouts/_table-footer.jsp">
                <jsp:param name="href" value="${param.customerListUrl}"/>
            </jsp:include>
        </div>
    </div>
</div>
</body>
</html>