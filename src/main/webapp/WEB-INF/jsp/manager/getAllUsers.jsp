<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/page.jspf"%>
<%@ include file="/WEB-INF/jspf/taglib.jspf"%>

<html>
<head>
    <style>
        form {
            width: auto;
            margin: 0 auto;
        }
    </style>
</head>

<c:set var="title" value="All users list" scope="page"/>
<jsp:include page="/WEB-INF/components/head.jsp"/>
<body>
<jsp:include page="/WEB-INF/jsp/managerMenu.jsp"/>
<div align="center">
    <table border="1" style="text-align: center;">
        <tr>
            <td>id</td>
            <td>firstName</td>
            <td>lastName</td>
            <td>email</td>
            <td>country</td>
            <td>role</td>
        </tr>
        <c:forEach var="user" items="${sessionScope.usersList}">
            <tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.email}</td>
                <td>${user.country}</td>
                <td>${user.role}</td>

            </tr>

        </c:forEach>
    </table>
    <br>
</div>
<jsp:include page="/WEB-INF/components/scripts.jsp"/>


</body>
</html>