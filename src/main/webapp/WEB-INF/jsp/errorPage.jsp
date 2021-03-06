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

</style>
<body class="text-center" id="errorForm">

            <h2 class="error">
                <fmt:message key="error.occurred"/>:
            </h2>


            <c:if test="${not empty requestScope.errorMessage}">
                <h3>${requestScope.errorMessage}</h3>
            </c:if>

<jsp:include page="/WEB-INF/components/scripts.jsp"/>

</body>
</html>
