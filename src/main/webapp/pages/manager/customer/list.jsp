<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/pages/common/layouts/customer/list.jsp">
    <jsp:param name="customerListUrl" value="${initParam['managerCustomerListUrl']}"/>
    <jsp:param name="customerViewUrl" value="${initParam['managerCustomerViewUrl']}"/>
</jsp:include>
