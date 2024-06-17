<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../commons/header.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/recommended.css">
</head>
<body>
<%@ include file="../commons/body.jsp" %>
<%@ include file="../commons/navigation.jsp" %>

<section class="container recommended-container mt-3 mb-5">
    <span class="text-left" id="pageInfo"><h2>사서 추천 도서</h2></span>
    <div class="filter-section">
        <div>
            전체 <c:out value="${totalCount}"/> 건
        </div>
    </div>

    <form method="get" action="${pageContext.request.contextPath}/library/recommended" class="form-inline">
        <select name="kdcName" id="kdcName" class="form-control mt-2 mx-1">
            <option value="전체" ${param.kdcName == '전체' ? 'selected' : ''}>전체</option>
            <option value="문학" ${param.kdcName == '문학' ? 'selected' : ''}>문학</option>
            <option value="인문과학" ${param.kdcName == '인문과학' ? 'selected' : ''}>인문과학</option>
            <option value="사회과학" ${param.kdcName == '사회과학' ? 'selected' : ''}>사회과학</option>
            <option value="자연과학" ${param.kdcName == '자연과학' ? 'selected' : ''}>자연과학</option>
            <option value="기타" ${param.kdcName == '기타' ? 'selected' : ''}>기타</option>
        </select>
        <select name="year" id="year" class="form-control mt-2 mx-1">
            <option value="" ${param.year == '' ? 'selected' : ''}>전체</option>
            <c:forEach var="i" begin="2010" end="2024">
                <option value="${i}" ${param.year == i ? 'selected' : ''}>${i}</option>
            </c:forEach>
        </select>
        <button type="submit" name="search" class="btn btn-dark mx-1 mt-2">보기</button>
    </form>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <c:if test="${not empty message}">
        <div class="message">${message}</div>
    </c:if>
    <c:if test="${not empty recommendedBooks}">

        <ul class="book-list">
            <c:forEach var="book" items="${recommendedBooks}" varStatus="status">
                <li class="book-item">
                    <img src="${book.recomfilepath}" alt="${book.recomtitle}" style="width: 150px; height: auto;">
                    <div class="book-details">
                        <strong>${book.recomtitle}</strong>
                        <p>저자: ${book.recomauthor}</p>
                        <p>출판사: ${book.recompublisher}</p>
                        <p>ISBN: ${book.recomisbn}</p>
                        <p>발행년도: ${book.publishYear}</p>
                        <p>추천 연도: ${book.recomYear}</p>
                        <p>추천 월: ${book.recomMonth}</p>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </c:if>

    <c:if test="${empty recommendedBooks}">
        <div class="message">추천 도서가 없습니다.</div>
    </c:if>

    <%-- 페이지 네이션 --%>
    <c:if test="${totalPages > 1}">
        <div class="pagination justify-content-center text-center">
            <c:choose>
                <c:when test="${currentPage > 1}">
                    <a href="${pageContext.request.contextPath}/library/recommended?year=${param.year}&kdcName=${param.kdcName}&page=1&pageSize=${pageSize}">&laquo;</a>
                    <a href="${pageContext.request.contextPath}/library/recommended?year=${param.year}&kdcName=${param.kdcName}&page=${currentPage - 1}&pageSize=${pageSize}">&lt;</a>
                </c:when>
                <c:otherwise>
                    <span class="disabled">&laquo;</span>
                    <span class="disabled">&lt;</span>
                </c:otherwise>
            </c:choose>

            <c:forEach begin="${startPage}" end="${endPage}" var="i">
                <a href="${pageContext.request.contextPath}/library/recommended?year=${param.year}&kdcName=${param.kdcName}&page=${i}&pageSize=${pageSize}"
                   class="${i == currentPage ? 'active' : ''}">${i}</a>
            </c:forEach>

            <c:choose>
                <c:when test="${currentPage < totalPages}">
                    <a href="${pageContext.request.contextPath}/library/recommended?year=${param.year}&kdcName=${param.kdcName}&page=${currentPage + 1}&pageSize=${pageSize}">&gt;</a>
                    <a href="${pageContext.request.contextPath}/library/recommended?year=${param.year}&kdcName=${param.kdcName}&page=${totalPages}&pageSize=${pageSize}">&raquo;</a>
                </c:when>
                <c:otherwise>
                    <span class="disabled">&gt;</span>
                    <span class="disabled">&raquo;</span>
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>
</section>

<%@ include file="../commons/footer.jsp" %>
</body>
</html>
