<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/page.jspf"%>
<%@ include file="/WEB-INF/jspf/taglib.jspf"%>

<html>
<head>
    <style type="text/css">
        form {
            width: auto;
            margin: 0 auto;
        }
    </style>
</head>
<body>
<%--<%@ include file="/WEB-INF/jspf/header.jspf" %>--%>
<div align="center">
    <table border="1" style="text-align: center;">
        <tr>
            <td>id</td>
            <td>firstName</td>
            <td>lastName</td>
            <td>email</td>
            <td>password</td>
            <td>country</td>
            <td>role</td>
        </tr>
        <c:forEach var="freeRoom" items="${sessionScope.usersList}">
            <tr>
                <td>${freeRoom.id}</td>
                <td>${freeRoom.firstName}</td>
                <td>${freeRoom.lastName}</td>
                <td>${freeRoom.email}</td>
                <td>${freeRoom.password}</td>
                <td>${freeRoom.country}</td>
                <td>${freeRoom.role}</td>



            </tr>

        </c:forEach>
    </table>
    <br>
</div>

</body>
</html>