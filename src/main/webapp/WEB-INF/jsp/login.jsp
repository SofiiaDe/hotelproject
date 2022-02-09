<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<!doctype html>
<html>
<c:set var="title" value="Login Page" scope="page"/>
<jsp:include page="/WEB-INF/templates/head.jsp"/>
<head>
    <link href="https://getbootstrap.com/docs/4.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="https://getbootstrap.com/docs/4.0/examples/sign-in/signin.css" rel="stylesheet">
</head>
<body class="text-center">
<form class="form-signin" method="post" action="controller?command=login">
    <img class="mb-4" src="https://getbootstrap.com/docs/4.0/assets/brand/bootstrap-solid.svg" alt="" width="72" height="72">
    <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="login.title.signin"/></h1>
    <label for="login" class="sr-only"><fmt:message key="login.label.email"/></label>
    <input type="text" id="login" name="email" class="form-control" placeholder=<fmt:message key="login.placeholder.email"/> required autofocus>
    <label for="password" class="sr-only"><fmt:message key="login.label.password"/></label>
    <input type="password" id="password" name="password" class="form-control" placeholder="<fmt:message key="login.placeholder.password"/>" required>
    <div class="checkbox mb-3">
        <label>
            <input type="checkbox" value="remember-me"> <fmt:message key="login.checkbox.remember"/>
        </label>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="login.button.login"/></button>
    <p class="mt-5 mb-3 text-muted">&copy;My project 2022</p>
</form>



<jsp:include page="/WEB-INF/templates/scripts.jsp"/>
</body>
</html>