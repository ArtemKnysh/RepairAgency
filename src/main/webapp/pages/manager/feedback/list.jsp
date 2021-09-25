<%--@elvariable id="loggedUser" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>
<%--@elvariable id="currentPage" type="java.lang.Integer"--%>
<%--@elvariable id="entities" type="java.util.List"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fileTags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="libTags" uri="http://com.epam.rd.java.basic.repairagency/lib" %>
<html>
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="List Of Feedbacks"/>
</jsp:include>
<body>
<fileTags:navbarForRole role="${loggedUser.role}" active="${initParam['managerFeedbackListUrl']}"/>
<div class="row">
    <div class="container">
        <h3 class="text-center">List of feedbacks</h3>
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
                    <a href="<fileTags:hrefWithParameters href="${initParam['managerFeedbackListUrl']}"
                       page="${currentPage}" sortingParam="text"/>">Text</a>
                    <fileTags:showSortIcon sortingParam="text"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${initParam['managerFeedbackListUrl']}"
                       page="${currentPage}" sortingParam="createdAt"/>">Created At</a>
                    <fileTags:showSortIcon sortingParam="createdAt"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${initParam['managerFeedbackListUrl']}"
                       page="${currentPage}" sortingParam="customerFullName"/>">Customer</a>
                    <fileTags:showSortIcon sortingParam="customerFullName"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${initParam['managerFeedbackListUrl']}"
                       page="${currentPage}" sortingParam="masterFullName"/>">Master</a>
                    <fileTags:showSortIcon sortingParam="masterFullName"/>
                </th>
                <th>
                    <a href="<fileTags:hrefWithParameters href="${initParam['managerFeedbackListUrl']}"
                       page="${currentPage}" sortingParam="isHidden"/>">Actions</a>
                    <fileTags:showSortIcon sortingParam="isHidden"/>
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="feedback" items="${entities}">
                <tr>
                    <td><c:out value="${feedback.text}"/></td>
                    <td><c:out value="${feedback.createdAt}"/></td>
                    <td>
                        <a href="${initParam['managerCustomerViewUrl']}?customerId=${feedback.customerId}">${feedback.customer.fullName}</a>
                    </td>
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
                                            <button type="submit" class="form-control btn btn-success">Show</button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="${initParam['managerFeedbackHideUrl']}" method="post">
                                            <input type="hidden" name="feedbackId" value="${feedback.id}"/>
                                            <button type="submit" class="form-control btn btn-danger">Hide</button>
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
        <hr>
        <jsp:include page="/pages/common/layouts/_table-footer.jsp">
            <jsp:param name="href" value="${initParam['managerFeedbackListUrl']}"/>
        </jsp:include>
    </div>
</div>
</body>
</html>