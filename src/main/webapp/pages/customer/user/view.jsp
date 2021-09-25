<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/pages/common/user/view.jsp">
    <jsp:param name="title" value="Customer Details"/>
    <jsp:param name="userEditUrl" value="${initParam['customerUserEditUrl']}"/>
    <jsp:param name="userViewUrl" value="${initParam['customerUserViewUrl']}"/>
    <jsp:param name="userTopUpAccountUrl" value="${initParam['customerTopUpAccountUrl']}"/>
</jsp:include>
