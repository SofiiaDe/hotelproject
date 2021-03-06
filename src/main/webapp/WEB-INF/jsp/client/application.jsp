<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<!DOCTYPE html>
<html>
<c:set var="title" value="Application form for client to book room" scope="page"/>
<jsp:include page="/WEB-INF/components/head.jsp"/>
<head>

    <title>Application form for client to book room</title>
</head>
<style>
    #appForm{
        margin-left: 35px;
        margin-top: 35px;
    }
    form input{
        margin-right: 10px;
    }
</style>
<body>
<jsp:include page="/WEB-INF/components/clientMenu.jsp"/>
<form method="post" action="controller?command=submitApplication" id="appForm">

    <h2><fmt:message key="application.header.choose_type"/></h2>
    <p>
        <input type="radio" value="single" checked name="room_seats"/><fmt:message key="room.type.single"/>
    </p>
    <p>
        <input type="radio" value="double" name="room_seats"/><fmt:message key="room.type.double"/>
    </p>
    <p>
        <input type="radio" value="triple" name="room_seats"/><fmt:message key="room.type.triple"/>
    </p>
    <p>
        <input type="radio" value="twin" name="room_seats"/><fmt:message key="room.type.twin"/>
    </p>
    <br>
    <h2><fmt:message key="application.header.choose_class"/></h2>
    <p>
        <input type="radio" value="standard" checked name="room_class"/><fmt:message key="room.class.standard"/>
    </p>
    <p>
        <input type="radio" value="business" name="room_class"/><fmt:message key="room.class.business"/>
    </p>
    <p>
        <input type="radio" value="lux" name="room_class"/><fmt:message key="room.class.lux"/>
    </p>
    <br>
    <h2><fmt:message key="application.header.choose_time"/></h2>

    <form>
        <jsp:useBean id="now" class="java.util.Date" />
        <fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />
        <p>
            <label for="date"><fmt:message key="checkin.date"/>: </label>
            <input type="date" id="date" name="checkin_date"/>
        </p>
        <p>
            <label for="date"><fmt:message key="checkout.date"/>: </label>
            <input type="date" id="dateout" name="checkout_date"/>
        </p>
        <br>
        <p>
            <button type="submit" class="btn btn-info"><fmt:message key="submit.button"/></button>
        </p>
    </form>
</form>

<jsp:include page="/WEB-INF/components/scripts.jsp"/>
</body>
</html>
