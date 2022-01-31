<%--
  Created by IntelliJ IDEA.
  User: Sofiia
  Date: 1/27/2022
  Time: 10:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Registration Form</title>
</head>
<body>
<h1>Guru Register Form</h1>
<form action="controller" method="post">
    <table style="with: 50%">
        <tr>
            <td>First Name</td>
            <td><input type="text" name="firstName" /></td>
        </tr>
        <tr>
            <td>Last Name</td>
            <td><input type="text" name="lastName" /></td>
        </tr>
        <tr>
            <td>Email</td>
            <td><input type="text" name="email" /></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="password" /></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="confirmPassword" /></td>
        </tr>
        <tr>
            <td>Country</td>
            <td><input type="text" name="country" /></td>
        </tr>
        <tr>
            <td>Role</td>
            <td><input type="text" name="role" /></td>
        </tr></table>
    <input type="submit" value="Submit" /></form>

</form>
<form action ="controller" method="post">
    <div align="center">
        <input type ="hidden" name="command" value="anycommand">
        <input type ="submit" value="Login page">	</div>
</form></td>

<%--<div align="center">--%>
<%--    <input type ="hidden" name="command" value="registration">--%>
<%--    <input type ="submit" value="register"></div>--%>
</body>
</html>
