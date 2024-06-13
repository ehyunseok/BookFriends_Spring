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

<section class="container mt-5 mb-5">
    <div class="card m-auto" style="max-width: 500px;">
        <div class="card-body text-center">
            <img src="${pageContext.request.contextPath}/images/icon.png" style="height: 50px;">
            <h5 class="card-title mt-3"><b><c:out value="${pageContext.request.userPrincipal.name}"/></b>님</h5>
            <h6 class="card-subtitle m-2 text-muted">환영합니다</h6>
            <form action="${pageContext.request.contextPath}/member/logout" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-secondary form-control">로그아웃</button>
            </form>
            <div class="row justify-content-center mt-3">
                <button type="button" class="btn btn-warning m-1"  onclick=window.location.href="${pageContext.request.contextPath}/review">서평</button>
                <button type="button" class="btn btn-warning m-1" onclick=window.location.href='${pageContext.request.contextPath}/library/recommended'>추천도서</button>
                <button type="button" class="btn btn-warning m-1" onclick=window.location.href='${pageContext.request.contextPath}/library/search'>도서검색</button>
                <button type="button" class="btn btn-warning m-1" onclick=window.location.href='${pageContext.request.contextPath}/board'>게시판</button>
                <button type="button" class="btn btn-warning m-1"  onclick=window.location.href="${pageContext.request.contextPath}/recruit">독서모임</button>
                <button type="button" class="btn btn-warning m-1" onclick=window.location.href='${pageContext.request.contextPath}/chat'>채팅</button>
            </div>
        </div>
    </div>
</section>

<!-- footer.jsp -->
<%@ include file="./commons/footer.jsp" %>
</body>
</html>
