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
</head>

<body>
<!-- body.jsp -->
<%@ include file="../commons/body.jsp" %>

<!-- navigation.jsp -->
<%@ include file="../commons/navigation.jsp" %>

<!-- container  -->
<section class="container mt-3 mb-5">
    <div class="card">
        <div class="card-header">
            <h5 class="card-title font-weight-bold mt-2">서평 작성</h5>
        </div>
        <div class="card-body pl-5 pb-5">
            <form action="${pageContext.request.contextPath}/review/regist" method="post" enctype="multipart/form-data">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="form-row">
                    <div class="form-group col-sm-2">
                        <label for="category">카테고리</label>
                        <select class="form-control pb-2" id="category" name="category">
                            <option value="">선택</option>
                            <option value="문학">문학</option>
                            <option value="사회">사회</option>
                            <option value="과학">과학</option>
                            <option value="예술">예술</option>
                            <option value="역사">역사</option>
                            <option value="언어(어학)">언어(어학)</option>
                            <option value="기타">기타</option>
                        </select>
                    </div>
                    <div class="form-group ml-1 col-sm-9">
                        <label for="bookName">서명</label>
                        <input type="text" name="bookName" class="form-control" maxlength="500" id="bookName">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-sm-5">
                        <label for="authorName">저자</label>
                        <input type="text" name="authorName" class="form-control" maxlength="100" id="authorName">
                    </div>
                    <div class="form-group col-sm-3">
                        <label for="publisher">출판사</label>
                        <input type="text" name="publisher" class="form-control" maxlength="20" id="publisher">
                    </div>
                    <div class="form-group col-sm-3">
                        <label for="reviewScore">평점</label>
                        <select name="reviewScore" class="form-control" id="reviewScore">
                            <option value="99" selected>선택</option>
                            <option value="5">★★★★★</option>
                            <option value="4">★★★★☆</option>
                            <option value="3">★★★☆☆</option>
                            <option value="2">★★☆☆☆</option>
                            <option value="1">★☆☆☆☆</option>
                            <option value="0">☆☆☆☆☆</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="reviewTitle">제목</label>
                    <input class="form-control col-sm-11" type="text" name="reviewTitle" placeholder="제목을 작성해주세요." id="reviewTitle">
                </div>
                <div class="form-group">
                    <label for="reviewContent">내용</label>
                    <textarea class="form-control col-sm-11" id="reviewContent" name="reviewContent"
                              placeholder="내용을 작성해주세요." style="overflow-wrap: break-word; resize: none; height: 300px;"></textarea>
                </div>
                <div class="row ml-auto mt-3">
                    <a class="btn btn-outline-secondary mr-1" href="${pageContext.request.contextPath}/review">취소</a>
                    <button type="submit" class="btn btn-primary">작성</button>
                </div>
            </form>
        </div>
    </div>
</section>

<!-- Include the Quill library -->
<script>
    var contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/js/reviewRegist.js"></script>

<!-- footer.jsp -->
<%@ include file="../commons/footer.jsp" %>
</body>
</html>
