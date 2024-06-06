<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<section class="container">
    <form method="get" action="${pageContext.request.contextPath}/board" class="form-inline mt-3">
        <select name="postCategory" class="form-control mx-1 mt-2">
            <option value="전체">전체</option>
            <option value="질문">질문</option>
            <option value="사담">사담</option>
        </select>
        <select name="searchType" class="form-control mx-1 mt-2">
            <option value="최신순">최신순</option>
            <option value="조회수순">조회수순</option>
            <option value="추천순">추천순</option>
        </select>
        <input type="text" name="search" class="form-control mx-1 mt-2" placeholder="내용을 입력해주세요.">
        <button type="submit" class="btn btn-dark mx-1 mt-2">검색</button>
        <div class="ml-auto">
            <a class="btn btn-primary mx-1 mt-2" href="${pageContext.request.contextPath}/board/regist">작성하기</a>
        </div>
    </form>
    <div class="card bg-light mt-3">
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>#</th>
                    <th>카테고리</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>작성일</th>
                    <th>조회수</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="board" items="${boardList}">
                    <tr onclick="window.location.href='${pageContext.request.contextPath}/board/post/${board.postID}'">
                        <td>${board.postID}</td>
                        <td>${board.postCategory}</td>
                        <td>${board.postTitle} <small>(추천: ${board.likeCount})</small></td>
                        <td>${board.member.memberID}</td>
                        <td>${board.postDate}</td>
                        <td>${board.viewCount}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</section>


<!-- footer.jsp -->
<%@ include file="../commons/footer.jsp" %>
</body>
</html>
