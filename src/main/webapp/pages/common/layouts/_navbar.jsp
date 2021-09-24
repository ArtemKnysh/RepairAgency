<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lib" uri="http://com.epam.rd.java.basic.repairagency/lib" %>
<nav class="navbar-expand-lg navbar-light bg-light shadow-sm navbar">
    <div class="container-fluid">
        <a class="navbar-brand" href="${initParam['loginUrl']}"> Repair Agency </a>
        <ul class="navbar-nav ml-auto nav">
            <lib:navItem href="${initParam['registrationUrl']}" text="Registration" active="${param.active}"/>
            <lib:navItem href="${initParam['loginUrl']}" text="Login" active="${param.active}"/>
        </ul>
    </div>
</nav>
<br>