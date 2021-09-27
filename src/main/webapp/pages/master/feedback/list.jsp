<%--@elvariable id="loggedUser" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>
<%--@elvariable id="currentPage" type="java.lang.Integer"--%>
<%--@elvariable id="entities" type="java.util.List"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fileTags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="libTags" uri="http://com.epam.rd.java.basic.repairagency/lib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="localeKeys"/>
<html lang="${sessionScope.lang}">
<c:set var="title">
    <fmt:message key="label.feedback.list"/>
</c:set>
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="${title}"/>
</jsp:include>
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="${title}"/>
</jsp:include>
<body>
<fileTags:navbarForRole role="${loggedUser.role}" active="${initParam['masterFeedbackListUrl']}"/>
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
                    <a href="<fileTags:hrefWithParameters href="${initParam['masterFeedbackListUrl']}"
                       page="${currentPage}" sortingParam="text"/>"><fmt:message key="label.feedback.text"/></a>
                    <fileTags:showSortIcon sortingParam="text"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${initParam['masterFeedbackListUrl']}"
                       page="${currentPage}" sortingParam="createdAt"/>"><fmt:message key="label.created_at"/></a>
                    <fileTags:showSortIcon sortingParam="createdAt"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${initParam['masterFeedbackListUrl']}"
                       page="${currentPage}" sortingParam="customerFullName"/>"><fmt:message key="label.customer"/></a>
                    <fileTags:showSortIcon sortingParam="customerFullName"/>
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="feedback" items="${entities}">
                <tr>
                    <td><c:out value="${feedback.text}"/></td>
                    <td>${libTags:formatLocalDateTime(feedback.createdAt)}</td>
                    <td>
                        <a href="${initParam['masterCustomerViewUrl']}?customerId=${feedback.customerId}">${feedback.customer.fullName}</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <hr>
        <jsp:include page="/pages/common/layouts/_table-footer.jsp">
            <jsp:param name="href" value="${initParam['masterFeedbackListUrl']}"/>
        </jsp:include>
    </div>
</div>
</body>
</html>