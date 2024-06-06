<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <!-- header.jsp -->
    <%@ include file="../commons/header.jsp" %>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/memberJoinLogin.css">

</head>

<body data-context-path="${pageContext.request.contextPath}">
<!-- body.jsp -->
<%@ include file="../commons/body.jsp" %>

<!-- navigation.jsp -->
<%@ include file="../commons/navigation.jsp" %>

<!-- container -->
<section class="container mt-5" style="max-width: 560px">
    <form method="post" action="${pageContext.request.contextPath}/member/join">
        <div class="form-group">
            <label>아이디</label>
            <div class="input-group mb-3">
                <input type="text" name="memberID" class="form-control" id="memberID">
                <div class="input-group-append">
                    <button class="btn btn-outline-secondary" type="button" id="checkMemberIDBtn">중복확인</button>
                </div>
            </div>
            <div id="memberIDFeedback"></div>
        </div>
        <div class="form-group">
            <label>비밀번호</label>
            <input type="password" name="memberPassword" class="form-control" id="memberPassword">
        </div>
        <div class="form-group">
            <label>이메일</label>
            <div class="input-group mb-3">
                <input type="email" name="memberEmail" class="form-control" placeholder="id@mail.com" id="memberEmail">
                <div class="input-group-append">
                    <button class="btn btn-outline-secondary" type="button" id="sendCodeBtn">인증코드 받기</button>
                </div>
            </div>
            <div class="text-left" id="authCodeFeedback"></div>
        </div>
        <!-- 인증 영역, 기본적으로 숨김! -->
        <div class="form-group" id="verificationSection" style="display: none;">
            <label for="verificationCode">인증코드</label>
            <div class="input-group mb-3">
                <input type="text" name="authCode" class="form-control" id="verificationCode">
                <div class="input-group-append">
                    <button class="btn btn-outline-secondary" type="button" id="verifyCodeBtn">인증코드 확인</button>
                </div>
            </div>
        </div>

        <button type="submit" class="btn btn-primary btn-block">회원가입</button>
    </form>
    <div class="mt-3">
        <p>계정이 있으신가요? <a href="${pageContext.request.contextPath}/member/login">로그인하기</a></p>
    </div>
</section>

<script src="${pageContext.request.contextPath}/js/memberJoin.js"></script>

<!-- footer.jsp -->
<%@ include file="../commons/footer.jsp" %>
</body>
</html>
