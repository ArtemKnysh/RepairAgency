<%--@elvariable id="loggedUser" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="localeKeys"/>
<html lang="${sessionScope.lang}">
<c:set var="title">
    <fmt:message key="label.login"/>
</c:set>
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="${title}"/>
</jsp:include>
<body>
<lib:navbarForRole role="${loggedUser.role}" active="${initParam['loginUrl']}"/>
<div class="container col-md-5">
    <div class="card mb-3">
        <div class="card-body">
            <h1>${title}</h1>
            <c:if test="${errorMessage != null}">
                <div class="alert alert-danger alert-dismissible fade show">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${errorMessage}
                </div>
            </c:if>
            <p><fmt:message key="label.login.input_tip"/></p>
            <form action="${initParam['loginUrl']}" method="post">
                <fieldset class="form-group">
                    <label for="login-email"><fmt:message key="label.user.email"/></label>
                    <input id="login-email" name="email" class="form-control"
                           type="email" required="required">
                </fieldset>
                <fieldset class="form-group">
                    <label for="login-password"><fmt:message key="label.user.password"/></label>
                    <input id="login-password" name="password" class="form-control"
                           type="password" required="required">
                </fieldset>
                <button type="submit" class="btn btn-success"><fmt:message key="label.login.submit_button"/></button>
            </form>
        </div>
    </div>
</div>
</body>
</html>