<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<ul class="pagination">
    <li class="page-item ${requestScope.page > 1 ? "" : " disabled"}">
        <a class="page-link" href="#" aria-label="Prev" id="prevPage">
            <span aria-hidden="true">&laquo;</span>
            <span class="sr-only">&laquo;</span>
        </a>
    </li>

    <c:if test="${requestScope.page - 3 > 1 || (requestScope.page> 1 && requestScope.page <= 4)}">
        <li class="page-item"><a class="page-link" href="#" id="firstPage">1</a></li>
    </c:if>
    <c:if test="${requestScope.page - 3 > 1}">
        <li class="page-item disabled"><a class="page-link" href="#">...</a></li>
    </c:if>

    <c:if test="${requestScope.page - 2 > 1}">
        <li class="page-item"><a class="page-link" href="#" id="pageMin2">${requestScope.page - 2}</a>
        </li>
    </c:if>

    <c:if test="${requestScope.page - 1 > 1}">
        <li class="page-item"><a class="page-link" href="#" id="pageMin1">${requestScope.page - 1}</a>
        </li>
    </c:if>

    <li class="page-item active"><a class="page-link" href="#">${requestScope.page}</a></li>

    <c:if test="${requestScope.page + 1 <= requestScope.pageCount}">
        <li class="page-item"><a class="page-link" href="#" id="pagePlus1">${requestScope.page + 1}</a>
        </li>
    </c:if>
    <c:if test="${requestScope.page + 2 <= requestScope.pageCount}">
        <li class="page-item"><a class="page-link" href="#" id="pagePlus2">${requestScope.page + 2}</a>
        </li>
    </c:if>

    <c:if test="${requestScope.page + 4 <= requestScope.pageCount}">
        <li class="page-item"><a class="page-link" href="#">...</a>
        </li>
    </c:if>
    <c:if test="${requestScope.page + 3 <= requestScope.pageCount && requestScope.pageCount >= 4}">
        <li class="page-item"><a class="page-link" href="#" id="lastPage">${requestScope.pageCount}</a>
        </li>
    </c:if>

    <li class="page-item ${requestScope.page < requestScope.pageCount ? "": " disabled"}">
        <a class="page-link" href="#" aria-label="Next" id="nextPage">
            <span aria-hidden="true">&raquo;</span>
            <span class="sr-only">&raquo;</span>
        </a>
    </li>
</ul>
