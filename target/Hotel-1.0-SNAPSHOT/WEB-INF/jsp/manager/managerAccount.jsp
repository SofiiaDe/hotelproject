<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/page.jspf" %>

<!doctype html>
<html>
<c:set var="title" value="Manager's desk" scope="page"/>
<jsp:include page="/WEB-INF/templates/head.jsp"/>
<body>
<jsp:include page="/WEB-INF/templates/managerMenu.jsp"/>
<div class="container">
    <div class="tab-pane fade show active" id="v-pills-services" role="tabpanel"
         aria-labelledby="v-pills-services-tab">
        <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item">
                <a class="nav-link active" id="booking-tab" data-toggle="tab" href="#booking"
                   role="tab"
                   aria-controls="booking" aria-selected="true">
                    <fmt:message key="main.tab.bookings"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="application-tab" data-toggle="tab" href="#application" role="tab"
                   aria-controls="application"
                   aria-selected="false">
                    <fmt:message key="main.tab.applications"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="confirmrequest-tab" data-toggle="tab" href="#confirmrequest" role="tab"
                   aria-controls="confirmrequest"
                   aria-selected="false">
                    <fmt:message key="main.tab.confirmation_requests"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="invoice-tab" data-toggle="tab" href="#invoice" role="tab"
                   aria-controls="invoice"
                   aria-selected="false">
                    <fmt:message key="main.tab.invoices"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="new-room-tab" data-toggle="tab" href="#new-room"
                   role="tab"
                   aria-controls="new-room" aria-selected="false">
                    <fmt:message key="main.tab.new_room"/>
                </a>
            </li>
        </ul>

        <div class="tab-content" id="myTabContent">

            <%-- Application --%>
<%--            <form method="post" action="controller?command=showAllApplications">--%>
                <div class="tab-pane fade" id="application" role="tabpanel" aria-labelledby="application-tab">
                    <table class="table table-hover mt-2">
                        <thead>
                        <tr>
                            <th scope="col">
                                <fmt:message key="table.th.application_id"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="table.th.user_id"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="table.th.room_seats"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="table.th.room_class"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="table.th.checkin_date"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="table.th.checkout_date"/>
                            </th>
                            <th scope="col"></th>
                        </tr>
                        </thead>
                        <tbody>
<%--                        <form action="controller?command=showAllApplications">--%>

                        <c:forEach var="application" items="${sessionScope.allApplications}">
                            <tr>
                                <td>${application.id}</td>
                                <td>${application.userId}</td>
                                <td>${application.roomTypeBySeats}</td>
                                <td>${application.roomClass}</td>
<%--                                <td>${application.checkinDate}</td>--%>
<%--                                <td>${application.checkoutDate}</td>--%>

                            </tr>
                        </c:forEach>
<%--                        </form>--%>
                        </tbody>
                    </table>
                </div>
<%--            </form>--%>

            <%--Add a room --%>
            <div class="tab-pane fade" id="new-room" role="tabpanel"
                 aria-labelledby="new-room-tab">
                <form class="mt-2" method="post" action="controller?command=addRoom">
                    <div class="form-group">
                        <div class="row">
                            <div class="col">
                                <input type="text" name="name" class="form-control"
                                       placeholder="<fmt:message key="main.modal.placeholder.name"/>"
                                       minlength="1"
                                       maxlength="40"
                                       required>
                            </div>
                            <div class="col">
                                <input type="number" name="price" class="form-control"
                                       step="0.01"
                                       placeholder="<fmt:message key="main.modal.placeholder.price"/>"
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
                            <fmt:message key="main.modal.description"/>
                        </label>
                        <textarea class="form-control" id="description" name="description"
                                  rows="3" minlength="10"
                                  maxlength="250"
                                  required></textarea>
                    </div>
                    <button type="submit" class="btn btn-dark">
                        <fmt:message key="main.modal.button.save"/>
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>


<jsp:include page="/WEB-INF/templates/scripts.jsp"/>

</body>
</html>



