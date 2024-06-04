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
                <a class="nav-link" href="/">메인</a>
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
                <a class="nav-link dropdown-toggle active" id="dropdown" data-toggle="dropdown">
                    <b>회원관리</b>
                </a>
                <div class="dropdown-menu" aria-labelledby="dropdown">
                    <a class="dropdown-item" href="/user/login">로그인</a>
                    <a class="dropdown-item" href="/user/register">회원가입</a>
                </div>
            </li>
        </ul>
    </div>
</nav>

<!-- container  -->
<section class="container mt-5" style="max-width: 560px">
    <form method="post" action="/login">
        <div class="form-group">
            <label>아이디</label>
            <input type="text" name="username" class="form-control" required>
        </div>
        <div class="form-group">
            <label>비밀번호</label>
            <input type="password" name="password" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary btn-block">로그인</button>
    </form>
    <div class="mt-3">
        <p>아직 계정이 없으신가요? <a href="/user/register">회원가입하기</a></p>
    </div>
</section>

<!-- footer -->
<footer class="fixed-bottom bg-dark text-center mt-5" style="color: #FFFFFF;">
    Copyright &copy; 2024 EhyunSeok All Rights Reserved.
</footer>
</body>
</html>
