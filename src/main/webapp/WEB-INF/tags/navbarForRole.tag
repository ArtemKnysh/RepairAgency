<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="role" rtexprvalue="true" required="true"
              type="com.epam.rd.java.basic.repairagency.entity.UserRole" %>
<%@ attribute name="active" rtexprvalue="true" type="java.lang.String" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:choose>
    <c:when test="${role != null}">
        <jsp:include page="/pages/${f:toLowerCase(role.toString())}/layouts/_navbar.jsp">
            <jsp:param name="active" value="${active}"/>
        </jsp:include>
    </c:when>
    <c:otherwise>
        <jsp:include page="/pages/common/layouts/_navbar.jsp">
            <jsp:param name="active" value="${active}"/>
        </jsp:include>
    </c:otherwise>
</c:choose>
