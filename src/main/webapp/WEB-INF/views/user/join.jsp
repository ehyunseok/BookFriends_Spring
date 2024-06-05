<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <!-- header.jsp -->
    <%@ include file="/WEB-INF/views/commons/header.jsp" %>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/userJoinLogin.css">

</head>

<body data-context-path="${pageContext.request.contextPath}">
<!-- body.jsp -->
<%@ include file="/WEB-INF/views/commons/body.jsp" %>

<!-- navigation.jsp -->
<%@ include file="/WEB-INF/views/commons/navigation.jsp" %>

<!-- container -->
<section class="container mt-5" style="max-width: 560px">
<!-- form action 수정해야함!!!! -->
    <form method="post" action="${pageContext.request.contextPath}/user/join">
        <div class="form-group">
            <label>아이디</label>
            <div class="input-group mb-3">
                <input type="text" name="userID" class="form-control" id="userID">
                <div class="input-group-append">
                    <button class="btn btn-outline-secondary" type="button" id="checkUserIdBtn">중복확인</button>
                </div>
            </div>
            <div id="userIdFeedback"></div>
        </div>
        <div class="form-group">
            <label>비밀번호</label>
            <input type="password" name="userPassword" class="form-control" id="userPassword">
        </div>
        <div class="form-group">
            <label>이메일</label>
            <input type="email" name="userEmail" class="form-control" placeholder="id@mail.com" id="userEmail">
        </div>
        <button type="submit" class="btn btn-primary btn-block">회원가입</button>
    </form>
    <div class="mt-3">
        <p>계정이 있으신가요? <a href="${pageContext.request.contextPath}/user/login">로그인하기</a></p>
    </div>
</section>

<script src="${pageContext.request.contextPath}/js/userJoin.js"></script>

<!-- footer.jsp -->
<%@ include file="/WEB-INF/views/commons/footer.jsp" %>
</body>
</html>
