<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="localeKeys"/>
<c:set var="title"><fmt:message key="label.master.edit"/></c:set>
<jsp:include page="/pages/common/user/edit.jsp">
    <jsp:param name="title" value="${title}"/>
    <jsp:param name="userEditUrl" value="${initParam['masterUserEditUrl']}"/>
</jsp:include>
