<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <!-- header.jsp -->
    <%@ include file="../commons/header.jsp" %>
</head>

<body>
<!-- body.jsp -->
<%@ include file="../commons/body.jsp" %>

<!-- navigation.jsp -->
<%@ include file="../commons/navigation.jsp" %>

<!-- container -->
<section class="container">
    <form method="get" action="${pageContext.request.contextPath}/review" class="form-inline mt-3">
        <select name="category" class="form-control mx-1 mt-2">
            <option value="전체" ${param.category == '전체' ? 'selected' : ''}>전체</option>
            <option value="문학" ${param.category == '문학' ? 'selected' : ''}>문학</option>
            <option value="사회" ${param.category == '사회' ? 'selected' : ''}>사회</option>
            <option value="과학" ${param.category == '과학' ? 'selected' : ''}>과학</option>
            <option value="예술" ${param.category == '예술' ? 'selected' : ''}>예술</option>
            <option value="역사" ${param.category == '역사' ? 'selected' : ''}>역사</option>
            <option value="언어(어학)" ${param.category == '언어(어학)' ? 'selected' : ''}>언어(어학)</option>
            <option value="기타" ${param.category == '기타' ? 'selected' : ''}>기타</option>
        </select>
        <select name="searchType" class="form-control mx-1 mt-2">
            <option value="최신순" ${param.searchType == '최신순' ? 'selected' : ''}>최신순</option>
            <option value="조회수순" ${param.searchType == '조회수순' ? 'selected' : ''}>조회수순</option>
            <option value="추천순" ${param.searchType == '추천순' ? 'selected' : ''}>추천순</option>
        </select>
        <input type="text" name="search" class="form-control mx-1 mt-2" placeholder="내용을 입력해주세요." value="${param.search}">
        <button type="submit" class="btn btn-dark mx-1 mt-2">검색</button>
        <div class="ml-auto">
            <a class="btn btn-primary mx-1 mt-2" href="${pageContext.request.contextPath}/review/regist">작성하기</a>
        </div>
    </form>

    <div class="row mt-3">
        <c:forEach var="review" items="${reviewList.content}" varStatus="status">
        <div class="col-md-6 mb-3" onclick="window.location.href='${pageContext.request.contextPath}/review/post/${review.reviewID}'" style="cursor: pointer;">
            <div class="card bg-light">
                <div class="card-header bg-light">
                    <div class="row">
                        <div class="col-8 text-left">
                            <h5><small>[서명]</small><b><c:choose>
                                    <c:when test="${fn:length(review.bookName) > 18}">
                                        ${fn:substring(review.bookName, 0, 18)}...
                                    </c:when>
                                    <c:otherwise>
                                        ${review.bookName}
                                    </c:otherwise>
                                </c:choose></b>
                            </h5>
                            <p><small>-<c:choose>
                                    <c:when test="${fn:length(review.authorName) > 23}">
                                        ${fn:substring(review.authorName, 0, 23)}...
                                    </c:when>
                                    <c:otherwise>
                                        ${review.authorName}
                                    </c:otherwise>
                                </c:choose></small></p>
                        </div>
                        <div class="col-4 text-right">
                            <img class="" src="${pageContext.request.contextPath}/images/icon.png" style="height:15px;"><span> ${review.member.memberID}</span>
                        </div>
                    </div>
                </div>
                <div class="card-body bg-white">
                    <h5 class="card-title font-weight-bold"><c:choose>
                                    <c:when test="${fn:length(review.reviewTitle) > 35}">
                                        ${fn:substring(review.reviewTitle, 0, 35)}...
                                    </c:when>
                                    <c:otherwise>
                                        ${review.reviewTitle}
                                    </c:otherwise>
                                </c:choose></h5>
                    <p class="date review-date" data-post-date="${review.registDate}" style="color: #6c757d;"></p>
                    <p class="card-text">
                        <c:choose>
                            <c:when test="${fn:length(review.reviewContent) > 40}">
                                ${fn:substring(review.reviewContent, 0, 40)}...
                            </c:when>
                            <c:otherwise>
                                ${review.reviewContent}
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <div class="row">
                        <div class="col-9 text-left">
                            평점
                            <span>
                                <c:forEach var="i" begin="1" end="5">
                                    <c:choose>
                                        <c:when test="${i <= review.reviewScore}">
                                            ★
                                        </c:when>
                                        <c:otherwise>
                                            ☆
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </span>
                        </div>
                        <div class="col-3 text-right">
                            추천(${review.likeCount})
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${status.index % 2 == 1 && !status.last}">
    </div>
    <div class="row mt-3">
        </c:if>
        </c:forEach>
    </div>

<!-- 페이지네이션 -->
    <nav class="mt-5">
        <ul class="pagination justify-content-center">
            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                <a class="page-link" href="?page=${currentPage - 1}&size=${size}&category=${param.category}&searchType=${param.searchType}&search=${param.search}" tabindex="-1">이전</a>
            </li>
            <c:forEach begin="1" end="${reviewList.totalPages}" var="i">
                <li class="page-item ${currentPage == i ? 'active' : ''}">
                    <a class="page-link" href="?page=${i}&size=${size}&category=${param.category}&searchType=${param.searchType}&search=${param.search}">${i}</a>
                </li>
            </c:forEach>
            <li class="page-item ${currentPage == reviewList.totalPages ? 'disabled' : ''}">
                <a class="page-link" href="?page=${currentPage + 1}&size=${size}&category=${param.category}&searchType=${param.searchType}&search=${param.search}">다음</a>
            </li>
        </ul>
    </nav>
</section>

<%-- 날짜 서식 --%>
<script>
    $(document).ready(function() {
        $('.review-date').each(function() {
            var postDate = $(this).data('post-date');
            var postDateObj = new Date(postDate);
            var now = new Date();

            var isToday = postDateObj.toDateString() === now.toDateString();

            var formattedDate = isToday ?
                postDateObj.getHours() + ':' + (postDateObj.getMinutes() < 10 ? '0' : '') + postDateObj.getMinutes() :
                postDateObj.getFullYear() + '.' + (postDateObj.getMonth() + 1) + '.' + postDateObj.getDate();

            $(this).text(formattedDate);
        });
    });
</script>

<!-- footer.jsp -->
<%@ include file="../commons/footer.jsp" %>
</body>
</html>
