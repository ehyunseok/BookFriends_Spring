<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.daney.bookfriends.util.StringUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
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

<!-- container  -->
<section class="container mt-3 mb-5">
    <span class="text-left" id="pageInfo"><h2>독서모임</h2></span>
    <form method="get" action="${pageContext.request.contextPath}/recruit" class="form-inline mt-1">
        <select name="recruitStatus" class="form-control mx-1 mt-2">
            <option value="전체" ${param.recruitStatus == '전체' ? 'selected' : ''}>전체</option>
            <option value="모집중" ${param.recruitStatus == '모집중' ? 'selected' : ''}>모집중</option>
            <option value="모집완료" ${param.recruitStatus == '모집완료' ? 'selected' : ''}>모집완료</option>
        </select>
        <select name="searchType" class="form-control mx-1 mt-2">
            <option value="최신순" ${param.searchType == '최신순' ? 'selected' : ''}>최신순</option>
            <option value="조회수순" ${param.searchType == '조회수순' ? 'selected' : ''}>조회수순</option>
        </select>
        <input type="text" name="search" class="form-control mx-1 mt-2" placeholder="내용을 입력해주세요." value="${param.search}">
        <button type="submit" class="btn btn-dark mx-1 mt-2">검색</button>
        <div class="ml-auto">
            <a class="btn btn-primary mx-1 mt-2" href="${pageContext.request.contextPath}/recruit/regist">작성하기</a>
        </div>
    </form>
    <div class="card bg-light mt-3">
        <table class="table table-hover">
            <tbody>
                <c:forEach var="recruit" items="${recruits.content}">
                    <tr onclick="window.location.href='${pageContext.request.contextPath}/recruit/post/${recruit.recruitID}'" style="cursor: pointer;">
                        <td>
                            <c:choose>
                                <c:when test="${'모집중' == recruit.recruitStatus}">
                                    <span class="badge badge-success p-2">모집중</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-secondary p-2">모집완료</span>
                                </c:otherwise>
                            </c:choose>
                            <span>${recruit.recruitTitle}</span>
                            <br><span class="ml-3" id="text-ellipsis">
                                    <small>
                                    <c:set var="content" value="${StringUtil.stripHtml(recruit.recruitContent)}"/>
                                    <c:choose>
                                        <c:when test="${fn:length(content) > 150}">
                                            ${fn:substring(content, 0, 150)}...
                                        </c:when>
                                        <c:otherwise>
                                            ${content}
                                        </c:otherwise>
                                    </c:choose>
                                    </small>
                                </span>
                            <br>
                            <div class="row m-1">
                                <div class="text-left ml-1" style="color:gray;">
                                    <img src="${pageContext.request.contextPath}/images/icon.png" style="height: 12px;"> ${recruit.member.memberID}　<img class="mb-0" src="${pageContext.request.contextPath}/images/clock.png" style="height: 12px;"> <span class="date post-date" data-post-date="${recruit.registDate}"></span>
                                </div>
                                <div class="text-right ml-auto" style="color: gray">
                                    <img class="mb-1" src="${pageContext.request.contextPath}/images/eye-icon.png" style="height: 15px;"> ${recruit.viewCount}
                                    <img class="mb-1" src="${pageContext.request.contextPath}/images/bubble-Icon.png" style="height: 23px;">${recruit.replies.size()}
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- 페이지네이션 -->
    <nav class="mt-5">
        <ul class="pagination justify-content-center">
            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                <a class="page-link" href="?page=${currentPage - 1}&size=${size}&recruitStatus=${param.recruitStatus}&searchType=${param.searchType}&search=${param.search}" tabindex="-1">이전</a>
            </li>
            <c:forEach begin="1" end="${totalPages}" var="i">
                <li class="page-item ${currentPage == i ? 'active' : ''}">
                    <a class="page-link" href="?page=${i}&size=${size}&recruitStatus=${param.recruitStatus}&searchType=${param.searchType}&search=${param.search}">${i}</a>
                </li>
            </c:forEach>
            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                <a class="page-link" href="?page=${currentPage + 1}&size=${size}&recruitStatus=${param.recruitStatus}&searchType=${param.searchType}&search=${param.search}">다음</a>
            </li>
        </ul>
    </nav>
</section>

<script>
$(document).ready(function() {
$('.post-date').each(function() {
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
