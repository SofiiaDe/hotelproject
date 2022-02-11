<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<!doctype html>
<html>
<c:set var="title" value="Form for client to book room" scope="page"/>
<jsp:include page="/WEB-INF/components/head.jsp"/>
<head>
    <title>Free rooms list</title>
</head>
<body>

<h1>Choose time of staying at the hotel</h1>
<%--<form class="form-group" method="post" action="controller?command=bookRoom">--%>

<%--<form>--%>
<%--    <p>--%>
<%--        <label for="date">Check-in date: </label>--%>
<%--        <input type="date" class="datepicker" id="date" name="checkin_date"/>--%>
<%--    </p>--%>
<%--    <p>--%>
<%--        <label for="date">Check-out date: </label>--%>
<%--        <input type="date" class="datepicker" id="dateout" name="checkout_date"/>--%>
<%--    </p>--%>
<%--    <br>--%>
<%--</form>--%>
<form method="get" action="controller">
    <jsp:useBean id="now" class="java.util.Date"/>
    <fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd"/>
    <input type="hidden" name="command" value="freeRoomsPage" />
    <div class="booking-wrap d-flex justify-content-between align-items-center">
        <div class="single-select-box mb-30">
            <!-- select check-in date -->
            <div class="booking-tittle">
                                            <span>
                                                <fmt:message key="checkin.date"/>:
                                            </span>
            </div>
            <div class="booking-datepicker">
                <input type="date" name="checkin_date" placeholder="${currentDate}"
                       min="${currentDate}" required/>
            </div>
        </div>
        <input type="submit" value="Submit">
        <div class="single-select-box mb-30">
            <!-- select check-out date -->
            <div class="booking-tittle">
                                            <span>
                                                <fmt:message key="checkout.date"/>:
                                            </span>
            </div>
            <div class="booking-datepicker">
                <input type="date" name="checkout_date" placeholder="${currentDate}"
                       min="${currentDate}" required/>
            </div>
        </div>
    </div>
</form>

<form action="controller?command=bookRoom" method="post">
    <c:if test="${sessionScope.freeRooms != null}">
        <h1>Choose a room</h1>
        <br>
    </c:if>
    <c:forEach var="freeRoom" items="${sessionScope.freeRooms}">

        <div class="list-group">
                <%--                        <a href="#" class="list-group-item list-group-item-action flex-column align-items-start active">--%>

            <a class="list-group-item list-group-item-action flex-column align-items-start active">
                <div class="d-flex w-100 justify-content-between">
                    <h5 class="mb-1">Type: ${freeRoom.roomTypeBySeats}</h5>
                    <small>No. ${freeRoom.roomNumber}</small>
                </div>
                <div class="d-flex w-100 justify-content-between">
                    <h5 class="mb-1">Type: ${freeRoom.roomClass}</h5>
                </div>
                <small>$${freeRoom.price}</small>

                <div class="ml-1">
                    <input type="hidden" name="room_id"
                           value="${freeRoom.id}">
                    <button class="ui-button" type="submit"><fmt:message key="book.button"/></button>

                </div>
                <br>
                <br>
                <br>
            </a>
        </div>

    </c:forEach>
</form>

<jsp:include page="/WEB-INF/components/scripts.jsp"/>
</body>
</html>