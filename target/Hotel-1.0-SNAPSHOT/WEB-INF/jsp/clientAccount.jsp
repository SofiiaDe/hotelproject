<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Client Account</title>
</head>
<body>
    <h>Welcome! This is your Client Account.</h>

    <c:if test="${sessionScope.submitApplication}">
        <br>
        <form action="controller" method="post">
            <div align="center">
                <input type="hidden" name="command" value="clientAccount"><br>  <input
            </div>
        </form>
    </c:if>
</body>
</html>
