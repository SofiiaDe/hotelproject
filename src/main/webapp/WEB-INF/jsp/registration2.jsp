<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>

<body>

<div align="center">
    <h1>Registration page</h1>
    <h3>Latin symbols and digits are allowed, cyrillic symbols are allowed for "First name" and "Last name" fields </h3>
    <table>
        <tr><td>
            <form action="controller" method="post">
                <fieldset>
                    <legend>Login</legend>
                    <input name="email" value="${requestScope.email}" /><br />
                </fieldset>
                <br />
                <fieldset>
                    <legend>Password</legend>
                    <input type="password" name="password" value="${requestScope.password}" />
                </fieldset>
                <br />
                <fieldset>
                    <legend>Confirm password</legend>
                    <input type="password" name="confirmPassword" value="${requestScope.confirmPassword}" />
                </fieldset>
                <br />
                <fieldset>
                    <legend>First name</legend>
                    <input name="firstName" value="${requestScope.firstName}" />
                </fieldset>
                <br />
                <fieldset>
                    <legend>Last name</legend>
                    <input name="lastName" value="${requestScope.lastName}" />
                </fieldset>
                <br />
                <fieldset>
                    <legend>E-mail</legend>
                    <input name="email" value="${requestScope.email}" />
                </fieldset>
                <br/>
                <fieldset>
                    <legend>Country</legend>
                    <input name="country" value="${requestScope.country}" />
                </fieldset>
                <br/>
                <div align="center">
                    <input type ="hidden" name="command" value="registration">
                    <input type ="submit" value="register"></div>
            </form>
            <form action ="controller" method="post">
<%--                <div align="center">--%>
<%--&lt;%&ndash;                    <input type ="hidden" name="command" value="anycommand">&ndash;%&gt;--%>
<%--                    <input type ="submit" value="Login page">	</div>--%>
            </form></td>
        </tr>
    </table>
</div>
</body>
</html>