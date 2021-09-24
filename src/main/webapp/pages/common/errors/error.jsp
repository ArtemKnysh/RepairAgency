<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="lib" tagdir="/WEB-INF/tags" %>
<html>
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="Error"/>
</jsp:include>
<body>
<lib:navbarForRole role="${loggedUser.role}"/>
<div style="text-align: center;">
    <h1 style="text-align: center;">Error</h1>
    <c:set var="errorPageExceptionMessage" value="${pageContext.exception.getMessage()}"/>
    <c:if test="${errorPageExceptionMessage != null}">
        <h2>${errorPageExceptionMessage}<br></h2>
    </c:if>
    <c:set var="parametrExceptionMessage" value="${errorMessage}"/>
    <c:if test="${parametrExceptionMessage != null}">
        <h2>${parametrExceptionMessage}<br></h2>
    </c:if>
</div>
</body>
</html>