<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/page.jspf" %>

<%@ taglib prefix="htl" tagdir="/WEB-INF/tags" %>

<!doctype html>
<html>
<c:set var="title" value="Client's desk" scope="page"/>
<jsp:include page="/WEB-INF/components/head.jsp"/>


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
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="booking" items="${sessionScope.myBookings}">
                        <tr>
                            <td>${booking.checkinDate}</td>
                            <td>${booking.checkoutDate}</td>
                            <td>${booking.roomTypeBySeats}</td>
                            <td>${booking.roomClass}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
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
                            <td>${application.roomTypeBySeats}</td>
                            <td>${application.roomClass}</td>
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
                            <td>${confirmrequest.roomTypeBySeats}</td>
                            <td>${confirmrequest.roomClass}</td>
                            <td>${confirmrequest.checkinDate}</td>
                            <td>${confirmrequest.checkoutDate}</td>
                            <td>${confirmrequest.applicationId}</td>
                            <td><htl:requestStatus request="${confirmrequest.status}"/></td>
                            <td>
                                <div class="ml-1">
                                    <form action="controller?command=confirmRequest" method="post">
                                        <input type="hidden" name="confirmRequest_id"
                                               value="${confirmrequest.id}">
                                        <button class="ui-button" type="submit"><fmt:message key="confirm.button"/></button>
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
                                        <a class="nav-link" href="controller?command=paymentPage&invoiceId=${invoice.id}">

                                            <button class="ui-button" type="submit"><fmt:message key="pay.button"/></button>
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

<jsp:include page="/WEB-INF/components/scripts.jsp"/>

</body>
</html>

