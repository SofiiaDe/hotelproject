<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<!doctype html>
<html>
<style>
    .dropDownPadding {
        margin-right: 20px;
    }

    #sortingSection {
        margin-top: 20px;
        display: flex;
    }

    .bookingSection {
        color: white;
        margin-bottom: 10px;
    }

    #bookBtn {
        margin-top: 5px;
    }
</style>
<c:set var="title" value="Form for client to book room" scope="page"/>
<jsp:include page="/WEB-INF/components/head.jsp"/>


<head>
    <title>Free rooms list</title>
</head>

<body>
<jsp:include page="/WEB-INF/components/clientMenu.jsp"/>
<div class="container">
    <h1><fmt:message key="client.book.choose_time"/></h1>

    <form method="get" action="controller">
        <jsp:useBean id="now" class="java.util.Date"/>
        <fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd"/>
        <input type="hidden" name="command" value="freeRoomsPage"/>
        <div class="booking-wrap d-flex justify-content-between align-items-center">
            <div class="single-select-box mb-30">
                <!-- select check-in date -->
                <div class="booking-tittle">
                    <span>
                        <fmt:message key="checkin.date"/>:
                    </span>
                </div>
                <div class="booking-datepicker">
                    <input type="date" name="checkin_date" placeholder="${currentDate}" min="${currentDate}" required
                           value="${requestScope.checkin}"/>
                </div>
            </div>

            <div class="single-select-box mb-30">
                <!-- select check-out date -->
                <div class="booking-tittle">
                    <span>
                        <fmt:message key="checkout.date"/>:
                    </span>
                </div>
                <div class="booking-datepicker">
                    <input type="date" name="checkout_date" placeholder="${currentDate}" min="${currentDate}"
                           value="${requestScope.checkout}" required/>
                </div>
            </div>

            <button type="submit" class="btn btn-info"><fmt:message key="find.button"/></button>
        </div>
    </form>

    <form action="controller?command=bookRoom" method="post">
        <input type="hidden" id="room_id" name="room_id">
        <c:if test="${requestScope != null && requestScope.freeRooms != null}">
            <input type="hidden" name="checkin_date" value="${requestScope.checkin}">
            <input type="hidden" name="checkout_date" value="${requestScope.checkout}">
            <div id="sortingSection">
                <div class="dropdown dropDownPadding">
                    <button class="btn btn-info dropdown-toggle" type="button" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false">
                        <fmt:message key="sorting"/>
                        <c:if test="${requestScope.sortType.text != null && requestScope.sortBy.text != null}">
                            <c:choose>
                                <c:when test="${requestScope.sortBy.text == 'price' && requestScope.sortType.text == 'asc'}">
                                    <fmt:message key="sorting.price.asc"/>
                                </c:when>
                                <c:when test="${requestScope.sortBy.text == 'price' && requestScope.sortType.text == 'desc'}">
                                    <fmt:message key="sorting.price.desc"/>
                                </c:when>
                                <c:when test="${requestScope.sortBy.text == 'class' && requestScope.sortType.text == 'asc'}">
                                    <fmt:message key="sorting.class.asc"/>
                                </c:when>
                                <c:when test="${requestScope.sortBy.text == 'class' && requestScope.sortType.text == 'desc'}">
                                    <fmt:message key="sorting.class.desc"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="sorting.unknown"/>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                        <a class="dropdown-item" href="#" id="priceAsc"><fmt:message key="sorting.price.asc"/></a>
                        <a class="dropdown-item" href="#" id="priceDesc"><fmt:message key="sorting.price.desc"/></a>
                        <a class="dropdown-item" href="#" id="classAsc"><fmt:message key="sorting.class.asc"/></a>
                        <a class="dropdown-item" href="#" id="classDesc"><fmt:message key="sorting.class.desc"/></a>
                    </div>
                </div>

                <div class="dropdown dropDownPadding">
                    <button class="btn btn-info dropdown-toggle" type="button" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false">
                        <c:if test="${requestScope.roomStatus != null}">
                            <c:choose>
                                <c:when test="${requestScope.roomStatus == 'available'}">
                                    <fmt:message key="sort.status.available"/>
                                </c:when>
                                <c:when test="${requestScope.roomStatus == 'reserved'}">
                                    <fmt:message key="sort.status.reserved"/>
                                </c:when>
                                <c:when test="${requestScope.roomStatus == 'booked'}">
                                    <fmt:message key="sort.status.booked"/>
                                </c:when>
                                <c:when test="${requestScope.roomStatus == 'unavailable'}">
                                    <fmt:message key="sort.status.unavailable"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="sort.status.unknown"/>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                        <c:if test="${requestScope.roomStatus == null}"><fmt:message key="status"/></c:if>
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                        <a class="dropdown-item" href="#" id="roomAvailable"><fmt:message
                                key="sort.status.available"/></a>
                        <a class="dropdown-item" href="#" id="roomReserved"><fmt:message
                                key="sort.status.reserved"/></a>
                        <a class="dropdown-item" href="#" id="roomBooked"><fmt:message key="sort.status.booked"/></a>
                        <a class="dropdown-item" href="#" id="roomUnavailable"><fmt:message
                                key="sort.status.unavailable"/></a>
                    </div>
                </div>

                <div class="dropdown">
                    <button class="btn btn-info dropdown-toggle" type="button" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false">
                        <c:if test="${requestScope.roomSeats != null}">
                            <c:choose>
                                <c:when test="${requestScope.roomSeats == 'single'}">
                                    <fmt:message key="sort.seats.single"/>
                                </c:when>
                                <c:when test="${requestScope.roomSeats == 'double'}">
                                    <fmt:message key="sort.seats.double"/>
                                </c:when>
                                <c:when test="${requestScope.roomSeats == 'twin'}">
                                    <fmt:message key="sort.seats.twin"/>
                                </c:when>
                                <c:when test="${requestScope.roomSeats == 'triple'}">
                                    <fmt:message key="sort.seats.triple"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="sort.seats.unknown"/>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                        <c:if test="${requestScope.roomSeats == null}"><fmt:message key="sort.seats"/></c:if>
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                        <a class="dropdown-item" href="#" id="seatsSingle"><fmt:message key="sort.seats.single"/></a>
                        <a class="dropdown-item" href="#" id="seatsDouble"><fmt:message key="sort.seats.double"/></a>
                        <a class="dropdown-item" href="#" id="seatsTwin"><fmt:message key="sort.seats.twin"/></a>
                        <a class="dropdown-item" href="#" id="seatsTriple"><fmt:message key="sort.seats.triple"/></a>
                    </div>
                </div>
            </div>

            <h1><fmt:message key="client.book.choose_room"/></h1>
            <br>
            <c:forEach var="room" items="${requestScope.freeRooms}">
                <input type="hidden" name="room_id" value="${room.id}"/>
                <div class="bookingSection">

                    <a class="list-group-item list-group-item-action flex-column align-items-start active"
                       style="background-color: #17a2b8">
                        <div class="d-flex w-100 justify-content-between">
                            <h5 class="mb-1"><fmt:message key="client.book.type"/>: ${room.roomTypeBySeats}</h5>

                            <c:if test="${room.id != null}">
                                <c:choose>
                                    <c:when test="${room.id == 6}">
                                        <img src="pictures/6single_business.jpg" alt="single business"
                                             style="width:400px;height:300px;">
                                    </c:when>
                                    <c:when test="${room.id == 7}">
                                        <img src="pictures/7double_standard.jpg" alt="double standard"
                                             style="width:400px;height:300px;">
                                    </c:when>
                                    <c:when test="${room.id == 8}">
                                        <img src="pictures/8twin_business.jpg" alt="twin business"
                                             style="width:400px;height:300px;">
                                    </c:when>
                                    <c:when test="${room.id == 9}">
                                        <img src="pictures/9triple_standard.jpg" alt="triple standard"
                                             style="width:400px;height:300px;">
                                    </c:when>
                                    <c:when test="${room.id == 10}">
                                        <img src="pictures/10lux_double.jpg" alt="double lux"
                                             style="width:400px;height:300px;">
                                    </c:when>
                                    <c:when test="${room.id == 11}">
                                        <img src="pictures/11single_business.jpg" alt="single business"
                                             style="width:400px;height:300px;">
                                    </c:when>
                                    <c:when test="${room.id == 12}">
                                        <img src="pictures/12double_standard.jpg" alt="double standard"
                                             style="width:400px;height:300px;">
                                    </c:when>
                                    <c:when test="${room.id == 13}">
                                        <img src="pictures/13business_double.jpg" alt="double business"
                                             style="width:400px;height:300px;">
                                    </c:when>
                                    <c:when test="${room.id == 14}">
                                        <img src="pictures/14triple_business.jpg" alt="triple business"
                                             style="width:400px;height:300px;">
                                    </c:when>
                                    <c:when test="${room.id == 15}">
                                        <img src="pictures/15lux_twin.jpg" alt="twin lux"
                                             style="width:400px;height:300px;">
                                    </c:when>
                                    <c:when test="${room.id == 16}">
                                        <img src="pictures/16single_lux.jpg" alt="single lux"
                                             style="width:400px;height:300px;">
                                    </c:when>
                                    <c:when test="${room.id == 17}">
                                        <img src="pictures/17double_standard.jpg" alt="double standard"
                                             style="width:400px;height:300px;">
                                    </c:when>
                                    <c:when test="${room.id == 18}">
                                        <img src="pictures/18business_double.jpg" alt="double business"
                                             style="width:400px;height:300px;">
                                    </c:when>
                                    <c:when test="${room.id == 19}">
                                        <img src="pictures/19triple_business.jpg" alt="triple business"
                                             style="width:400px;height:300px;">
                                    </c:when>
                                    <c:when test="${room.id == 20}">
                                        <img src="pictures/20single_standard.jpg" alt="twin standard"
                                             style="width:400px;height:300px;">
                                    </c:when>
                                    <c:when test="${room.id == 21}">
                                        <img src="pictures/21single_standard.jpg" alt="single standard"
                                             style="width:400px;height:300px;">
                                    </c:when>
                                    <c:when test="${room.id == 22}">
                                        <img src="pictures/22single_standard.jpg" alt="single standard"
                                             style="width:400px;height:300px;">
                                    </c:when>
<%--                                    <c:otherwise>--%>
<%--                                        UNKNOWN_STATUS--%>
<%--                                    </c:otherwise>--%>
                                </c:choose>
                            </c:if>
                            <c:if test="${room.id == null}"> </c:if>

                            <small>No. ${room.roomNumber}</small>
                        </div>
                        <div class="d-flex w-100 justify-content-between">
                            <h5 class="mb-1"><fmt:message key="client.book.class"/>: ${room.roomClass}</h5>
                        </div>
                        <small>$${room.price}</small>

                        <c:if test="${requestScope.roomStatus == null || requestScope.roomStatus == 'available'}">
                            <div id="bookBtn">
                                <button class="btn btn-light" type="submit"
                                        onclick="setInputValue('room_id', ${room.id})">
                                    <fmt:message key="book.button"/></button>
                            </div>
                        </c:if>
                        <br>
                        <br>
                    </a>
                </div>

            </c:forEach>

        </c:if>
    </form>

    <c:if test="${requestScope != null && requestScope.page != null && requestScope.pageCount != null}">
        <form action="controller">
            <input type="hidden" name="command" value="freeRoomsPage"/>
            <input type="hidden" name="checkin" value="${requestScope.checkin}"/>
            <input type="hidden" name="checkout" value="${requestScope.checkout}"/>

            <nav aria-label="Free rooms navigation">
                <jsp:include page="/WEB-INF/components/pagination.jsp"/>
            </nav>
        </form>
    </c:if>
</div>
<jsp:include page="/WEB-INF/components/scripts.jsp"/>
<script>
    function setInputValue(tagId, value) {
        document.getElementById(tagId).value = value;
    }

    function setUrls(tagId, value) {
        if (document.getElementById(tagId)) {
            document.getElementById(tagId).href = value;
        }
    }

    (function () {
        setUrls("prevPage", buildUrl(${ requestScope.page } -1));
        setUrls("nextPage", buildUrl(${ requestScope.page } +1));
        setUrls("pageMin1", buildUrl(${ requestScope.page } -1));
        setUrls("pageMin2", buildUrl(${ requestScope.page } -2));
        setUrls("pagePlus1", buildUrl(${ requestScope.page } +1));
        setUrls("pagePlus2", buildUrl(${ requestScope.page } +2));
        setUrls("pagePlus2", buildUrl(${ requestScope.page } +2));
        setUrls("firstPage", buildUrl(1));
        setUrls("lastPage", buildUrl(${ requestScope.pageCount }));

        setUrls("priceAsc", buildUrl(1, "price", "asc"));
        setUrls("priceDesc", buildUrl(1, "price", "desc"));
        setUrls("classAsc", buildUrl(1, "class", "asc"));
        setUrls("classDesc", buildUrl(1, "class", "desc"));

        setUrls("roomAvailable", buildUrl(1, undefined, undefined, "available"));
        setUrls("roomReserved", buildUrl(1, undefined, undefined, "reserved"));
        setUrls("roomBooked", buildUrl(1, undefined, undefined, "booked"));
        setUrls("roomUnavailable", buildUrl(1, undefined, undefined, "unavailable"));

        setUrls("seatsSingle", buildUrl(1, undefined, undefined, undefined, "single"));
        setUrls("seatsDouble", buildUrl(1, undefined, undefined, undefined, "double"));
        setUrls("seatsTwin", buildUrl(1, undefined, undefined, undefined, "twin"));
        setUrls("seatsTriple", buildUrl(1, undefined, undefined, undefined, "triple"));
    })();

    function buildUrl(page, sortBy, sortType, roomStatus, roomSeats) {
        const result = new URL(window.location.href);
        result.searchParams.set("page", page);

        setOptionalSearchParam(result.searchParams, "sortBy", sortBy);
        +
            setOptionalSearchParam(result.searchParams, "sortType", sortType);
        setOptionalSearchParam(result.searchParams, "roomStatus", roomStatus);
        setOptionalSearchParam(result.searchParams, "roomSeats", roomSeats);

        return result;
    }

    function setOptionalSearchParam(searchParams, paramName, value) {
        const data = value ?? searchParams.get(paramName);
        if (data) {
            searchParams.set(paramName, data);
        }
    }

</script>

</body>

</html>
