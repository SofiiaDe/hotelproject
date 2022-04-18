<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>


<html>

<head>
    <title>Home page</title>
    <link rel="stylesheet" href="home.css">

    <c:set var="title" value="Home" scope="page"/>
    <jsp:include page="/WEB-INF/components/head.jsp"/>

    <style>
        #infoSection{
            background-color: floralwhite;
            border-radius: 30px 0 0 15px;
            color: #404040;
            padding: 30px;
        }
        #footer{
            position: absolute;
            bottom: 0;
            width: 100%;
        }
        #title{
            color: #404040;
        }
        .navLink{
            color: white !important;
        }
        body{
            background-color: floralwhite;
        }
        #web{
            margin-top: 55px;
        }
    </style>
</head>

<body class="text-center">

<nav class="navbar background">
    <ul class="nav-list">
        <div class="logo">
            <img src="pictures/logo-hotel.jpg" alt="logo">
        </div>
<%--        <li><a href="#rooms" class="navLink"><fmt:message key="home.menu.button.rooms"/></a></li>--%>
        <li><a href="/Hotel/controller?command=loginPage" class="navLink"><fmt:message key="home.menu.button.login"/></a></li>
        <li><a href="/Hotel/controller?command=registerPage" class="navLink"><fmt:message key="home.menu.button.register"/></a></li>
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

    <div class="rightNav">
        <input type="text" name="search" id="search">
        <button class="btn btn-sm"><fmt:message key="home.menu.button.search"/></button>
    </div>


</nav>

<section class="firstsection">
    <div class="box-main">
        <div class="firstHalf" id="title">
            <h1 class="text-big" id="web">
                Hotel
            </h1>

            <p class="text-small">
                <fmt:message key="home.hotel.short_description"/>
            </p>


        </div>
    </div>
</section>

<section class="section" style="justify-content: end">
    <div class="paras" id="infoSection">
        <h1 class="sectionTag text-big"><fmt:message key="home.hotel.location"/></h1>

        <p class="sectionSubTag text-small">
            <fmt:message key="home.hotel.location.address"/>
            <br>
            <fmt:message key="home.hotel.location.city"/>
            <br>
            <fmt:message key="home.hotel.location.phone"/>
            <br>
            <fmt:message key="home.hotel.location.email"/>
        </p>

    </div>

    <div class="thumbnail">
        <img src="pictures/exterior-homepage.jpg" alt="hotel building">
    </div>
</section>

<footer class="background" id="footer">
    <p class="text-footer">
        <fmt:message key="home.copyright"/>
    </p>


</footer>

<jsp:include page="/WEB-INF/components/scripts.jsp"/>


</body>
<style>
    body {
        background-image: url('pictures/RoomHomepage.jpg');
        background-repeat: no-repeat;
    }
</style>
</html>
