<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lib" uri="http://com.epam.rd.java.basic.repairagency/lib" %>
<nav class="navbar-expand-lg navbar-light bg-light shadow-sm navbar">
    <div class="container-fluid">
        <a class="navbar-brand" href="${initParam['customerUserListUrl']}"> Repair Agency </a>
        <ul class="navbar-nav mr-auto nav">
            <lib:navItem href="${initParam['customerRepairRequestListUrl']}"
                         text="Repair Requests"
                         active="${param.active}"/>
            <lib:navItem href="${initParam['customerFeedbackListUrl']}" text="Feedbacks"
                         active="${param.active}"/>
            <lib:navItem href="${initParam['customerCustomerListUrl']}" text="Customers"
                         active="${param.active}"/>
            <lib:navItem href="${initParam['customerMasterListUrl']}" text="Masters"
                         active="${param.active}"/>
        </ul>
        <ul class="navbar-nav ml-auto nav">
            <lib:navItem href="${initParam['customerUserViewUrl']}" text="Customer Details"
                         active="${param.active}"/>
            <li class="nav-item">
                <form action="${initParam['customerLogoutUrl']}" method="post">
                    <input type="submit" class="nav-link border-0 bg-transparent" value="Logout"/>
                </form>
            </li>
        </ul>
    </div>
</nav>
<br>