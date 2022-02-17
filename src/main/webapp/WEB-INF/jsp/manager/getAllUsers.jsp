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
            <td>
                <c:if test="${user.country != null}">
                    <c:choose>
                        <c:when test="${user.country == 'Italy'}">
                            <fmt:message key="country.italy"/>
                        </c:when>
                        <c:when test="${user.country == 'UK'}">
                            <fmt:message key="country.uk"/>
                        </c:when>
                        <c:when test="${user.country == 'Ukraine'}">
                            <fmt:message key="country.ukraine"/>
                        </c:when>
                        <c:when test="${user.country == 'Poland'}">
                            <fmt:message key="country.poland"/>
                        </c:when>
                        <c:when test="${user.country == 'Germany'}">
                            <fmt:message key="country.germany"/>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="country"/>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <c:if test="${user.country == null}"><fmt:message key="country"/></c:if>
            </td>
            <td>
                <c:if test="${user.role != null}">
                    <c:choose>
                        <c:when test="${user.role == 'client'}">
                            <fmt:message key="role.client"/>
                        </c:when>
                        <c:when test="${user.role == 'manager'}">
                            <fmt:message key="role.manager"/>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="role"/>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <c:if test="${user.role == null}"><fmt:message key="role"/></c:if>
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>


<jsp:include page="/WEB-INF/components/scripts.jsp"/>


</body>
</html>