<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<!-- header.jsp -->
<%@ include file="../commons/header.jsp" %>

<style>
    #editor {
        width: auto;
        margin: 0 auto;
        min-height: 300px;
    }
</style>
<!-- Quill styleSheet -->
<link href="https://cdn.jsdelivr.net/npm/quill@2.0.2/dist/quill.snow.css" rel="stylesheet" />
</head>

<body>
<!-- body.jsp -->
<%@ include file="../commons/body.jsp" %>

<!-- navigation.jsp -->
<%@ include file="../commons/navigation.jsp" %>

<!-- container  -->
<section class="container mt-3 mb-5">
    <div class="">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/board/regist" method="post" enctype="multipart/form-data">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="form-group">
                    <select id="postCategory" name="postCategory">
                        <option value="">카테고리</option>
                        <option value="질문">질문</option>
                        <option value="사담">사담</option>
                    </select>
                </div>
                <div class="form-group">
                    <input class="" name="postTitle" placeholder="제목을 작성해주세요." id="postTitle"
                           style="height: 50px; width:100%; border: none; background:transparent;">
                </div>
                <div class="form-group">
                    <div id="editor"></div>
                    <input type="hidden" id="quill_html" name="postContent">
                </div>
                <div class="row ml-auto mt-3">
                    <a class="btn btn-outline-secondary mr-1" href="${pageContext.request.contextPath}/board">취소</a>
                    <button type="submit" class="btn btn-primary">작성</button>
                </div>
            </form>
        </div>
    </div>
</section>

<!-- Include the Quill library -->
<script src="https://cdn.jsdelivr.net/npm/quill@2.0.2/dist/quill.js"></script>
<script>
    var contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/js/postRegist.js"></script>

<!-- footer.jsp -->
<%@ include file="../commons/footer.jsp" %>
</body>
</html>
