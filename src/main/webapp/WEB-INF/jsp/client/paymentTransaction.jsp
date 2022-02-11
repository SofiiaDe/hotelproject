<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/page.jspf" %>


<!doctype html>
<html>
<c:set var="title" value="Simulate payment transaction" scope="page"/>
<jsp:include page="/WEB-INF/components/head.jsp"/>
<body>
<jsp:include page="/WEB-INF/components/clientMenu.jsp"/>
<h2><fmt:message key="payment.header.confirm_payment"/></h2>

<div class="ml-1">
    <form action="controller?command=payInvoice" method="post">
<%--        var news=<%= request.getParameter("invoice_id") %>--%>
<%--${param.invoice_id}--%>
        <input type="hidden" name="pay_invoice"
               value="pay_invoice">

        <button class="ui-button" type="submit"><fmt:message key="button.confirm.payment.button"/></button>

    </form>
</div>
</div>

<jsp:include page="/WEB-INF/components/scripts.jsp"/>

</body>
</html>
