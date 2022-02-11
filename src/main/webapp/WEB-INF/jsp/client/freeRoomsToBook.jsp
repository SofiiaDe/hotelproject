<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<!doctype html>
<html>
<c:set var="title" value="Form for client to book room" scope="page" />
<jsp:include page="/WEB-INF/components/head.jsp" />

<head>
    <title>Free rooms list</title>
</head>

<body>
<jsp:include page="/WEB-INF/components/clientMenu.jsp"/>
<div class="container">
    <h1>Choose time of staying at the hotel</h1>

    <form method="get" action="controller">
        <jsp:useBean id="now" class="java.util.Date" />
        <fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />
        <input type="hidden" name="command" value="freeRoomsPage" />
        <div class="booking-wrap d-flex justify-content-between align-items-center">
            <div class="single-select-box mb-30">
                <!-- select check-in date -->
                <div class="booking-tittle">
                    <span>
                        <fmt:message key="checkin.date" />:
                    </span>
                </div>
                <div class="booking-datepicker">
                    <input type="date" name="checkin_date" placeholder="${currentDate}" min="${currentDate}" required
                        value="${requestScope.checkin}" />
                </div>
            </div>

            <input type="submit" value="Submit">

            <div class="single-select-box mb-30">
                <!-- select check-out date -->
                <div class="booking-tittle">
                    <span>
                        <fmt:message key="checkout.date" />:
                    </span>
                </div>
                <div class="booking-datepicker">
                    <input type="date" name="checkout_date" placeholder="${currentDate}" min="${currentDate}"
                        value="${requestScope.checkout}" required />
                </div>
            </div>
        </div>
    </form>

    <form action="controller?command=bookRoom" method="post">
        <c:if test="${requestScope != null && requestScope.freeRooms != null}">
            <h1>Choose a room</h1>
            <br>
            <c:forEach var="freeRoom" items="${requestScope.freeRooms}">

                <div class="list-group">

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
                            <input type="hidden" name="room_id" value="${freeRoom.id}">
                            <button class="ui-button" type="submit">
                                <fmt:message key="book.button" /></button>

                        </div>
                        <br>
                        <br>
                        <br>
                    </a>
                </div>

            </c:forEach>

        </c:if>
    </form>

    <c:if test="${requestScope != null && requestScope.page != null && requestScope.pageCount != null}">

        <form action="controller">
            <input type="hidden" name="command" value="freeRoomsPage" />
            <input type="hidden" name="checkin" value="${requestScope.checkin}" />
            <input type="hidden" name="checkout" value="${requestScope.checkout}" />

            <nav aria-label="Page navigation example">
                <ul class="pagination">

                    <li class="page-item ${requestScope.page > 1 ? "" : " disabled"}">
                        <a class="page-link" href="#" aria-label="Prev" id="prevPage">
                            <span aria-hidden="true">&laquo;</span>
                            <span class="sr-only">&laquo;</span>
                        </a>
                    </li>

                    <c:if test="${requestScope.page - 3 > 1 || (requestScope.page> 1 && requestScope.page <= 4)}">
                        <li class="page-item"><a class="page-link" href="#" id="firstPage">1</a></li>
                    </c:if>
                    <c:if test="${requestScope.page - 3 > 1}">
                        <li class="page-item disabled"><a class="page-link" href="#">...</a></li>
                    </c:if>

                    <c:if test="${requestScope.page - 2 > 1}">
                        <li class="page-item"><a class="page-link" href="#" id="pageMin2">${requestScope.page - 2}</a>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.page - 1 > 1}">
                        <li class="page-item"><a class="page-link" href="#" id="pageMin1">${requestScope.page - 1}</a>
                        </li>
                    </c:if>

                    <li class="page-item active"><a class="page-link" href="#">${requestScope.page}</a></li>

                    <c:if test="${requestScope.page + 1 <= requestScope.pageCount}">
                        <li class="page-item"><a class="page-link" href="#" id="pagePlus1">${requestScope.page + 1}</a>
                        </li>
                    </c:if>
                    <c:if test="${requestScope.page + 2 <= requestScope.pageCount}">
                        <li class="page-item"><a class="page-link" href="#" id="pagePlus2">${requestScope.page + 2}</a>
                        </li>
                    </c:if>

                    <c:if test="${requestScope.page + 4 <= requestScope.pageCount}">
                        <li class="page-item"><a class="page-link" href="#">...</a></li>
                    </c:if>
                    <c:if test="${requestScope.page + 3 <= requestScope.pageCount && requestScope.pageCount >= 4}">
                        <li class="page-item"><a class="page-link" href="#" id="lastPage">${requestScope.pageCount}</a>
                        </li>
                    </c:if>

                    <li class="page-item ${requestScope.page < requestScope.pageCount ? "": " disabled"}">
                        <a class="page-link" href="#" aria-label="Next" id="nextPage">
                            <span aria-hidden="true">&raquo;</span>
                            <span class="sr-only">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </form>

    </c:if>
</div>
    <script>
        (function () {
            setUrls("prevPage", buildUrl(${ requestScope.page } - 1));
            setUrls("nextPage", buildUrl(${ requestScope.page } + 1));
            setUrls("pageMin1", buildUrl(${ requestScope.page } - 1));
            setUrls("pageMin2", buildUrl(${ requestScope.page } - 2));
            setUrls("pagePlus1", buildUrl(${ requestScope.page } + 1));
            setUrls("pagePlus2", buildUrl(${ requestScope.page } + 2));
            setUrls("pagePlus2", buildUrl(${ requestScope.page } + 2));
            setUrls("firstPage", buildUrl(1));
            setUrls("lastPage", buildUrl(${ requestScope.pageCount }));
        })();
        function setUrls(tagId, value) {
            if (document.getElementById(tagId)) {
                console.log('found')
                document.getElementById(tagId).href = value;
            }
        }
        function buildUrl(page) {
            const result = new URL(window.location.href);
            result.searchParams.set("page", page);

            return result;
        }

    </script>

    <jsp:include page="/WEB-INF/components/scripts.jsp" />
</body>

</html>