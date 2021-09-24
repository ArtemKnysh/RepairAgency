<%--@elvariable id="loggedUser" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>
<%--@elvariable id="feedbacks" type="java.util.List"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fileTags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="libTags" uri="http://com.epam.rd.java.basic.repairagency/lib" %>
<html>
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="List Of Feedbacks"/>
</jsp:include>
<body>
<fileTags:navbarForRole role="${loggedUser.role}" active="${initParam['customerFeedbackListUrl']}"/>
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
                <th>Text</th>
                <th>Created At</th>
                <th>Master</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="feedback" items="${feedbacks}">
                <tr>
                    <td class="form-group">
                        <form action="${initParam['customerFeedbackEditUrl']}" method="post">
                            <input type="hidden" name="masterId" value="${feedback.masterId}"/>
                            <input type="hidden" name="feedbackId" value="${feedback.id}"/>
                            <textarea name="text" rows="4" class="form-control mb-2" required
                            ><c:out value="${feedback.text}"/></textarea>
                            <button type="submit" class="form-control btn btn-success">Save</button>
                        </form>
                    </td>
                    <td><c:out value="${feedback.createdAt}"/></td>
                    <td>
                        <a href="${initParam['customerMasterViewUrl']}?masterId=${feedback.masterId}">${feedback.master.fullName}</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>