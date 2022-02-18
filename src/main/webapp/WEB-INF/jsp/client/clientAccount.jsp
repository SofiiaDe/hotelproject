<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/page.jspf" %>

<%@ taglib prefix="htl" tagdir="/WEB-INF/tags" %>

<!doctype html>
<html>
<c:set var="title" value="Client's desk" scope="page"/>
<jsp:include page="/WEB-INF/components/head.jsp"/>

<style>
    /* The snackbar - position it at the bottom and in the middle of the screen */
    #succRegSnackbar {
        visibility: hidden; /* Hidden by default. Visible on click */
        min-width: 250px; /* Set a default minimum width */
        margin-left: -125px; /* Divide value of min-width by 2 */
        background-color: #333; /* Black background color */
        color: #fff; /* White text color */
        text-align: center; /* Centered text */
        border-radius: 2px; /* Rounded borders */
        padding: 16px; /* Padding */
        position: fixed; /* Sit on top of the screen */
        z-index: 1; /* Add a z-index if needed */
        left: 50%; /* Center the snackbar */
        bottom: 30px; /* 30px from the bottom */
    }

    /* Show the snackbar when clicking on a button (class added with JavaScript) */
    #succRegSnackbar.show {
        visibility: visible; /* Show the snackbar */
        /* Add animation: Take 0.5 seconds to fade in and out the snackbar.
        However, delay the fade out process for 2.5 seconds */
        -webkit-animation: fadein 0.5s, fadeout 0.5s 2.5s;
        animation: fadein 0.5s, fadeout 0.5s 2.5s;
    }

    /* Animations to fade the snackbar in and out */
    @-webkit-keyframes fadein {
        from {bottom: 0; opacity: 0;}
        to {bottom: 30px; opacity: 1;}
    }

    @keyframes fadein {
        from {bottom: 0; opacity: 0;}
        to {bottom: 30px; opacity: 1;}
    }

    @-webkit-keyframes fadeout {
        from {bottom: 30px; opacity: 1;}
        to {bottom: 0; opacity: 0;}
    }

    @keyframes fadeout {
        from {bottom: 30px; opacity: 1;}
        to {bottom: 0; opacity: 0;}
    }
</style>

<body>
<jsp:include page="/WEB-INF/components/clientMenu.jsp"/>
<div class="container">
    <div class="tab-pane fade show active" id="v-pills-services" role="tabpanel"
         aria-labelledby="v-pills-services-tab">
        <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item">
                <a class="nav-link active" id="booking-tab" data-toggle="tab" href="#booking"
                   role="tab"
                   aria-controls="booking" aria-selected="true">
                    <fmt:message key="client.table.user_bookings"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="application-tab" data-toggle="tab" href="#application" role="tab"
                   aria-controls="application"
                   aria-selected="false">
                    <fmt:message key="client.table.user_applications"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="confirmrequest-tab" data-toggle="tab" href="#confirmrequest" role="tab"
                   aria-controls="confirmrequest"
                   aria-selected="false">
                    <fmt:message key="client.table.user_confirmation_requests"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="invoice-tab" data-toggle="tab" href="#invoice" role="tab"
                   aria-controls="invoice"
                   aria-selected="false">
                    <fmt:message key="client.table.user_invoices"/>
                </a>
            </li>

        </ul>
        <div class="tab-content" id="myTabContent">

            <%-- My bookings --%>
            <div class="tab-pane fade active show" id="booking" role="tabpanel" aria-labelledby="booking-tab">
                <table class="table table-hover mt-2">
                    <thead>
                    <tr>
                        <th scope="col">
                            <fmt:message key="checkin.date"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="checkout.date"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.room_seats"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.room_class"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.status"/>
                        </th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="booking" items="${sessionScope.myBookings}">

                        <tr>
                            <td>${booking.checkinDate}</td>
                            <td>${booking.checkoutDate}</td>
                            <td>
                                <c:if test="${booking.roomTypeBySeats != null}">
                                    <c:choose>
                                        <c:when test="${booking.roomTypeBySeats == 'single'}">
                                            <fmt:message key="room.type.single"/>
                                        </c:when>
                                        <c:when test="${booking.roomTypeBySeats == 'double'}">
                                            <fmt:message key="room.type.double"/>
                                        </c:when>
                                        <c:when test="${booking.roomTypeBySeats == 'twin'}">
                                            <fmt:message key="room.type.twin"/>
                                        </c:when>
                                        <c:when test="${booking.roomTypeBySeats == 'triple'}">
                                            <fmt:message key="room.type.triple"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="sort.seats.unknown"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${booking.roomClass != null}">
                                    <c:choose>
                                        <c:when test="${booking.roomClass == 'standard'}">
                                            <fmt:message key="room.class.standard"/>
                                        </c:when>
                                        <c:when test="${booking.roomClass == 'business'}">
                                            <fmt:message key="room.class.business"/>
                                        </c:when>
                                        <c:when test="${booking.roomClass == 'lux'}">
                                            <fmt:message key="room.class.lux"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="sort.seats.unknown"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${booking.bookingStatus.text != null}">
                                    <c:choose>
                                        <c:when test="${booking.bookingStatus.text == 'new'}">
                                            <fmt:message key="status.new"/>
                                        </c:when>
                                        <c:when test="${booking.bookingStatus.text == 'cancelled'}">
                                            <fmt:message key="status.cancelled"/>
                                        </c:when>
                                        <c:when test="${booking.bookingStatus.text == 'paid'}">
                                            <fmt:message key="status.paid"/>
                                        </c:when>
                                        <c:when test="${booking.bookingStatus.text == 'finished'}">
                                            <fmt:message key="status.finished"/>
                                        </c:when>
                                        <c:when test="${booking.bookingStatus.text == 'ongoing'}">
                                            <fmt:message key="status.ongoing"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="status"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <c:if test="${requestScope != null && requestScope.page != null && requestScope.pageCount != null}">
                    <form action="controller">
                        <input type="hidden" name="command" value="clientAccount"/>
                        <nav aria-label="Client's lift of booking navigation">
                            <jsp:include page="/WEB-INF/components/pagination.jsp"/>
                        </nav>
                    </form>
                </c:if>
            </div>


            <%-- My applications --%>
            <div class="tab-pane fade" id="application" role="tabpanel" aria-labelledby="application-tab">
                <table class="table table-hover mt-2">
                    <thead>
                    <tr>
                        <th scope="col">
                            <fmt:message key="table.th.room_seats"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.room_class"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="checkin.date"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="checkout.date"/>
                        </th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="application" items="${sessionScope.myApplications}">
                        <tr>
                            <td>
                                <c:if test="${application.roomTypeBySeats != null}">
                                    <c:choose>
                                        <c:when test="${application.roomTypeBySeats == 'single'}">
                                            <fmt:message key="room.type.single"/>
                                        </c:when>
                                        <c:when test="${application.roomTypeBySeats == 'double'}">
                                            <fmt:message key="room.type.double"/>
                                        </c:when>
                                        <c:when test="${application.roomTypeBySeats == 'twin'}">
                                            <fmt:message key="room.type.twin"/>
                                        </c:when>
                                        <c:when test="${application.roomTypeBySeats == 'triple'}">
                                            <fmt:message key="room.type.triple"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="sort.seats.unknown"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${application.roomClass != null}">
                                    <c:choose>
                                        <c:when test="${application.roomClass == 'standard'}">
                                            <fmt:message key="room.class.standard"/>
                                        </c:when>
                                        <c:when test="${application.roomClass == 'business'}">
                                            <fmt:message key="room.class.business"/>
                                        </c:when>
                                        <c:when test="${application.roomClass == 'lux'}">
                                            <fmt:message key="room.class.lux"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="sort.seats.unknown"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </td>
                            <td>${application.checkinDate}</td>
                            <td>${application.checkoutDate}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <%-- Confirmation Request --%>
            <div class="tab-pane fade" id="confirmrequest" role="tabpanel" aria-labelledby="confirmrequest-tab">
                <table class="table table-hover mt-2">
                    <thead>
                    <tr>
                        <th scope="col">
                            <fmt:message key="table.th.confirmrequest_date"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.confirmrequest_due_date"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.room_seats"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.room_class"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="checkin.date"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="checkout.date"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.application_id"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.status"/>
                        </th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="confirmrequest" items="${sessionScope.myConfirmRequests}">
                        <tr>
                            <td>${confirmrequest.confirmRequestDate}</td>
                            <td>${confirmrequest.confirmRequestDueDate}</td>
                            <td>
                                <c:if test="${confirmrequest.roomTypeBySeats != null}">
                                    <c:choose>
                                        <c:when test="${confirmrequest.roomTypeBySeats == 'single'}">
                                            <fmt:message key="room.type.single"/>
                                        </c:when>
                                        <c:when test="${confirmrequest.roomTypeBySeats == 'double'}">
                                            <fmt:message key="room.type.double"/>
                                        </c:when>
                                        <c:when test="${confirmrequest.roomTypeBySeats == 'twin'}">
                                            <fmt:message key="room.type.twin"/>
                                        </c:when>
                                        <c:when test="${confirmrequest.roomTypeBySeats == 'triple'}">
                                            <fmt:message key="room.type.triple"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="sort.seats.unknown"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${confirmrequest.roomClass != null}">
                                    <c:choose>
                                        <c:when test="${confirmrequest.roomClass == 'standard'}">
                                            <fmt:message key="room.class.standard"/>
                                        </c:when>
                                        <c:when test="${confirmrequest.roomClass == 'business'}">
                                            <fmt:message key="room.class.business"/>
                                        </c:when>
                                        <c:when test="${confirmrequest.roomClass == 'lux'}">
                                            <fmt:message key="room.class.lux"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="sort.seats.unknown"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </td>

                            <td>${confirmrequest.checkinDate}</td>
                            <td>${confirmrequest.checkoutDate}</td>
                            <td>${confirmrequest.applicationId}</td>
                            <td><htl:requestStatus request="${confirmrequest.status}"/></td>
                            <td>
                                <div class="ml-1">
                                    <form action="controller?command=confirmRequest" method="post">
                                        <input type="hidden" name="confirmRequest_id"
                                               value="${confirmrequest.id}">
                                        <button class="ui-button" type="submit"><fmt:message
                                                key="confirm.button"/></button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>


            <%-- Invoices --%>
            <div class="tab-pane fade" id="invoice" role="tabpanel" aria-labelledby="invoice-tab">
                <table class="table table-hover mt-2">
                    <thead>
                    <tr>
                        <th scope="col">
                            <fmt:message key="table.th.invoice_date"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.invoice_due_date"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.invoice_amount"/>, $
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.booking_id"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.room_price"/>, $
                        </th>
                        <th scope="col">
                            <fmt:message key="checkin.date"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="checkout.date"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.status"/>
                        </th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach var="invoice" items="${sessionScope.myInvoices}">
                        <tr>
                            <td>${invoice.invoiceDate}</td>
                            <td>${invoice.dueDate}</td>
                            <td>${invoice.amount}</td>
                            <td>${invoice.bookingId}</td>
                            <td>${invoice.pricePerNight}</td>
                            <td>${invoice.checkInDate}</td>
                            <td>${invoice.checkOutDate}</td>
                            <td><htl:invoiceStatus invoiceStatus="${invoice.status}"/></td>

                            <td>
                                <c:if test="${invoice.status == null || invoice.status == 'new'}">

                                    <div class="ml-1">
                                        <a class="nav-link"
                                           href="controller?command=paymentPage&invoiceId=${invoice.id}">

                                            <button class="ui-button" type="submit"><fmt:message
                                                    key="pay.button"/></button>
                                        </a>
                                    </div>
                                </c:if>


                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

        </div>
    </div>
</div>

<%--<div id="successApplicSnackbar"><fmt:message key="client.snackbar.success.submit_application"/></div>--%>


<jsp:include page="/WEB-INF/components/scripts.jsp"/>
<script>
    (function () {
        const url = new URL(window.location.href);
        if(url.searchParams.get("successApplic")){
            showSnackbar("successApplicSnackbar");
        }
        function showSnackbar(tagId) {
            const x = document.getElementById(tagId);
            x.className = "show";
            setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
        }
    })();
</script>

</body>
</html>

