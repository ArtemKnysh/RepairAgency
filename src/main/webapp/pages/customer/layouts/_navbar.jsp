<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lib" uri="http://com.epam.rd.java.basic.repairagency/lib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="localeKeys"/>
<nav class="navbar-expand-lg navbar-light bg-light shadow-sm navbar">
    <div class="container-fluid">
        <a class="navbar-brand" href="${initParam['customerHomeUrl']}"><fmt:message key="label.app_name"/></a>
        <ul class="navbar-nav mr-auto nav">
            <c:set var="title"><fmt:message key="label.repair_requests"/></c:set>
            <lib:navItem href="${initParam['customerRepairRequestListUrl']}"
                         text="${title}" active="${param.active}"/>
            <c:set var="title"><fmt:message key="label.feedbacks"/></c:set>
            <lib:navItem href="${initParam['customerFeedbackListUrl']}"
                         text="${title}" active="${param.active}"/>
            <c:set var="title"><fmt:message key="label.customers"/></c:set>
            <lib:navItem href="${initParam['customerCustomerListUrl']}"
                         text="${title}" active="${param.active}"/>
            <c:set var="title"><fmt:message key="label.masters"/></c:set>
            <lib:navItem href="${initParam['customerMasterListUrl']}"
                         text="${title}" active="${param.active}"/>
        </ul>
        <ul class="navbar-nav ml-auto nav">
            <li class="nav-item">
                <c:set var="active" value="${sessionScope.lang == 'en' ? 'active' : '' }"/>
                <form action="${initParam['localeChangeUrl']}" method="post">
                    <input type="hidden" name="lang" value="en"/>
                    <input type="submit" class="nav-link border-0 bg-transparent ${active}"
                           value="<fmt:message key="label.lang.en"/>"/>
                </form>
            </li>
            <li class="nav-item">
                <c:set var="active" value="${sessionScope.lang == 'ru' ? 'active' : '' }"/>
                <form action="${initParam['localeChangeUrl']}" method="post">
                    <input type="hidden" name="lang" value="ru"/>
                    <input type="submit" class="nav-link border-0 bg-transparent ${active}"
                           value="<fmt:message key="label.lang.ru"/>"/>
                </form>
            </li>
            <c:set var="title"><fmt:message key="label.customer.details"/></c:set>
            <lib:navItem href="${initParam['customerUserViewUrl']}"
                         text="${title}" active="${param.active}"/>
            <li class="nav-item">
                <form action="${initParam['customerLogoutUrl']}" method="post">
                    <input type="submit" class="nav-link border-0 bg-transparent"
                           value="<fmt:message key="label.logout"/>"/>
                </form>
            </li>
        </ul>
    </div>
</nav>
<br>