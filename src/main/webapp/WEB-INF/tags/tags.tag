<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="status" required="true" type="java.lang.String" %>

<%--<c:set var="invoice.status" value=""/>--%>


<%--<c:choose>--%>
<%--    <c:when test="${invoice.status == 'new'}">--%>
<%--        <span class="badge badge-danger text-uppercase"><fmt:message key="status.new"/></span>&ndash;%&gt;--%>
<%--    </c:when>--%>
<%--    <c:when test="${status.status == 'paid'}">--%>
<%--        <span class="badge badge-success text-uppercase"><fmt:message key="status.paid"/></span>&ndash;%&gt;--%>
<%--    </c:when>--%>
<%--    <c:otherwise>--%>
<%--        <fmt:message key="status.cancelled"/>--%>
<%--    </c:otherwise>--%>
<%--</c:choose>--%>

<%--<c:set var="status" value=""/>--%>



<c:choose>
    <c:when test="${status == 'new'}">
        <span class="badge badge-danger text-uppercase"><fmt:message key="status.new"/></span>
    </c:when>
    <c:when test="${status == 'paid'}">
        <span class="badge badge-success text-uppercase"><fmt:message key="status.paid"/></span>
    </c:when>
    <c:otherwise>
        <span class="badge badge-secondary text-uppercase"><fmt:message key="status.cancelled"/>
    </c:otherwise>
</c:choose>






<%--<%@ attribute name="status" required="true" type="java.lang.Boolean" %>--%>

<%--<c:choose>--%>
<%--    <c:when test="${status}">--%>
<%--        <span class="badge badge-danger text-uppercase"><fmt:message key="status.new"/></span>--%>
<%--    </c:when>--%>
<%--    <c:otherwise>--%>
<%--        <span class="badge badge-success text-uppercase"><fmt:message key="status.paid"/></span>--%>
<%--    </c:otherwise>--%>
<%--</c:choose>--%>

<%--<%@ attribute name="value" required="true" type="java.lang.Boolean" %>--%>
<%--<%@ attribute name="status" required="true" type="java.lang.String" %>--%>


<%--<%--%>
<%--    request.setAttribute("status", request.getParameter("status"));--%>
<%--%>--%>

<%--    <c:choose>--%>
<%--        <c:when test="${param.status == 'new'}">--%>
<%--            <span class="badge badge-danger text-uppercase"><fmt:message key="status.new"/></span>&ndash;%&gt;--%>
<%--        </c:when>--%>
<%--        <c:when test="${param.status == 'paid'}">--%>
<%--            <span class="badge badge-success text-uppercase"><fmt:message key="status.paid"/></span>&ndash;%&gt;--%>
<%--        </c:when>--%>
<%--        <c:otherwise>--%>
<%--            <fmt:message key="status.cancelled"/>--%>
<%--            </c:otherwise>--%>
<%--    </c:choose>--%>

    <%--<c:choose>--%>
<%--    <c:when test="${value}">--%>
<%--        <span class="material-icons">add</span>--%>
<%--    </c:when>--%>
<%--    <c:otherwise>--%>
<%--        <span class="material-icons">remove</span>--%>
<%--    </c:otherwise>--%>
<%--</c:choose>--%>


<%--<%@ attribute name="new" required="true" type="java.lang.Boolean" %>--%>

<%--<c:choose>--%>
<%--    <c:when test="${new}">--%>
<%--        <span class="badge badge-danger text-uppercase"><fmt:message key="status.new"/></span>--%>
<%--    </c:when>--%>
<%--    <c:otherwise>--%>
<%--        <span class="badge badge-success text-uppercase"><fmt:message key="status.paid"/></span>--%>
<%--    </c:otherwise>--%>
<%--</c:choose>--%>