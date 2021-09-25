<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/pages/common/user/view.jsp">
    <jsp:param name="title" value="Manager Details"/>
    <jsp:param name="userEditUrl" value="${initParam['managerUserEditUrl']}"/>
    <jsp:param name="userViewUrl" value="${initParam['managerUserViewUrl']}"/>
    <jsp:param name="userTopUpAccountUrl" value="${initParam['managerTopUpAccountUrl']}"/>
</jsp:include>
