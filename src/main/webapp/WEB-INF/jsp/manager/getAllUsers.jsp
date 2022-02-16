<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<html>


<c:set var="title" value="All users list" scope="page"/>
<jsp:include page="/WEB-INF/components/head.jsp"/>
<body>
<jsp:include page="/WEB-INF/components/managerMenu.jsp"/>

<h1><fmt:message key="all.users"/></h1>

<table class="table table-hover mt-2 center">
    <thead>
    <tr>
        <th scope="col">
            <fmt:message key="table.th.user.id"/>
        </th>
        <th scope="col">
            <fmt:message key="table.th.user.first_name"/>
        </th>
        <th scope="col">
            <fmt:message key="table.th.user.last_name"/>
        </th>
        <th scope="col">
            <fmt:message key="table.th.user_email"/>
        </th>
        <th scope="col">
            <fmt:message key="table.th.user.country"/>
        </th>
        <th scope="col">
            <fmt:message key="table.th.user.role"/>
        </th>

    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${sessionScope.usersList}">
        <tr>
            <td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.email}</td>
            <td>${user.country}</td>
            <td>${user.role}</td>

        </tr>
    </c:forEach>
    </tbody>
</table>


<jsp:include page="/WEB-INF/components/scripts.jsp"/>


</body>
</html>