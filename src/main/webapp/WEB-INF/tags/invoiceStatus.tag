<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="invoiceStatus" required="true" type="java.lang.String" %>

<c:choose>
    <c:when test="${invoiceStatus == 'new'}">
        <span class="badge badge-danger text-uppercase"><fmt:message key="status.new"/></span>
    </c:when>
    <c:when test="${invoiceStatus == 'paid'}">
        <span class="badge badge-success text-uppercase"><fmt:message key="status.paid"/></span>
    </c:when>
    <c:otherwise>
        <span class="badge badge-secondary text-uppercase"><fmt:message key="status.cancelled"/>
    </c:otherwise>
</c:choose>



