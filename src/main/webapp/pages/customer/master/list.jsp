<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/pages/common/layouts/master/list.jsp">
    <jsp:param name="masterListUrl" value="${initParam['customerMasterListUrl']}"/>
    <jsp:param name="masterViewUrl" value="${initParam['customerMasterViewUrl']}"/>
</jsp:include>