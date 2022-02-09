<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>


<html>

<head>
    <title>Home page</title>
</head>
<c:set var="title" value="Home" scope="page"/>
<jsp:include page="/WEB-INF/templates/head.jsp"/>
<body>
<nav class="navbar background">
    <ul class="nav-list">
        <div class="logo">
            <img src="logo.png">
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
                Hotel.
            </p>


        </div>
    </div>
</section>

<section class="secondsection">
    <div class="box-main">
        <div class="secondHalf">
            <h1 class="text-big" id="program">
                Description
            </h1>
            <p class="text-small">
                Some text.
            </p>


        </div>
    </div>
</section>

<section class="section">
    <div class="paras">
        <h1 class="sectionTag text-big">Java</h1>

        <p class="sectionSubTag text-small">
            More text.
        </p>


    </div>

    <div class="thumbnail">
        <img src="img.png" alt="laptop image">
    </div>
</section>

<footer class="background">
    <p class="text-footer">
        Copyright ©-All rights are reserved
    </p>


</footer>

<jsp:include page="/WEB-INF/templates/scripts.jsp"/>

</body>

</html>
