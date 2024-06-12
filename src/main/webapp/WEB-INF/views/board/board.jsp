<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <!-- header.jsp -->
    <%@ include file="../commons/header.jsp" %>
    <style>
        .table th, .table td {
            vertical-align: middle; /* 모든 헤더와 셀 수직 가운데 정렬 */
        }
        .table th.id, .table td.id {
            width: 50px; /* ID 열의 너비를 고정 */
            text-align: center; /* ID 열 가운데 정렬 */
        }
        .table th.category, .table td.category {
            width: 150px; /* 카테고리 열의 너비를 고정 */
            text-align: center; /* 카테고리 열 가운데 정렬 */
        }
        .table th.title, .table td.title {
            width: 300px; /* 제목 열의 너비를 고정 */
            text-align: left; /* 제목 열 왼쪽 정렬 */
        }
        .table th.author, .table td.author {
            width: 150px; /* 작성자 열의 너비를 고정 */
            text-align: center; /* 작성자 열 가운데 정렬 */
        }
        .table th.date, .table td.date {
            width: 200px; /* 작성일 열의 너비를 고정 */
            text-align: center; /* 작성일 열 가운데 정렬 */
        }
        .table th.viewCount, .table td.viewCount {
            width: 100px; /* 조회수 열의 너비를 고정 */
            text-align: center; /* 조회수 열 가운데 정렬 */
        }
    </style>
</head>

<body>
<!-- body.jsp -->
<%@ include file="../commons/body.jsp" %>

<!-- navigation.jsp -->
<%@ include file="../commons/navigation.jsp" %>

<!-- container  -->
<section class="container mt-3 mb-5">
    <span class="text-left" id="pageInfo"><h2>자유게시판</h2></span>
    <form method="get" action="${pageContext.request.contextPath}/board" class="form-inline mt-1">
        <select name="postCategory" class="form-control mx-1 mt-2">
            <option value="전체" ${param.postCategory == '전체' ? 'selected' : ''}>전체</option>
            <option value="질문" ${param.postCategory == '질문' ? 'selected' : ''}>질문</option>
            <option value="사담" ${param.postCategory == '사담' ? 'selected' : ''}>사담</option>
        </select>
        <select name="searchType" class="form-control mx-1 mt-2">
            <option value="최신순" ${param.searchType == '최신순' ? 'selected' : ''}>최신순</option>
            <option value="조회수순" ${param.searchType == '조회수순' ? 'selected' : ''}>조회수순</option>
            <option value="추천순" ${param.searchType == '추천순' ? 'selected' : ''}>추천순</option>
        </select>
        <input type="text" name="search" class="form-control mx-1 mt-2" placeholder="내용을 입력해주세요." value="${param.search}">
        <button type="submit" class="btn btn-dark mx-1 mt-2">검색</button>
        <div class="ml-auto">
            <a class="btn btn-primary mx-1 mt-2" href="${pageContext.request.contextPath}/board/regist">작성하기</a>
        </div>
    </form>
    <div class="card bg-light mt-3">
        <table class="table table-hover">
            <thead>
                <tr>
                    <th class="id">#</th>
                    <th class="category">카테고리</th>
                    <th class="title">제목</th>
                    <th class="author">작성자</th>
                    <th class="date">작성일</th>
                    <th class="viewCount">조회수</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="board" items="${boardList.content}">
                    <tr class="clickable-row" onclick="window.location.href='${pageContext.request.contextPath}/board/post/${board.postID}'" style="cursor: pointer;">
                        <td class="id">${board.postID}</td>
                        <td class="category">${board.postCategory}</td>
                        <td class="title">${board.postTitle} <small>(추천: ${board.likeCount})</small></td>
                        <td class="author">${board.member.memberID}</td>
                        <td class="date post-date" data-post-date="${board.postDate}"></td>
                        <td class="viewCount">${board.viewCount}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- 페이지네이션 -->
    <nav class="mt-5">
        <ul class="pagination justify-content-center">
            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                <a class="page-link" href="?page=${currentPage - 1}&size=${size}&postCategory=${param.postCategory}&searchType=${param.searchType}&search=${param.search}" tabindex="-1">이전</a>
            </li>
            <c:forEach begin="1" end="${totalPages}" var="i">
                <li class="page-item ${currentPage == i ? 'active' : ''}">
                    <a class="page-link" href="?page=${i}&size=${size}&postCategory=${param.postCategory}&searchType=${param.searchType}&search=${param.search}">${i}</a>
                </li>
            </c:forEach>
            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                <a class="page-link" href="?page=${currentPage + 1}&size=${size}&postCategory=${param.postCategory}&searchType=${param.searchType}&search=${param.search}">다음</a>
            </li>
        </ul>
    </nav>
</section>

<!-- Include dateFormatter.js -->
<script src="${pageContext.request.contextPath}/js/dateFormatter.js"></script>

<!-- footer.jsp -->
<%@ include file="../commons/footer.jsp" %>
</body>
</html>
