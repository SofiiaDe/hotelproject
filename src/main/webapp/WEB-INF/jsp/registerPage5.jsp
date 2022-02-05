<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<!doctype html>
<html>
<c:set var="title" value="Registration page" scope="page"/>
<%--<c:set var="title" value="Registration page" scope="session"/>--%>
<jsp:include page="/WEB-INF/templates/head.jsp"/>
<head>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <title>Registration form</title>
</head>

<body>

<div class="container register">
    <div class="row">
        <div class="col-md-3 register-left">
            <img src="https://image.ibb.co/n7oTvU/logo_white.png" alt=""/>
            <h3>Welcome</h3>
            <p>Premium comfort with attention to details</p>
            <form class="form-signin" method="post" action="controller?command=loginPage">
                <input type="submit" name="login" value="Login"/><br/>
            </form>
        </div>
        <div class="col-md-9 register-right">
            <ul class="nav nav-tabs nav-justified" id="myTab" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active" id="client-tab" data-toggle="tab" href="#client" role="tab"
                       aria-controls="client" aria-selected="true">Client</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab"
                       aria-controls="profile" aria-selected="false">Manager</a>
                </li>
            </ul>
            <div class="tab-content" id="myTabContent">
                <div class="tab-pane fade show active" id="client" role="tabpanel" aria-labelledby="client-tab">
                    <h3 class="register-heading">Register as a Client</h3>
                    <form class="form-signup" method="post" action="controller?command=registration">

                        <div class="row register-form">

                            <div class="col-md-6">
                                <div class="form-group">
                                    <input type="text" name="firstName" class="form-control" placeholder="First Name *"
                                           value=""/>
                                </div>
                                <div class="form-group">
                                    <input type="text" name="lastName" class="form-control" placeholder="Last Name *"
                                           value=""/>
                                </div>
                                <div class="form-group">
                                    <input type="password" name="password" class="form-control" placeholder="Password *"
                                           value=""/>
                                </div>
                                <div class="form-group">
                                    <input type="password" name="confirmPassword" class="form-control"
                                           placeholder="Confirm Password *"
                                           value=""/>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <input type="email" name="email" class="form-control" placeholder="Your Email *"
                                           value=""/>
                                </div>
                                <div class="form-group">
                                    <input type="text" name="country" class="form-control" placeholder="Your Country *"
                                           value=""/>
                                </div>

                                <%--                            <input type="submit" class="btnRegister" value="Register"/>--%>
                                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message
                                        key="register.button.register"/></button>
                            </div>
                        </div>
                    </form>

                </div>


                <div class="tab-pane fade show" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                    <h3 class="register-heading">Register as a Manager</h3>
                    <form class="form-signup" method="post" action="controller?command=registration">

                        <div class="row register-form">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <input type="text" name="firstName" class="form-control" placeholder="First Name *"
                                           value=""/>
                                </div>
                                <div class="form-group">
                                    <input type="text" name="lastName" class="form-control" placeholder="Last Name *"
                                           value=""/>
                                </div>
                                <div class="form-group">
                                    <input type="email" name="email" class="form-control" placeholder="Email *"
                                           value=""/>
                                </div>
                                <div class="form-group">
                                    <input type="text" name="country" class="form-control" placeholder="Country *"
                                           value=""/>
                                </div>


                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <input type="password" name="password" class="form-control" placeholder="Password *"
                                           value=""/>
                                </div>
                                <div class="form-group">
                                    <input type="password" name="confirmPassword" class="form-control"
                                           placeholder="Confirm Password *"
                                           value=""/>
                                </div>

                                <%--                                <input type="submit" class="btnRegister" value="Register"/>--%>
                                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message
                                        key="register.button.register"/></button>

                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>

<jsp:include page="/WEB-INF/templates/scripts.jsp"/>

</body>
</html>
