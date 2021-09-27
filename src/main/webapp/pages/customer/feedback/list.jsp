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
<body>
<fileTags:navbarForRole role="${loggedUser.role}" active="${initParam['customerFeedbackListUrl']}"/>
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
                    <a href="<fileTags:hrefWithParameters href="${initParam['customerFeedbackListUrl']}"
                       page="${currentPage}" sortingParam="text"/>"><fmt:message key="label.feedback.text"/></a>
                    <fileTags:showSortIcon sortingParam="text"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${initParam['customerFeedbackListUrl']}"
                       page="${currentPage}" sortingParam="createdAt"/>"><fmt:message key="label.created_at"/></a>
                    <fileTags:showSortIcon sortingParam="createdAt"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${initParam['customerFeedbackListUrl']}"
                       page="${currentPage}" sortingParam="masterFullName"/>"><fmt:message key="label.master"/></a>
                    <fileTags:showSortIcon sortingParam="masterFullName"/>
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="feedback" items="${entities}">
                <tr>
                    <td class="form-group">
                        <form action="${initParam['customerFeedbackEditUrl']}" method="post">
                            <input type="hidden" name="masterId" value="${feedback.masterId}"/>
                            <input type="hidden" name="feedbackId" value="${feedback.id}"/>
                            <textarea name="text" rows="4" class="form-control mb-2" required
                            ><c:out value="${feedback.text}"/></textarea>
                            <button type="submit" class="form-control btn btn-success"><fmt:message key="label.save"/></button>
                        </form>
                    </td>
                    <td>${libTags:formatLocalDateTime(feedback.createdAt)}</td>
                    <td>
                        <a href="${initParam['customerMasterViewUrl']}?masterId=${feedback.masterId}">${feedback.master.fullName}</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <hr>
        <jsp:include page="/pages/common/layouts/_table-footer.jsp">
            <jsp:param name="href" value="${initParam['customerFeedbackListUrl']}"/>
        </jsp:include>
    </div>
</div>
</body>
</html>