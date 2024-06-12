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
            <h5 class="card-title font-weight-bold mt-2">서평 수정</h5>
        </div>
        <div class="card-body pl-5 pb-5">
            <form action="${pageContext.request.contextPath}/review/update/${review.reviewID}" method="post" enctype="multipart/form-data">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="form-row">
                    <div class="form-group col-sm-2">
                        <label for="category">카테고리</label>
                        <select class="form-control pb-2" id="category" name="category">
                            <option value="">선택</option>
                            <option value="문학" <c:if test="${review.category == '문학'}">selected</c:if>>문학</option>
                            <option value="인문과학" <c:if test="${review.category == '인문과학'}">selected</c:if>>인문과학</option>
                            <option value="사회과학" <c:if test="${review.category == '사회과학'}">selected</c:if>>사회과학</option>
                            <option value="자연과학" <c:if test="${review.category == '자연과학'}">selected</c:if>>자연과학</option>
                            <option value="기타" <c:if test="${review.category == '기타'}">selected</c:if>>기타</option>
                        </select>
                    </div>
                    <div class="form-group ml-1 col-sm-9">
                        <label for="bookName">서명</label>
                        <input type="text" name="bookName" class="form-control" maxlength="500" id="bookName" value="${review.bookName}">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-sm-5">
                        <label for="authorName">저자</label>
                        <input type="text" name="authorName" class="form-control" maxlength="100" id="authorName" value="${review.authorName}">
                    </div>
                    <div class="form-group col-sm-3">
                        <label for="publisher">출판사</label>
                        <input type="text" name="publisher" class="form-control" maxlength="20" id="publisher" value="${review.publisher}">
                    </div>
                    <div class="form-group col-sm-3">
                        <label for="reviewScore">평점</label>
                        <select name="reviewScore" class="form-control" id="reviewScore">
                            <option value="99">선택</option>
                            <option value="5" <c:if test="${review.reviewScore == '5' }">selected</c:if>>★★★★★</option>
                            <option value="4" <c:if test="${review.reviewScore == '4' }">selected</c:if>>★★★★☆</option>
                            <option value="3" <c:if test="${review.reviewScore == '3'}">selected</c:if>>★★★☆☆</option>
                            <option value="2" <c:if test="${review.reviewScore == '2'}">selected</c:if>>★★☆☆☆</option>
                            <option value="1" <c:if test="${review.reviewScore == '1'}">selected</c:if>>★☆☆☆☆</option>
                            <option value="0" <c:if test="${review.reviewScore == '0'}">selected</c:if>>☆☆☆☆☆</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="reviewTitle">제목</label>
                    <input class="form-control col-sm-11" type="text" name="reviewTitle" placeholder="제목을 작성해주세요." id="reviewTitle" value="${review.reviewTitle}">
                </div>
                <div class="form-group">
                    <label for="reviewContent">내용</label>
                    <textarea class="form-control col-sm-11" id="reviewContent" name="reviewContent"
                              placeholder="내용을 작성해주세요." style="overflow-wrap: break-word; resize: none; height: 300px;">${review.reviewContent}</textarea>
                </div>
                <div class="row ml-auto mt-3">
                    <a class="btn btn-outline-secondary mr-1" href="${pageContext.request.contextPath}/review/post/${review.reviewID}">취소</a>
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
