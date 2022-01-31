<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Insert title here</title>
</head>
<body>
<div align="center">
    <h1>User Login Form</h1>
    <form action="<%=request.getContextPath()%>/login" method="post">
        <table style="with: 100%">
            <tr>
                <td>Email</td>
                <td><input type="text" name="email" /></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="password" /></td>
            </tr>

        </table>
        <input type="submit" value="Submit" />
    </form>

</div>
</body>
</html>