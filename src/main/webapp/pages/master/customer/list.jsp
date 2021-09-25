<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/pages/common/customer/list.jsp">
    <jsp:param name="customerListUrl" value="${initParam['masterCustomerListUrl']}"/>
    <jsp:param name="customerViewUrl" value="${initParam['masterCustomerViewUrl']}"/>
</jsp:include>
