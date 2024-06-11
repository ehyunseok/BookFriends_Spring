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
<section class="container mt-3 mb-5">
    <span class="text-left" id="pageInfo"><h2>중고장터</h2></span>
    <div class="alert alert-dark mt-5 text-center p-5" role="alert">
        ⚠️
        <br>페이지를 준비중입니다.
        <br>
        <br><a class="alert-link" href="${pageContext.request.contextPath}/">메인 페이지로 이동</a>
    </div>
</section>

<!-- footer.jsp -->
<%@ include file="../commons/footer.jsp" %>
</body>
</html>
