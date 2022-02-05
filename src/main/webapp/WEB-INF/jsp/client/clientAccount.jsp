<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/page.jspf" %>

<!doctype html>
<html>
<c:set var="title" value="Client's desk" scope="page"/>
<jsp:include page="/WEB-INF/templates/head.jsp"/>
<body>
<jsp:include page="/WEB-INF/templates/clientMenu.jsp"/>
<div class="container">
    <div class="tab-pane fade show active" id="v-pills-services" role="tabpanel"
         aria-labelledby="v-pills-services-tab">
        <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item">
                <a class="nav-link active" id="internet-tab" data-toggle="tab" href="#internet"
                   role="tab"
                   aria-controls="internet" aria-selected="true">
                    <fmt:message key="main.tab.user_bookings"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="applications-tab" data-toggle="tab" href="#application" role="tab"
                   aria-controls="applications"
                   aria-selected="false">
                    <fmt:message key="main.tab.user_applications"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="confirmrequests-tab" data-toggle="tab" href="#confirmrequests" role="tab"
                   aria-controls="confirmrequests"
                   aria-selected="false">
                    <fmt:message key="main.tab.user_confirmation_requests"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="invoices-tab" data-toggle="tab" href="#invoices" role="tab"
                   aria-controls="invoices"
                   aria-selected="false">
                    <fmt:message key="main.tab.user_invoices"/>
                </a>
            </li>

        </ul>
        <div class="tab-content" id="myTabContent">
            <%-- My bookings --%>
            <div class="tab-pane fade show active" id="internet" role="tabpanel"
                 aria-labelledby="internet-tab">
                <table class="table table-hover mt-2">
                    <thead>
                    <tr>
                        <th scope="col">
                            <fmt:message key="table.th.checkin_date"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.checkout_date"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.room_number"/>
                        </th>
                        <th scope="col"></th>
                    </tr>
                    </thead>

                    <%--                   !!! Paste code for bookings table here--%>


                </table>
            </div>


            <%-- My applications --%>
            <div class="tab-pane fade" id="application" role="tabpanel" aria-labelledby="applications-tab">
                <table class="table table-hover mt-2">
                    <thead>
                    <tr>
                        <th scope="col">
                            <fmt:message key="table.th.checkin_date"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.checkout_date"/>
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
                    <c:forEach var="userApplication" items="${sessionScope.myApplications}">
                    <tr>
                        <td>${userApplication.checkinDate}</td>
                        <td>${userApplication.checkoutDate}</td>
                        <td>${userApplication.roomTypeBySeats}</td>
                        <td>${userApplication.roomClass}</td>
                        <td>
                            <div class="d-flex justify-content-end">
                                <div>
                                    <button type="submit" class="btn btn-outline-secondary btn-sm"
                                            data-toggle="modal"
                                            data-target="#editApplicationModalCenter${userApplication.id}">
                                        <i class="material-icons">create</i>
                                    </button>
                                    <!-- Modal -->
                                    <div class="modal fade bd-example-modal-lg"
                                         id="editApplicationModalCenter${userApplication.id}"
                                         tabindex="-1"
                                         role="dialog"
                                         aria-labelledby="editApplicationModalCenterTitle"
                                         aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-centered modal-lg"
                                             role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title"
                                                        id="editApplicationModalCenterTitle">
                                                            ${userApplication.checkinDate}
                                                    </h5>
                                                    <button type="button" class="close"
                                                            data-dismiss="modal"
                                                            aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    <form method="post"
                                                          action="controller?command=editApplication">
                                                        <div class="form-group">
                                                            <div class="row">
                                                                <div class="col">
                                                                    <input type="text" name="name"
                                                                           class="form-control"
                                                                           placeholder="Название"
                                                                           value="${userApplication.checkinDate}"
                                                                           minlength="1"
                                                                           maxlength="40"
                                                                           required>
                                                                </div>
                                                                <div class="col">
                                                                    <input type="number"
                                                                           name="price"
                                                                           step="0.01"
                                                                           class="form-control"
                                                                           placeholder="Стоимость"
                                                                           value="${userApplication.checkoutDate}"
                                                                           min="0" minlength="1"
                                                                           required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                                <%--                                                            <label for="roomTypeBySeats">--%>
                                                                <%--                                                            <label for="description">--%>
                                                                <%--                                                                <fmt:message--%>
                                                                <%--                                                                        key="main.modal.description"/>--%>
                                                                <%--                                                            </label>--%>
                                                            <textarea class="form-control"
                                                                      name="description"
                                                                      rows="3" minlength="10"
                                                                      maxlength="250"
                                                                      required>${userApplication.roomTypeBySeats}</textarea>
                                                        </div>
                                                        <input type="hidden" name="tariff_id"
                                                               value="${userApplication.id}">
                                                        <div class="d-flex justify-content-end">
                                                            <button type="submit"
                                                                    class="btn btn-outline-primary">
                                                                <fmt:message
                                                                        key="main.modal.button.save"/>
                                                            </button>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="ml-1">
                                    <form action="controller?action=remove_tariff" method="post">
                                        <input type="hidden" name="tariff_id"
                                               value="${userApplication.id}">
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
                </table>
            </div>


            <%-- Confirmation requests --%>
            <div class="tab-pane fade" id="confirmrequests" role="tabpanel" aria-labelledby="confirmrequests-tab">
                <table class="table table-hover mt-2">
                    <thead>
                    <tr>
                        <th scope="col">
                            <fmt:message key="table.th.application_id"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.checkin_date"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.checkout_date"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.room_seats"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.room_class"/>
                        </th>
                        <th scope="col">
                            <fmt:message key="table.th.confirmrequest_status"/>
                        </th>
                        <th scope="col"></th>
                    </tr>
                    </thead>

                </table>
            </div>


                <%-- Invoices --%>
                <div class="tab-pane fade" id="invoices" role="tabpanel" aria-labelledby="invoices-tab">
                    <table class="table table-hover mt-2">
                        <thead>
                        <tr>
                            <th scope="col">
                                <fmt:message key="table.th.invoice_date"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="table.th.invoice_amount"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="table.th.booking_id"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="table.th.room_number"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="table.th.invoice_status"/>
                            </th>
                            <th scope="col"></th>
                        </tr>
                        </thead>

                    </table>


        </div>

    </div>

    <jsp:include page="/WEB-INF/templates/scripts.jsp"/>

</body>
</html>

