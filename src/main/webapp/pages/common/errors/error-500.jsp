<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="lib" tagdir="/WEB-INF/tags" %>
<html>
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="500 Error - Internal Server Error"/>
</jsp:include>
<body>
<lib:navbarForRole role="${loggedUser.role}"/>
<div style="text-align: center;">
    <h1 style="text-align: center;">500 Error - Internal Server Error</h1>
    <h2>Sorry, the server encountered an error<br></h2>
</div>
</body>
</html>