<%@ page isErrorPage="true" %>
<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<!DOCTYPE html>
<html>

<c:set var="title" value="Error" scope="page" />
<jsp:include page="/WEB-INF/components/head.jsp"/>

<head>

    <title>Error</title>
</head>
<style>
    #errorForm{
        margin-left: 35px;
        margin-top: 35px;
    }
    form input{
        margin-right: 10px;
    }
</style>
<body>

<table id="main-container">


    <tr id="errorForm">
        <td class="content">

            <h2 class="error">
                <fmt:message key="error.occurred"/>
            </h2>

            <%-- obtain an information about an exception in case it has been occurred --%>
            <c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"/>
            <c:set var="message" value="${requestScope['javax.servlet.error.message']}"/>
            <c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>

            <c:if test="${not empty code}">
                <h3><fmt:message key="error.code"/> ${code}</h3>
            </c:if>

            <c:if test="${not empty message}">
                <h3>${message}</h3>
            </c:if>

            <%-- if we get this page using forward --%>
            <c:if test="${not empty requestScope.errorMessage}">
                <h3>${requestScope.errorMessage}</h3>
            </c:if>

        </td>
    </tr>

</table>
<jsp:include page="/WEB-INF/components/scripts.jsp"/>

</body>
</html>
