<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html>
<c:set var="title" value="Registration page" scope="page"/>
<jsp:include page="/WEB-INF/components/head.jsp"/>
<head>

    <link rel="stylesheet" href="home.css">

    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <title>Registration form</title>

    <style>
        #footer{
            position: absolute;
            bottom: 0;
            width: 100%;
        }
        .navLink{
            color: white !important;
        }
        body{
            background-color: floralwhite;
        }
    </style>
</head>

<body>

<nav class="navbar background">
    <ul class="nav-list">
        <div class="logo">
            <img src="pictures/logo-hotel.jpg" alt="logo">
        </div>
        <li><a href="#rooms" class="navLink"><fmt:message key="home.menu.button.rooms"/></a></li>
        <li><a href="/Hotel/controller?command=homePage" class="navLink"><fmt:message key="menu.button.home"/></a></li>
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

<div class="container register">
    <div class="row">
        <div class="col-md-3 register-left">
            <img src="https://image.ibb.co/n7oTvU/logo_white.png" alt=""/>
            <h3><fmt:message key="register.welcome"/></h3>
            <p><fmt:message key="register.welcome.text"/></p>
            <form class="form-signin" method="post" action="controller?command=loginPage">
                <input type="submit" name="login" value="<fmt:message key="login.button.login"/>"/><br/>
                <%--                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="login.button.login"/></button><br/>--%>

            </form>
        </div>
        <div class="col-md-9 register-right">
            <ul class="nav nav-tabs nav-justified" id="myTab" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active" id="client-tab" data-toggle="tab" href="#client" role="tab"
                       aria-controls="client" aria-selected="true"><fmt:message key="register.header.client"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="manager-tab" data-toggle="tab" href="#manager" role="tab"
                       aria-controls="manager" aria-selected="false"><fmt:message key="register.header.manager"/></a>
                </li>
            </ul>
            <div class="tab-content" id="myTabContent">
                <div class="tab-pane fade show active" id="client" role="tabpanel" aria-labelledby="client-tab">
                    <h3 class="register-heading"><fmt:message key="register.header.as.client"/></h3>
                    <form class="form-signup" method="post" action="controller?command=registration">

                        <div class="row register-form">

                            <div class="col-md-6">
                                <div class="form-group">
                                    <input type="text" required="required" name="firstName" class="form-control"
                                           placeholder="<fmt:message key="register.placeholder.first_name"/> *"
                                           value=""/>
                                </div>
                                <div class="form-group">
                                    <input type="text" required="required" name="lastName" class="form-control"
                                           placeholder="<fmt:message key="register.placeholder.last_name"/> *"
                                           value=""/>
                                </div>
                                <div class="form-group">
                                    <input type="text" required="required" name="country" class="form-control"
                                           placeholder="<fmt:message key="register.placeholder.country"/> *"
                                           value=""/>
                                </div>
                                <div class="form-group">
                                    <input type="text" required="required" name="role" class="form-control" placeholder="Role *"
                                           readonly="readonly" value="<fmt:message key="register.placeholder.client"/>"/>
                                </div>


                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <input type="email" required="required" name="email" class="form-control"
                                           placeholder="<fmt:message key="register.placeholder.email"/> *"
                                           value=""/>
                                </div>
                                <div class="form-group">
                                    <input type="password" required="required" name="password" class="form-control" placeholder="<fmt:message
                                    key="register.placeholder.password"/> *"
                                           value=""/>
                                </div>
                                <div class="form-group">
                                    <input type="password" required="required" name="confirmPassword" class="form-control"
                                           placeholder="<fmt:message key="register.placeholder.confirm.password"/> *"
                                           value=""/>
                                </div>

                                <button class="btn btn-info btn-lg btn-primary btn-block" type="submit"><fmt:message
                                        key="register.button"/></button>
                            </div>
                        </div>
                    </form>

                </div>


                <div class="tab-pane fade show" id="manager" role="tabpanel" aria-labelledby="manager-tab">
                    <h3 class="register-heading"><fmt:message key="register.header.as.manager"/></h3>
                    <form class="form-signup" method="post" action="controller?command=registration">

                        <div class="row register-form">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <input type="text" required="required" name="firstName" class="form-control" placeholder="<fmt:message
                                    key="register.placeholder.first_name"/> *"
                                           value=""/>
                                </div>
                                <div class="form-group">
                                    <input type="text" required="required" name="lastName" class="form-control" placeholder="<fmt:message
                                    key="register.placeholder.last_name"/> *"
                                           value=""/>
                                </div>
                                <div class="form-group">
                                    <input type="text" required="required" name="country" class="form-control" placeholder="<fmt:message
                                    key="register.placeholder.country"/> *"
                                           value=""/>
                                </div>
                                <div class="form-group">
                                    <input type="text" name="role" class="form-control" placeholder="Role *"
                                           readonly="readonly" value="<fmt:message key="register.placeholder.manager"/>"/>
                                </div>

                            </div>
                            <div class="col-md-6">

                                <div class="form-group">
                                    <input type="email" required="required" name="email" class="form-control" placeholder="<fmt:message key="register.placeholder.email"/> *"
                                           value=""/>
                                </div>
                                <div class="form-group">
                                    <input type="password" required="required" name="password" class="form-control" placeholder="<fmt:message key="register.placeholder.password"/> *"
                                           value=""/>
                                </div>
                                <div class="form-group">
                                    <input type="password" required="required" name="confirmPassword" class="form-control"
                                           placeholder="<fmt:message key="register.placeholder.confirm.password"/> *"
                                           value=""/>
                                </div>

                                <button class="btn btn-info btn-lg btn-block" type="submit"><fmt:message
                                        key="register.button"/></button>

                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>

<footer class="background" id="footer">
    <p class="text-footer">
        <fmt:message key="home.copyright"/>
    </p>
</footer>

<jsp:include page="/WEB-INF/components/scripts.jsp"/>

</body>

</html>
