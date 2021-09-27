<%--@elvariable id="loggedUser" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="lib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="localeKeys"/>
<html lang="${sessionScope.lang}">
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="${param.title}"/>
</jsp:include>
<body>
<lib:navbarForRole role="${loggedUser.role}" active="${param.userEditUrl}"/>
<div class="container col-md-6">
    <div class="card mb-3">
        <div class="card-body">
            <h1>${param.title}</h1>
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
            <p><fmt:message key="label.edit.input_tip"/></p>
            <form action="${param.userEditUrl}" method="post">
                <input type="hidden" name="id" value="<c:out value='${loggedUser.id}' />"/>
                <fieldset class="form-group">
                    <label for="edit-user-firstName"><fmt:message key="label.user.first_name"/></label>
                    <input id="edit-user-firstName" type="text" value="<c:out value='${loggedUser.firstName}' />"
                           class="form-control" name="firstName" required>
                </fieldset>
                <fieldset class="form-group">
                    <label for="edit-user-lastName"><fmt:message key="label.user.last_name"/></label>
                    <input id="edit-user-lastName" type="text" value="<c:out value='${loggedUser.lastName}' />"
                           class="form-control" name="lastName" required>
                </fieldset>
                <fieldset class="form-group">
                    <label for="edit-user-email"><fmt:message key="label.user.email"/></label>
                    <input id="edit-user-email" type="email" value="<c:out value='${loggedUser.email}' />"
                           class="form-control" name="email" required>
                </fieldset>
                <fieldset class="form-group">
                    <label for="edit-user-phoneNumber"><fmt:message key="label.user.phone_number"/></label>
                    <input id="edit-user-phoneNumber" type="text"
                           value="<c:out value='${loggedUser.phoneNumber}' />"
                           class="form-control" name="phoneNumber">
                </fieldset>
                <fieldset class="form-group">
                    <label for="edit-user-password"><fmt:message key="label.user.password"/></label>
                    <input id="edit-user-password" type="password"
                           value="<c:out value='${loggedUser.password}' />"
                           class="form-control" name="password" required>
                </fieldset>
                <button type="submit" class="btn btn-success"><fmt:message key="label.save"/></button>
            </form>
        </div>
    </div>
</div>
</body>
</html>