<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/page.jspf" %>

<%@ taglib prefix="htl" tagdir="/WEB-INF/tags" %>

<!doctype html>
<html>
<c:set var="title" value="Manager's desk" scope="page"/>
<jsp:include page="/WEB-INF/components/head.jsp"/>
<body>
<jsp:include page="/WEB-INF/components/managerMenu.jsp"/>
<div class="container">
    <div class="tab-pane fade show active" id="v-pills-services" role="tabpanel"
         aria-labelledby="v-pills-services-tab">
        <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item">
                <a class="nav-link active" id="booking-tab" data-toggle="tab" href="#booking"
                   role="tab"
                   aria-controls="booking" aria-selected="true">
                    <fmt:message key="manager.table.bookings"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="application-tab" data-toggle="tab" href="#application" role="tab"
                   aria-controls="application"
                   aria-selected="false">
                    <fmt:message key="manager.table.applications"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="confirmrequest-tab" data-toggle="tab" href="#confirmrequest" role="tab"
                   aria-controls="confirmrequest"
                   aria-selected="false">
                    <fmt:message key="manager.table.confirmation_requests"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="invoice-tab" data-toggle="tab" href="#invoice" role="tab"
                   aria-controls="invoice"
                   aria-selected="false">
                    <fmt:message key="manager.table.invoices"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="new-room-tab" data-toggle="tab" href="#new-room"
                   role="tab"
                   aria-controls="new-room" aria-selected="false">
                    <fmt:message key="manager.form.new_room"/>
                </a>
            </li>
        </ul>

        <div class="tab-content" id="myTabContent">
            <%-- Booking --%>
            <div class="dropdown dropDownPadding">
                <button class="btn btn-info dropdown-toggle" type="button" data-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false">
                    <fmt:message key="status"/>
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <a class="dropdown-item" href="#" id="statusPaid"><fmt:message key="status.paid"/></a>
                    <a class="dropdown-item" href="#" id="statusOngoing"><fmt:message key="status.ongoing"/></a>
                    <a class="dropdown-item" href="#" id="statusFinished"><fmt:message key="status.finished"/></a>
                    <a class="dropdown-item" href="#" id="statusNew"><fmt:message key="status.new"/></a>
                    <a class="dropdown-item" href="#" id="statusCancelled"><fmt:message key="status.cancelled"/></a>
                </div>
            </div>
            <div class="tab-pane fade active show" id="booking" role="tabpanel" aria-labelledby="booking-tab">
                <table class="table table-hover mt-2">
                    <thead>
                    <tr>
                        <th scope="col">
                            <fmt:message key="table.th.booked_by"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.user_email"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="checkin.date"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="checkout.date"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.room_number"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.status"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="booking" items="${sessionScope.allBookings}">
                        <tr>
                            <td>${booking.bookedByUser}</td>
                            <td>${booking.bookedByUserEmail}</td>
                            <td>${booking.checkinDate}</td>
                            <td>${booking.checkoutDate}</td>
                            <td>${booking.roomNumber}</td>
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
                                            !
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                                <c:if test="${booking.bookingStatus.text == null}"><fmt:message key="status"/></c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <c:if test="${requestScope != null && requestScope.page != null && requestScope.pageCount != null}">
                    <form action="controller">
                        <input type="hidden" name="command" value="managerAccount"/>
                        <nav aria-label="Manager's lift of booking navigation">
                            <jsp:include page="/WEB-INF/components/pagination.jsp"/>
                        </nav>
                    </form>
                </c:if>
            </div>

            <%-- Application --%>
            <div class="tab-pane fade" id="application" role="tabpanel" aria-labelledby="application-tab">
                <table class="table table-hover mt-2">
                    <thead>
                    <tr>
                        <th scope="col">
                            <fmt:message key="table.th.booked_by"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.user_email"/>
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

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="application" items="${sessionScope.allApplications}">
                        <tr>
                            <td>${application.bookedByUser}</td>
                            <td>${application.bookedByUserEmail}</td>
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
                            <td>
                                <div class="d-flex justify-content-end">
                                    <div>
                                        <button type="submit" class="btn btn-outline-secondary btn-sm"
                                                data-toggle="modal"
                                                data-target="#makeRequestModalCenter${application.id}">
                                            <i class="material-icons"><fmt:message key="manager.make.request"/></i>
                                        </button>
                                        <!-- Modal -->
                                        <div class="modal fade bd-example-modal-lg"
                                             id="makeRequestModalCenter${application.id}"
                                             tabindex="-1"
                                             role="dialog"
                                             aria-labelledby="#makeRequestModalCenterTitle"
                                             aria-hidden="true">
                                            <div class="modal-dialog modal-dialog-centered modal-lg"
                                                 role="document">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title"
                                                            id="makeRequestModalCenterTitle">
                                                            <fmt:message key="manager.make.request"/>
                                                        </h5>
                                                        <button type="button" class="close"
                                                                data-dismiss="modal"
                                                                aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>

                                                    <div class="modal-body">
                                                        <form method="post"
                                                              action="controller?command=makeConfirmRequest">

                                                            <div class="form-group">
                                                                <div class="row">
                                                                    <div class="col">
                                                                        <input type="text" name="checkin_date"
                                                                               class="form-control"
                                                                               value="${application.checkinDate}"
                                                                               minlength="1"
                                                                               maxlength="40"
                                                                               required>
                                                                    </div>
                                                                    <div class="col">
                                                                        <input type="text" name="checkout_date"
                                                                               class="form-control"
                                                                               value="${application.checkoutDate}"
                                                                               minlength="1"
                                                                               maxlength="40"
                                                                               required>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group">
                                                                <fmt:message
                                                                        key="manager.request.specify_room"/>

                                                                <textarea class="form-control"
                                                                          name="description"
                                                                          rows="3" minlength="10"
                                                                          maxlength="250"
                                                                          required>${application.roomClass} ${application.roomTypeBySeats}
                                                                </textarea>
                                                            </div>

                                                            <div class="d-flex justify-content-end">
                                                                <input type="hidden" name="applicationId"
                                                                       value="${application.id}">
                                                                <button type="submit"
                                                                        class="btn btn-info btn-outline-primary">
                                                                    <fmt:message
                                                                            key="make_request.button"/>
                                                                </button>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="ml-1">
                                        <form action="controller?command=removeApplication" method="post">
                                            <input type="hidden" name="application_id"
                                                   value="${application.id}">
                                            <button type="submit"
                                                    class="btn btn-outline-secondary btn-sm">
                                                <i class="material-icons">delete_outline</i>
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </td>
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
                            <fmt:message key="table.th.booked_by"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.user_email"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.application_id"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.room_id"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.confirmrequest_date"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.status"/>
                        </th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="confirmrequest" items="${sessionScope.allConfirmRequests}">
                        <tr>
                            <td>${confirmrequest.bookedByUser}</td>
                            <td>${confirmrequest.bookedByUserEmail}</td>
                            <td>${confirmrequest.applicationId}</td>
                            <td>${confirmrequest.roomId}</td>
                            <td>${confirmrequest.confirmRequestDate}</td>
                            <td><htl:requestStatus request="${confirmrequest.status}"/></td>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <%-- Invoice --%>
            <div class="tab-pane fade active" id="invoice" role="tabpanel" aria-labelledby="invoice-tab">
                <table class="table table-hover mt-2">
                    <thead>
                    <tr>
                        <th scope="col">
                            <fmt:message key="table.th.booked_by"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.user_email"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.invoice_amount"/>, $
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.booking_id"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.invoice_date"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.status"/>
                        </th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="invoice" items="${sessionScope.allInvoices}">
                        <tr>
                            <td>${invoice.bookedByUser}</td>
                            <td>${invoice.bookedByUserEmail}</td>
                            <td>${invoice.amount}</td>
                            <td>${invoice.bookingId}</td>
                            <td>${invoice.invoiceDate}</td>
                            <td><htl:invoiceStatus invoiceStatus="${invoice.status}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <%--Add a room --%>
            <div class="tab-pane fade" id="new-room" role="tabpanel"
                 aria-labelledby="new-room-tab">
                <form class="mt-2" method="post" action="controller?command=addRoom">
                    <div class="form-group">
                        <div class="row">
                            <div class="col">
                                <select class="custom-select" name="roomSeats">
                                    <option value="single"><fmt:message key="sort.seats.single"/></option>
                                    <option value="double"><fmt:message key="sort.seats.double"/></option>
                                    <option value="twin"><fmt:message key="sort.seats.twin"/></option>
                                    <option value="triple"><fmt:message key="sort.seats.triple"/></option>
                                </select>
                            </div>

                            <div class="col">
                                <select class="custom-select" name="roomClass">
                                    <option value="standard"><fmt:message key="room.class.standard"/></option>
                                    <option value="business"><fmt:message key="room.class.business"/></option>
                                    <option value="lux"><fmt:message key="room.class.lux"/></option>
                                </select>
                            </div>
                            <div class="col">
                                <input type="text" name="roomNumber" class="form-control"
                                       placeholder="<fmt:message key="manager.modal.placeholder.number"/>"
                                       minlength="1"
                                       maxlength="40"
                                       required>
                            </div>
                            <div class="col">
                                <input type="number" name="price" class="form-control"
                                       step="0.01"
                                       placeholder="<fmt:message key="manager.modal.placeholder.price"/>"
                                       min="0" minlength="1"
                                       required>
                            </div>
                            <div class="col">
                                <select class="custom-select" name="roomStatus">
                                    <option value="available"><fmt:message key="sort.status.available"/></option>
                                    <option value="unavailable"><fmt:message key="sort.status.unavailable"/></option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <br>
                    <br>
                    <button type="submit" class="btn btn-info" >
                        <fmt:message key="manager.modal.button.save"/>
                    </button>

                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/components/scripts.jsp"/>

<script>
    (function () {
        setUrls("statusPaid", buildUrl(1, "paid"));
        setUrls("statusOngoing", buildUrl(1, "ongoing"));
        setUrls("statusFinished", buildUrl(1, "finished"));
        setUrls("statusNew", buildUrl(1, "new"));
        setUrls("statusCancelled", buildUrl(1, "cancelled"));
    })();

    function buildUrl(page, bookingStatus) {
        const result = new URL(window.location.href);
        result.searchParams.set("page", page);

        setOptionalSearchParam(result.searchParams, "bookingStatus", bookingStatus);

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



