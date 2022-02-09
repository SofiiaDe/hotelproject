<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/page.jspf" %>

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <%--<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />--%>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <!-- Material CSS -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
          rel="stylesheet">
    <title> ${title}</title>
</head>

<fmt:setLocale value="${requestScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>

<html lang="${requestScope.lang}" class="h-100">