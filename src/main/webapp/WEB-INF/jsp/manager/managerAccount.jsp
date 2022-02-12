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
                            <fmt:message key="table.th.room_id"/>
                        </th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="booking" items="${sessionScope.allBookings}">
                        <tr>
                            <td>${booking.bookedByUser}</td>
                            <td>${booking.bookedByUserEmail}</td>
                            <td>${booking.checkinDate}</td>
                            <td>${booking.checkoutDate}</td>
                            <td>${booking.roomId}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
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
                            <td>${application.roomTypeBySeats}</td>
                            <td>${application.roomClass}</td>
                            <td>${application.checkinDate}</td>
                            <td>${application.checkoutDate}</td>
                            <td>
                                <div class="d-flex justify-content-end">
                                    <div>
                                        <button type="submit" class="btn btn-outline-secondary btn-sm"
                                                data-toggle="modal"
                                                data-target="#makeRequestModalCenter${application.id}">
                                            <i class="material-icons">Make request</i>
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
                                                            Make request
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
                                                                        class="btn btn-outline-primary">
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
            <div class="tab-pane fade active show" id="invoice" role="tabpanel" aria-labelledby="invoice-tab">
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
                        <%--                            <td>${invoice.status}</td>--%>
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
                                <input type="text" name="name" class="form-control"
                                       placeholder="<fmt:message key="manager.modal.placeholder.name"/>"
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
                                <select class="custom-select" name="serviceId">
                                    <c:forEach var="service" items="${services}">
                                        <option value="${service.id}">${service.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="description">
                            <fmt:message key="manager.modal.description"/>
                        </label>
                        <textarea class="form-control" id="description" name="description"
                                  rows="3" minlength="10"
                                  maxlength="250"
                                  required></textarea>
                    </div>
                    <button type="submit" class="btn btn-dark">
                        <fmt:message key="manager.modal.button.save"/>
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>


<jsp:include page="/WEB-INF/components/scripts.jsp"/>

</body>
</html>



