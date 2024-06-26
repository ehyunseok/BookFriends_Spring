<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
    <!-- header.jsp -->
    <%@ include file="../commons/header.jsp" %>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/memberJoinLogin.css">
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>

<body data-context-path="${pageContext.request.contextPath}">
<!-- body.jsp -->
<%@ include file="../commons/body.jsp" %>

<!-- navigation.jsp -->
<%@ include file="../commons/navigation.jsp" %>

<!-- container  -->
<section class="container mt-5" style="max-width: 560px">
    <form id="loginForm" method="post" action="${pageContext.request.contextPath}/member/login">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group">
            <label>아이디</label>
            <input type="text" name="username" class="form-control" id="memberID" required>
        </div>
        <div class="form-group">
            <label>비밀번호</label>
            <input type="password" name="password" class="form-control" id="memberPassword" required>
        </div>
        <button type="submit" id="loginBtn" class="btn btn-primary btn-block">로그인</button>
    </form>
    <c:if test="${not empty error}">
        <div class="alert alert-danger mt-3">${error}</div>
    </c:if>
    <div class="mt-3">
        <p>아직 계정이 없으신가요? <a href="${pageContext.request.contextPath}/member/join">회원가입하기</a></p>
    </div>
</section>

<script src="${pageContext.request.contextPath}/js/memberLogin.js"></script>

<!-- footer.jsp -->
<%@ include file="../commons/footer.jsp" %>
</body>
</html>
