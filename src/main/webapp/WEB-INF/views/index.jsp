<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<!-- header.jsp -->
<%@ include file="./commons/header.jsp" %>
</head>

<body>
<!-- body.jsp -->
<%@ include file="./commons/body.jsp" %>

<!-- navigation.jsp -->
<%@ include file="./commons/navigation.jsp" %>

<section class="container m-5>
<div class="center-container">
        <div class="row row-cols-1 row-cols-md-2 mt-5">
            <!-- 서평 인기글 -->
            <div class="col card-wrapper">
                <div class="card">
                    <div class="card-header text-center">
                        <a href="${pageContext.request.contextPath}/review/bookReview" style="color: black;">
                            <h5 class="card-title"><b>서평</b></h5>
                        </a>
                        <p class="card-text">추천수 top5</p>
                    </div>
                    <ul class="list-group list-group-flush">
                        <c:forEach var="review" items="#">
                            <li class="list-group-item" onclick="">
                                [토지 <small>박경리</small>]
                                <div class="truncate-text">
                                    <b>review.reviewTitle</b>  <small style="font-size:xx-small;">추천:10000</small>
                                </div>
                                <small><img src="${pageContext.request.contextPath}/images/icon.png" style="height:12px;">memberID</small>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <!-- 자유게시판 인기글 -->
            <div class="col card-wrapper">
                <div class="card">
                    <div class="card-header text-center">
                        <a href="${pageContext.request.contextPath}/board/board" style="color: black;">
                            <h5 class="card-title"><b>자유게시판</b></h5>
                        </a>
                        <p class="card-text">인기글 top5</p>
                    </div>
                    <ul class="list-group list-group-flush">
                        <c:forEach var="board" items="#">
                            <li class="list-group-item" onclick="">
                                [board.postCategory]
                                <div class="truncate-text">
                                    <b>board.postTitle</b>  <small style="font-size:xx-small;">추천:board.likeCount</small>
                                </div>
                                <small><img src="${pageContext.request.contextPath}/images/icon.png" style="height:15px;">board.memberID</small>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- footer.jsp -->
<%@ include file="./commons/footer.jsp" %>
</body>
</html>
