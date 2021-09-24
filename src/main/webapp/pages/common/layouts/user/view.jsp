<%--@elvariable id="loggedUser" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>
<%--@elvariable id="balance" type="java.lang.Double"--%>
<%--@elvariable id="userAccountTransactions" type="java.util.List"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fileTags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="libTags" uri="http://com.epam.rd.java.basic.repairagency/lib" %>
<html>
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="${param.title}"/>
</jsp:include>
<body>
<fileTags:navbarForRole role="${loggedUser.role}" active="${param.userViewUrl}"/>
<div class="container col-md-5">
    <div class="card mb-3">
        <div class="card-body">
            <div class="row mb-3">
                <div class="col">
                    <h1>${param.title}</h1>
                </div>
                <div class="col-3 pt-2">
                    <a class="form-control btn btn-primary" href="${param.userEditUrl}">Edit</a>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4">
                    <h6 class="mb-0">Full Name</h6>
                </div>
                <div class="col-sm-8 text-secondary">
                    <c:out value="${loggedUser.fullName}"/>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-4">
                    <h6 class="mb-0">Email</h6>
                </div>
                <div class="col-sm-8 text-secondary">
                    <c:out value="${loggedUser.email}"/>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-4">
                    <h6 class="mb-0">Phone Number</h6>
                </div>
                <div class="col-sm-8 text-secondary">
                    <c:out value="${loggedUser.phoneNumber}"/>
                </div>
            </div>
            <hr>
            <h3 class="text-center">Account Details</h3>
            <hr>
            <div class="row">
                <div class="col-sm-4">
                    <h6 class="mb-0">Account Balance</h6>
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
                    <button type="submit" class="form-control btn btn-success">Top-up</button>
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
                    <th>Amount</th>
                    <th>Created at</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="accountTransaction" items="${userAccountTransactions}">
                    <tr>
                        <td><c:out value="${accountTransaction.amountWithSign}"/></td>
                        <td><c:out value="${accountTransaction.createdAt}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>