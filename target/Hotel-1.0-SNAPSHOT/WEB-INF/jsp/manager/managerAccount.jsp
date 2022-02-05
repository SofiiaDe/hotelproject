<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/page.jspf" %>

<!doctype html>
<html>
<c:set var="title" value="Manager's desk" scope="page"/>
<jsp:include page="/WEB-INF/templates/head.jsp"/>
<body>
<%--<jsp:include page="/WEB-INF/templates/managerMenu.jsp"></jsp:include>--%>
<jsp:include page="/WEB-INF/templates/managerMenu.jsp"/>
<div class="container">
    <div class="tab-pane fade show active" id="v-pills-services" role="tabpanel"
         aria-labelledby="v-pills-services-tab">
        <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item">
                <a class="nav-link active" id="internet-tab" data-toggle="tab" href="#internet"
                   role="tab"
                   aria-controls="internet" aria-selected="true">
                    <fmt:message key="main.tab.room"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="iptv-tab" data-toggle="tab" href="#iptv" role="tab"
                   aria-controls="iptv"
                   aria-selected="false">
                    <fmt:message key="main.tab.applications"/>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" id="new-tariff-tab" data-toggle="tab" href="#new-tariff"
                   role="tab"
                   aria-controls="new-tariff" aria-selected="false">
                    <fmt:message key="main.tab.new_room"/>
                </a>
            </li>
        </ul>
        <div class="tab-content" id="myTabContent">
            <%-- Интернет --%>
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
                    <tbody>
                    <c:forEach var="intTariff" items="${internetTariffs}">
                        <tr>
                            <td>${intTariff.name}</td>
                            <td>${intTariff.price}</td>
                            <td>${intTariff.description}</td>
                            <td>
                                <div class="d-flex justify-content-end">
                                    <div>
                                        <button type="submit"
                                                class="btn btn-outline-secondary btn-sm"
                                                data-toggle="modal"
                                                data-target="#editInetModalCenter${intTariff.id}">
                                            <i class="material-icons">create</i>
                                        </button>
                                        <!-- Modal -->
                                        <div class="modal fade bd-example-modal-lg"
                                             id="editInetModalCenter${intTariff.id}"
                                             tabindex="-1"
                                             role="dialog"
                                             aria-labelledby="editInetModalCenterTitle"
                                             aria-hidden="true">
                                            <div class="modal-dialog modal-dialog-centered modal-lg"
                                                 role="document">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title"
                                                            id="editInetModalCenterTitle">
                                                                ${intTariff.name}
                                                        </h5>
                                                        <button type="button" class="close"
                                                                data-dismiss="modal"
                                                                aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <form method="post"
                                                              action="controller?action=edit_tariff">
                                                            <div class="form-group">
                                                                <div class="row">
                                                                    <div class="col">
                                                                        <input type="text"
                                                                               name="name"
                                                                               class="form-control"
                                                                               placeholder="Название"
                                                                               value="${intTariff.name}"
                                                                               minlength="1"
                                                                               maxlength="40"
                                                                               required>
                                                                    </div>
                                                                    <div class="col">
                                                                        <input type="number"
                                                                               step="0.01"
                                                                               name="price"
                                                                               class="form-control"
                                                                               placeholder="Стоимость грн./мес."
                                                                               value="${intTariff.price}"
                                                                               min="0" minlength="1"
                                                                               required>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group">
                                                                <label for="description">
                                                                    <fmt:message
                                                                            key="main.modal.description"/>
                                                                </label>
                                                                <textarea class="form-control"
                                                                          name="description"
                                                                          rows="3" minlength="10"
                                                                          maxlength="250"
                                                                          required>${intTariff.description}
                                                                </textarea>
                                                            </div>
                                                            <input type="hidden" name="tariff_id"
                                                                   value="${intTariff.id}">
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
                                        <form action="controller?action=remove_tariff"
                                              method="post">
                                            <input type="hidden" name="tariff_id"
                                                   value="${intTariff.id}">
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
            <%-- IPTV --%>
            <div class="tab-pane fade" id="iptv" role="tabpanel" aria-labelledby="iptv-tab">
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
                    <tbody>
                    <c:forEach var="iptvTariff" items="${iptvTariffs}">
                    <tr>
                        <td>${iptvTariff.name}</td>
                        <td>${iptvTariff.price}</td>
                        <td>${iptvTariff.description}</td>
                        <td>
                            <div class="d-flex justify-content-end">
                                <div>
                                    <button type="submit" class="btn btn-outline-secondary btn-sm"
                                            data-toggle="modal"
                                            data-target="#editIPTVModalCenter${iptvTariff.id}">
                                        <i class="material-icons">create</i>
                                    </button>
                                    <!-- Modal -->
                                    <div class="modal fade bd-example-modal-lg"
                                         id="editIPTVModalCenter${iptvTariff.id}"
                                         tabindex="-1"
                                         role="dialog"
                                         aria-labelledby="editIPTVModalCenterTitle"
                                         aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-centered modal-lg"
                                             role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title"
                                                        id="editIPTVModalCenterTitle">
                                                            ${iptvTariff.name}
                                                    </h5>
                                                    <button type="button" class="close"
                                                            data-dismiss="modal"
                                                            aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    <form method="post"
                                                          action="controller?action=edit_tariff">
                                                        <div class="form-group">
                                                            <div class="row">
                                                                <div class="col">
                                                                    <input type="text" name="name"
                                                                           class="form-control"
                                                                           placeholder="Название"
                                                                           value="${iptvTariff.name}"
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
                                                                           value="${iptvTariff.price}"
                                                                           min="0" minlength="1"
                                                                           required>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="description">
                                                                <fmt:message
                                                                        key="main.modal.description"/>
                                                            </label>
                                                            <textarea class="form-control"
                                                                      name="description"
                                                                      rows="3" minlength="10"
                                                                      maxlength="250"
                                                                      required>${iptvTariff.description}</textarea>
                                                        </div>
                                                        <input type="hidden" name="tariff_id"
                                                               value="${iptvTariff.id}">
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
                                               value="${iptvTariff.id}">
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

            <%--Add a room --%>
            <div class="tab-pane fade" id="new-tariff" role="tabpanel"
                 aria-labelledby="new-tariff-tab">
                <form class="mt-2" method="post" action="controller?action=add_tariff">
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



