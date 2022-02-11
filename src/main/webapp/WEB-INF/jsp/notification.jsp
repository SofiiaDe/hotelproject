<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<html>

<c:set var="title" value="Notificaton" scope="page" />
<jsp:include page="/WEB-INF/components/head.jsp"/>

<body>

<table id="main-container">


    <tr >
        <td class="content">
            <%-- CONTENT --%>

            <%-- this way we obtain an information about an exception (if it has been occurred) --%>
<%--            <c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"/>--%>
<%--            <c:set var="message" value="${requestScope['javax.servlet.error.message']}"/>--%>
<%--            <c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>--%>

<%--            <c:if test="${not empty code}">--%>
<%--                <h3>Error code: ${code}</h3>--%>
<%--            </c:if>--%>

<%--            <c:if test="${not empty message}">--%>
<%--                <h3>${message}</h3>--%>
<%--            </c:if>--%>

            <%-- if we get this page using forward --%>
            <c:if test="${not empty requestScope.notification}">
                <h3>${requestScope.notification}</h3>
            </c:if>

            <%-- CONTENT --%>
        </td>
    </tr>

</table>
</body>
</html>

