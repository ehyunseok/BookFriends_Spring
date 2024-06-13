<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <!-- header.jsp -->
    <%@ include file="../commons/header.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/search.css">
</head>
<body>
<!-- body.jsp -->
<%@ include file="../commons/body.jsp" %>

<!-- navigation.jsp -->
<%@ include file="../commons/navigation.jsp" %>

<!-- container  -->
<section class="container search-container mt-3 mb-5">
    <span class="text-left" id="pageInfo"><h2>국립중앙도서관 소장자료 검색</h2></span>
    <div class="row">
        <form action="${pageContext.request.contextPath}/library/search" method="get" class="col-sm-12">
            <input type="text" name="query" class="form-control" placeholder="검색어를 입력하세요" required>
            <button type="submit" class="btn btn-dark col-sm-1">검색</button>
            <input type="hidden" name="page" value="1">
        </form>
    </div>
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <c:if test="${not empty message}">
        <div class="message">${message}</div>
    </c:if>

    <c:if test="${not empty books}">
        <p class="text-right mt-3 mb-0">${totalBooks}개의 검색 결과</p>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">No</th>
                    <th scope="col">제목</th>
                    <th scope="col">저자</th>
                    <th scope="col">발행자</th>
                    <th scope="col">자료있는곳</th>
                    <th scope="col">청구기호</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="book" items="${books}" varStatus="status">
                    <tr>
                        <td>${(page - 1) * pageSize + status.index + 1}</td>
                        <td>${book.title}</td>
                        <td>${book.author}</td>
                        <td>${book.publisher}</td>
                        <td>${book.placeInfo}</td>
                        <td>${book.callNumber}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="pagination">
            <c:choose>
                <c:when test="${page > 1}">
                    <a href="?query=${query}&page=1">&laquo;</a>
                </c:when>
                <c:otherwise>
                    <a class="disabled">&laquo;</a>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${page > 10}">
                    <a href="?query=${query}&page=${startPage - 10}">&lt;</a>
                </c:when>
                <c:otherwise>
                    <a class="disabled">&lt;</a>
                </c:otherwise>
            </c:choose>
            <c:forEach var="i" begin="${startPage}" end="${endPage}">
                <a href="?query=${query}&page=${i}" class="${page == i ? 'active' : ''}">${i}</a>
            </c:forEach>
            <c:choose>
                <c:when test="${endPage < totalPages}">
                    <a href="?query=${query}&page=${startPage + 10}">&gt;</a>
                </c:when>
                <c:otherwise>
                    <a class="disabled">&gt;</a>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${page < totalPages}">
                    <a href="?query=${query}&page=${totalPages}">&raquo;</a>
                </c:when>
                <c:otherwise>
                    <a class="disabled">&raquo;</a>
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>
</section>
<script>
    console.log('query=' + ${query});
</script>
<!-- footer.jsp -->
<%@ include file="../commons/footer.jsp" %>
</body>
</html>
