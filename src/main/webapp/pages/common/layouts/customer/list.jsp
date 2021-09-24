<%--@elvariable id="loggedUser" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>
<%--@elvariable id="entities" type="java.util.List"--%>
<%--@elvariable id="recordsOnPage" type="java.lang.Integer"--%>
<%--@elvariable id="activeParam" type="java.lang.String"--%>
<%--@elvariable id="activeType" type="java.lang.String"--%>
<%--@elvariable id="currentPage" type="java.lang.Integer"--%>
<%--@elvariable id="numberOfPages" type="java.lang.Integer"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fileTags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="libTags" uri="http://com.epam.rd.java.basic.repairagency/lib" %>
<html>
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="List Of Customers"/>
</jsp:include>
<body>
<fileTags:navbarForRole role="${loggedUser.role}" active="${param.customerListUrl}"/>
<div class="row">
    <div class="container">
        <h3 class="text-center">List Of Customers</h3>
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
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${param.customerListUrl}" page="${currentPage}"
                    recordsOnPage="${recordsOnPage}" sortingParam="firstName" activeParam="${activeParam}"
                    activeType="${activeType}"/>">First Name</a>
                    <fileTags:showSortIcon activeParam="${activeParam}" activeType="${activeType}"
                                           sortingParam="firstName"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${param.customerListUrl}" page="${currentPage}"
                    recordsOnPage="${recordsOnPage}" sortingParam="lastName" activeParam="${activeParam}"
                    activeType="${activeType}"/>">Last Name</a>
                    <fileTags:showSortIcon activeParam="${activeParam}" activeType="${activeType}"
                                           sortingParam="lastName"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${param.customerListUrl}" page="${currentPage}"
                    recordsOnPage="${recordsOnPage}" sortingParam="email" activeParam="${activeParam}"
                    activeType="${activeType}"/>">Email</a>
                    <fileTags:showSortIcon activeParam="${activeParam}" activeType="${activeType}"
                                           sortingParam="email"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${param.customerListUrl}" page="${currentPage}"
                    recordsOnPage="${recordsOnPage}" sortingParam="phoneNumber" activeParam="${activeParam}"
                    activeType="${activeType}"/>">Phone Number</a>
                    <fileTags:showSortIcon activeParam="${activeParam}" activeType="${activeType}"
                                           sortingParam="phoneNumber"/>
                </th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="customer" items="${entities}">
                <tr>
                    <td><c:out value="${customer.firstName}"/></td>
                    <td><c:out value="${customer.lastName}"/></td>
                    <td><c:out value="${customer.email}"/></td>
                    <td><c:out value="${customer.phoneNumber}"/></td>
                    <td class="form-group">
                        <div class="form-row">
                            <div class="col mb-1">
                                <a class="form-control btn btn-primary"
                                   href="${param.customerViewUrl}?customerId=${customer.id}">Details</a>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <hr>
        <div class="row mb-3">
            <form action="${param.customerListUrl}" method="get" class="col form-row mr-auto">
                <input type="hidden" name="page" value="${currentPage}">
                <input type="hidden" name="activeParam" value="${activeParam}">
                <input type="hidden" name="activeType" value="${activeType}">
                <div class="col-auto">Show</div>
                <div class="col-auto">
                    <select name="recordsOnPage" class="custom-select">
                        <option value="5" <c:if test="${recordsOnPage == 5}">selected</c:if>>5</option>
                        <option value="10" <c:if test="${recordsOnPage == 10}">selected</c:if>>10</option>
                        <option value="25" <c:if test="${recordsOnPage == 25}">selected</c:if>>25</option>
                        <option value="50" <c:if test="${recordsOnPage == 50}">selected</c:if>>50</option>
                    </select>
                </div>
                <div class="col-auto"> records on page</div>
                <div class="col-auto">
                    <button type="submit" class="form-control btn btn-success">Apply</button>
                </div>
            </form>
            <div class="col-auto">
                <ul class="pagination mb-0 float-right">
                    <li class="page-item
                    <c:if test="${numberOfPages == 1 || currentPage == 1}">disabled</c:if>">
                        <a class="page-link"
                           href="<fileTags:hrefWithParameters href="${param.customerListUrl}" page="${currentPage - 1}"
                           activeParam="${activeParam}" activeType="${activeType}" recordsOnPage="${recordsOnPage}"
                           />">Previous</a>
                    </li>
                    <c:forEach begin="1" end="${numberOfPages}" var="num">
                        <li class="page-item <c:if test="${currentPage == num}">active</c:if>">
                            <a class="page-link"
                               href="<fileTags:hrefWithParameters href="${param.customerListUrl}" page="${num}"
                           activeParam="${activeParam}" activeType="${activeType}" recordsOnPage="${recordsOnPage}"
                           />">${num}</a>
                        </li>
                    </c:forEach>
                    <li class="page-item <c:if test="${currentPage == numberOfPages}">disabled</c:if>">
                        <a class="page-link"
                           href="<fileTags:hrefWithParameters href="${param.customerListUrl}" page="${currentPage + 1}"
                           activeParam="${activeParam}" activeType="${activeType}" recordsOnPage="${recordsOnPage}"
                           />">Next</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>