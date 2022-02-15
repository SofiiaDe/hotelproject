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
</head>

<body class="text-center">

<nav class="navbar background">
    <ul class="nav-list">
        <div class="logo">
            <img src="pictures/logo-hotel.jpg" alt="logo">
        </div>
        <li><a href="#rooms"><fmt:message key="home.menu.button.rooms"/></a></li>
        <li><a href="/Hotel/controller?command=loginPage"><fmt:message key="home.menu.button.login"/></a></li>
        <li><a href="/Hotel/controller?command=registerPage"><fmt:message key="home.menu.button.register"/></a></li>
    </ul>

    <div class="rightNav">
        <input type="text" name="search" id="search">
        <button class="btn btn-sm"><fmt:message key="home.menu.button.search"/></button>
    </div>
</nav>

<section class="firstsection">
    <div class="box-main">
        <div class="firstHalf">
            <h1 class="text-big" id="web">
                Hotel
            </h1>

            <p class="text-small">
                <fmt:message key="home.hotel.short_description"/>
            </p>


        </div>
    </div>
</section>

<section class="section">
    <div class="paras">
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

<footer class="background">
    <p class="text-footer">
        Copyright ©-All rights are reserved
    </p>


</footer>

<jsp:include page="/WEB-INF/components/scripts.jsp"/>


</body>
<style>
    body {
        background-image: url('pictures/RoomHomepage.jpg');
    }
</style>
</html>
