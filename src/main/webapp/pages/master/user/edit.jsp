<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/pages/common/user/edit.jsp">
    <jsp:param name="title" value="Edit Master Data"/>
    <jsp:param name="userEditUrl" value="${initParam['masterUserEditUrl']}"/>
</jsp:include>
