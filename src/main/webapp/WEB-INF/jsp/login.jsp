<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!doctype html>
<html>
<c:set var="title" value="Login page" scope="page"/>
<jsp:include page="/WEB-INF/components/head.jsp"/>
<head>

    <link rel="stylesheet" href="home.css">

    <link href="https://getbootstrap.com/docs/4.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="https://getbootstrap.com/docs/4.0/examples/sign-in/signin.css" rel="stylesheet">

    <link rel="stylesheet" href="home.css">


    <style>
        #footer {
            position: absolute;
            bottom: 0;
            width: 100%;
        }

        .navLink {
            color: white !important;
        }

        body {
            background-color: floralwhite;
        }

    </style>
</head>

<body class="text-center">

<nav class="navbar background">
    <ul class="nav-list">
        <div class="logo">
            <img src="pictures/logo-hotel.jpg" alt="logo">
        </div>
        <li><a href="#rooms" class="navLink"><fmt:message key="home.menu.button.rooms"/></a></li>
        <li><a href="/Hotel/controller?command=homePage" class="navLink"><fmt:message key="menu.button.home"/></a>
        </li>
        <li><a href="/Hotel/controller?command=registerPage" class="navLink"><fmt:message
                key="home.menu.button.register"/></a></li>
    </ul>

    <div class="dropdown">
        <button class="btn btn-outline-secondary btn-sm" type="button" id="dropdownMenuButton"
                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <i class="material-icons">
                language
            </i>
        </button>
        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <form class="form-inline" method="post" action="controller?command=i18n">
                <button type="submit" name="en" onclick="changeFieldElement('langField', 'en')"
                        class="dropdown-item"><fmt:message key="main.menu.button.english"/></button>
                <button type="submit" name="uk" onclick="changeFieldElement('langField', 'uk')"
                        class="dropdown-item"><fmt:message key="main.menu.button.ukrainian"/></button>
                <input name="langField" type="hidden" value="en">
            </form>
        </div>
    </div>

</nav>


<form class="form-signin" method="post" action="controller?command=login">
    <img class="mb-4" src="pictures/logo-hotel.jpg" alt="logo" width="80" height="80">
    <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="login.title.signin"/></h1>
    <label for="login" class="sr-only"><fmt:message key="login.label.email"/></label>
    <input type="text" id="login" name="email" class="form-control" placeholder=
    <fmt:message key="login.placeholder.email"/> required autofocus>
    <label for="password" class="sr-only"><fmt:message key="login.label.password"/></label>
    <input type="password" id="password" name="password" class="form-control"
           placeholder="<fmt:message key="login.placeholder.password"/>" required>
    <div class="checkbox mb-3">
        <label>
            <input type="checkbox" value="remember-me"> <fmt:message key="login.checkbox.remember"/>
        </label>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="login.button.login"/></button>
    <p class="mt-5 mb-3 text-muted">&copy;My project 2022</p>
</form>

<div id="succRegSnackbar"><fmt:message key="login.snackbar.success.login"/></div>

<footer class="background" id="footer">
    <p class="text-footer">
    </p>


</footer>

<jsp:include page="/WEB-INF/components/scripts.jsp"/>

</body>
</html>