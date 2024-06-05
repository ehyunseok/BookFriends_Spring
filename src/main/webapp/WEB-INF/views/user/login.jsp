<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<!-- header.jsp -->
<%@ include file="/WEB-INF/views/commons/header.jsp" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/userJoinLogin.css">

</head>

<body>
<!-- body.jsp -->
<%@ include file="/WEB-INF/views/commons/body.jsp" %>

<!-- navigation.jsp -->
<%@ include file="/WEB-INF/views/commons/navigation.jsp" %>

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
        <p>아직 계정이 없으신가요? <a href="${pageContext.request.contextPath}/user/join">회원가입하기</a></p>
    </div>
</section>

<!-- footer.jsp -->
<%@ include file="/WEB-INF/views/commons/footer.jsp" %>
</body>
</html>
