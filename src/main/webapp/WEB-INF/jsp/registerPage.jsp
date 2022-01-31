<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<!doctype html>
<html>
<c:set var="title" value="Страница входа" scope="page"/>

<head>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <title>Registration form</title>
</head>

<body>

    <form method="post" action="controller?command=registration">

    <%--</form>--%>
<%--<br>--%>
<%--<form action="controller"--%>
<%--      method="post">--%>
<%--    <div type="center">--%>
<%--        <input type="hidden" name="command" value="registration"><br>  <input>--%>
<%--    </div>--%>
<%--</form>--%>
<%--<p>Name: ${sessionScope.newUser}</p>--%>



<div class="container register">
    <div class="row">
        <div class="col-md-3 register-left">
            <img src="https://image.ibb.co/n7oTvU/logo_white.png" alt=""/>
            <h3>Welcome</h3>
            <p>Premium comfort with attention to details</p>
            <input type="submit" name="" value="Login"/><br/>
        </div>
        <div class="col-md-9 register-right">
            <ul class="nav nav-tabs nav-justified" id="myTab" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">Client</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">Manager</a>
                </li>
            </ul>
            <div class="tab-content" id="myTabContent">
                <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                    <h3 class="register-heading">Register as a Client</h3>
                    <div class="row register-form">
                        <div class="col-md-6">
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="First Name *" value="" />
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Last Name *" value="" />
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control" placeholder="Password *" value="" />
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control"  placeholder="Confirm Password *" value="" />
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <input type="email" class="form-control" placeholder="Your Email *" value="" />
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Your Country *" value="" />
                            </div>
<%--                            <div class="form-group">--%>
<%--                                <select class="form-control">--%>
<%--                                    <option class="hidden"  selected disabled>Please select your Security Question</option>--%>
<%--                                    <option>What is your Birthdate?</option>--%>
<%--                                    <option>What is Your old Phone Number</option>--%>
<%--                                    <option>What is your Pet Name?</option>--%>
<%--                                </select>--%>
<%--                            </div>--%>
<%--                            <div class="form-group">--%>
<%--                                <input type="text" class="form-control" placeholder="Enter Your Answer *" value="" />--%>
<%--                            </div>--%>
                            <input type="submit" class="btnRegister"  value="Register"/>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade show" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                    <h3  class="register-heading">Register as a Manager</h3>
                    <div class="row register-form">
                        <div class="col-md-6">
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="First Name *" value="firstName" />
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Last Name *" value="lastName" />
                            </div>
                            <div class="form-group">
                                <input type="email" class="form-control" placeholder="Email *" value="email" />
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Country *" value="country" />
                            </div>


                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <input type="password" class="form-control" placeholder="Password *" value="password" />
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control" placeholder="Confirm Password *" value="password" />
                            </div>
<%--                            <div class="form-group">--%>
<%--                                <select class="form-control">--%>
<%--                                    <option class="hidden"  selected disabled>Please select your Security Question</option>--%>
<%--                                    <option>What is your Birthdate?</option>--%>
<%--                                    <option>What is Your old Phone Number</option>--%>
<%--                                    <option>What is your Pet Name?</option>--%>
<%--                                </select>--%>
<%--                            </div>--%>
<%--                            <div class="form-group">--%>
<%--                                <input type="text" class="form-control" placeholder="`Answer *" value="" />--%>
<%--                            </div>--%>
<%--                            <input type="submit" class="btnRegister"  value="registration"/>--%>
                            <input type="submit" class="btnRegister" value="Register"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
</form>

</body>
</html>
