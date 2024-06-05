<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>독서친구</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="/css/custom.css">
    <!-- Font Awesome -->
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"/>
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- Bootstrap JS -->
    <script src="/js/bootstrap.min.js"></script>
    <style>
        .center-container {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 20vh;
        }
        .card-wrapper {
            height: 50vh;
            width: 80vh;
        }
        .truncate-text {
            white-space: nowrap;
            overflow: hidden;
            tindex.jspext-overflow: ellipsis;
            max-width: 500px;
        }
    </style>
</head>
<body>

<!-- navigation -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">독서친구</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div id="navbar" class="collapse navbar-collapse">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/"><b>메인</b></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/review/bookReview.jsp">서평</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/board/board.jsp">자유게시판</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/recruit/recruit.jsp">독서모임</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/market/market.jsp">중고장터</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/chat/chat.jsp">채팅</a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown">
                    회원관리
                </a>
                <div class="dropdown-menu" aria-labelledby="dropdown">
                    <a class="dropdown-item" style="color: green;"><b><c:out value="${userID}"/></b> 님 환영합니다.</a>
                    <a class="dropdown-item" href="/user/logout">로그아웃</a>
                </div>
            </li>
        </ul>
    </div>
</nav>

<section class="container">
    <div class="center-container">
        <div class="row row-cols-1 row-cols-md-2 mt-5">
            <!-- 서평 인기글 -->
            <div class="col card-wrapper">
                <div class="card">
                    <div class="card-header text-center">
                        <a href="/review/bookReview.jsp" style="color: black;">
                            <h5 class="card-title"><b>서평</b></h5>
                        </a>
                        <p class="card-text">추천수 top5</p>
                    </div>
                    <ul class="list-group list-group-flush">
                        <c:forEach var="review" items="${top5Reviews}">
                            <li class="list-group-item" onclick="window.location='/review/reviewDetail.jsp?reviewID=${review.reviewID}'">
                                [${review.bookName} <small>${review.authorName}</small>]
                                <div class="truncate-text">
                                    <b>${review.reviewTitle}</b>  <small style="font-size:xx-small;">추천:${review.likeCount}</small>
                                </div>
                                <small><img src="/images/icon.png" style="height:12px;">${review.userID}</small>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <!-- 자유게시판 인기글 -->
            <div class="col card-wrapper">
                <div class="card">
                    <div class="card-header text-center">
                        <a href="/board/board.jsp" style="color: black;">
                            <h5 class="card-title"><b>자유게시판</b></h5>
                        </a>
                        <p class="card-text">인기글 top5</p>
                    </div>
                    <ul class="list-group list-group-flush">
                        <c:forEach var="board" items="${top5Boards}">
                            <li class="list-group-item" onclick="window.location='/board/postDetail.jsp?postID=${board.postID}'">
                                [${board.postCategory}]
                                <div class="truncate-text">
                                    <b>${board.postTitle}</b>  <small style="font-size:xx-small;">추천:${board.likeCount}</small>
                                </div>
                                <small><img src="/images/icon.png" style="height:15px;">${board.userID}</small>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- footer -->
<footer class="fixed-bottom bg-dark text-center mt-5" style="color: #FFFFFF;">
    Copyright &copy; 2024 EhyunSeok All Rights Reserved.
</footer>

</body>
</html>
