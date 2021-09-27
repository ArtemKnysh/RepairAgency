<%--@elvariable id="loggedUser" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>
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
<c:set var="title">
    <fmt:message key="label.customer.list"/>
</c:set>
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="${title}"/>
</jsp:include>
<body>
<fileTags:navbarForRole role="${loggedUser.role}" active="${param.customerListUrl}"/>
<div class="row">
    <div class="container col-md-10">
        <h3 class="text-center">${title}</h3>
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
                       sortingParam="firstName"/>"><fmt:message key="label.user.first_name"/></a>
                    <fileTags:showSortIcon sortingParam="firstName"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${param.customerListUrl}" page="${currentPage}"
                    sortingParam="lastName"/>"><fmt:message key="label.user.last_name"/></a>
                    <fileTags:showSortIcon sortingParam="lastName"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${param.customerListUrl}" page="${currentPage}"
                    sortingParam="email"/>"><fmt:message key="label.user.email"/></a>
                    <fileTags:showSortIcon sortingParam="email"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${param.customerListUrl}" page="${currentPage}"
                    sortingParam="phoneNumber"/>"><fmt:message key="label.user.phone_number"/></a>
                    <fileTags:showSortIcon sortingParam="phoneNumber"/>
                </th>
                <th><fmt:message key="label.actions"/></th>
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
                                   href="${param.customerViewUrl}?customerId=${customer.id}"><fmt:message key="label.details"/></a>
                            </div>
                        </div>
                    </td>
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
</body>
</html>