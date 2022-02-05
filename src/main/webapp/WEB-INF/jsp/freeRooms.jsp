<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<!doctype html>
<html>
<head>
    <title>Free rooms list</title>
</head>
<body>
<form class="form-group" method="post" action="controller?command=bookRoom">

    <h1>Choose time of staying at the hotel</h1>

    <form>
        <p>
            <label for="date">Check-in date: </label>
            <input type="date" id="date" name="checkin_date"/>
        </p>
        <p>
            <label for="date">Check-out date: </label>
            <input type="date" id="dateout" name="checkout_date"/>
        </p>
        <br>
    </form>
    <h1>Choose a room</h1>
    <br>
    <c:forEach var="bean" items="${sessionScope.freeRooms}">

        <div class="list-group">
                <%--        <a href="#" class="list-group-item list-group-item-action flex-column align-items-start active">--%>

            <a class="list-group-item list-group-item-action flex-column align-items-start active">
                <div class="d-flex w-100 justify-content-between">
                    <h5 class="mb-1">Type: ${bean.roomTypeBySeats}</h5>
                    <small>No. ${bean.roomNumber}</small>
                </div>
                <p class="mb-1">Class: ${bean.roomClass}</p>
                <small>$${bean.price}</small>
<%--                <a href="/Hotel/controller?command=bookRoom" class="button">Book</a>--%>
<%--                <input type="submit" class="ui-button" value="Book"/>--%>
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="book.button.book"/></button>

                <br>
                <br>
                <br>
            </a>
        </div>


    </c:forEach>
</form>
</body>
</html>