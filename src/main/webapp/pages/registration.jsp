<%--@elvariable id="loggedUser" type="com.epam.rd.java.basic.repairagency.entity.User"--%>
<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%--@elvariable id="successMessage" type="java.lang.String"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="lib" tagdir="/WEB-INF/tags" %>
<html>
<jsp:include page="/pages/common/layouts/_head.jsp">
    <jsp:param name="title" value="Registration"/>
</jsp:include>
<body>
<lib:navbarForRole role="${loggedUser.role}" active="${initParam['registrationUrl']}"/>
<div class="container col-md-5">
    <div class="card mb-3">
        <div class="card-body">
            <h1>Registration</h1>
            <c:if test="${errorMessage != null}">
                <div class="alert alert-danger alert-dismissible fade show">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                        ${errorMessage}
                </div>
            </c:if>
            <p>Please fill out the following fields to signup:</p>
            <form action="${initParam['registrationUrl']}" method="post">
                <fieldset class="form-group">
                    <label for="registration-firstName">First name</label>
                    <input id="registration-firstName" name="firstName" class="form-control"
                           type="text" required="required">
                </fieldset>
                <fieldset class="form-group">
                    <label for="registration-lastName">Last name</label>
                    <input id="registration-lastName" name="lastName" class="form-control"
                           type="text" required="required">
                </fieldset>
                <fieldset class="form-group">
                    <label for="registration-email">Email</label>
                    <input id="registration-email" name="email" class="form-control"
                           type="email" required="required">
                </fieldset>
                <fieldset class="form-group">
                    <label for="registration-phoneNumber">Phone number</label>
                    <input id="registration-phoneNumber" name="phoneNumber" class="form-control"
                           type="text">
                </fieldset>
                <fieldset class="form-group">
                    <label for="registration-password">Password</label>
                    <input id="registration-password" name="password" class="form-control"
                           type="password" required="required">
                </fieldset>
                <button type="submit" class="btn btn-success">Registration</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>