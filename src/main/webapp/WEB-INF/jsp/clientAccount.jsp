<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<!doctype html>
<html>
<head>
    <title>Client Account</title>
</head>
<body>
<h1>Welcome ${sessionScope.authorisedUser.firstName}! This is your Client Account.</h1>
<a href="/Hotel/controller?command=applicationPage" class="button">Submit application</a>
<br>
<a href="/Hotel/controller?command=freeRoomsPage" class="button">Choose a room</a>
</body>
</html>
