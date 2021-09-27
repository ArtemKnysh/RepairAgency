<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lib" uri="http://com.epam.rd.java.basic.repairagency/lib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="localeKeys"/>
<nav class="navbar-expand-lg navbar-light bg-light shadow-sm navbar">
    <div class="container-fluid">
        <a class="navbar-brand" href="${initParam['loginUrl']}"> <fmt:message key="label.app_name"/> </a>
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
            <c:set var="registrationTitle"><fmt:message key="label.registration"/></c:set>
            <lib:navItem href="${initParam['registrationUrl']}" text="${registrationTitle}" active="${param.active}"/>
            <c:set var="loginTitle"><fmt:message key="label.login"/></c:set>
            <lib:navItem href="${initParam['loginUrl']}" text="${loginTitle}" active="${param.active}"/>
        </ul>
    </div>
</nav>
<br>