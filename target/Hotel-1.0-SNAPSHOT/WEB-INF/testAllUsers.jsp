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
            <td>name</td>
            <td>email</td>
            <td>country</td>
        </tr>
        <c:forEach var="bean" items="${sessionScope.usersList}">
            <tr>
                <td>${bean.id}</td>
                <td>${bean.name}</td>
                <td>${bean.email}</td>
                <td>${bean.country}</td>




            </tr>

        </c:forEach>
    </table>
    <br>
    <form action="controller" method="post">
        <div align="center">
            <input type="hidden" name="command" value="test"><br>  <input>
        </div>
    </form>
</div>

</body>
</html>