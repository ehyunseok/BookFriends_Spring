<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <!-- header.jsp -->
    <%@ include file="../commons/header.jsp" %>

    <style>
        #goToChat {
            display: flex;
            align-items: center;
            justify-content: center;
            border: none;
            background: transparent;
            box-shadow: none;
            padding: 0;
        }
        #goToChat h5 {
            margin-bottom: 0;
        }
    </style>
</head>

<body>
<!-- body.jsp -->
<%@ include file="../commons/body.jsp" %>

<!-- navigation.jsp -->
<%@ include file="../commons/navigation.jsp" %>

<!-- hidden input for contextPath -->
<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}" />

<!-- container -->
<section class="container mt-3 mb-5">
    <span class="text-left" id="pageInfo"><h2>서평</h2></span>
    <div class="card bg-light mt-1">
        <div class="card-header bg-light">
            <div class="btn-group dropright">
                <button id="goToChat" class="dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">
                    <h5 class="card-title"><img src="${pageContext.request.contextPath}/images/icon.png" style="height:30px;"> <b>${review.member.memberID}</b></h5>
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdown">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/chat/chatting/${review.member.memberID}">채팅하기</a>
                </div>
            </div>
            <p class="card-text">조회수: ${review.viewCount} | 작성일: <span class="post-date" data-post-date="${review.registDate}"></span></p>
        </div>
        <div class="card-body">
            <h4 class="card-title"><b>${review.reviewTitle}</b></h4>
            <h5 class="">[${review.category}]${review.bookName}</h5>
            <p><small>저자: ${review.authorName}</small></p>
            <p class="card-text" style="text-align:justify; white-space:pre-wrap;">${review.reviewContent}</p>
            <div class="row">
                <div class="col-sm-3">
                    평점 :
                    <span>
                        <c:forEach var="i" begin="1" end="5">
                            <c:choose>
                                <c:when test="${i <= review.reviewScore}">
                                    ★
                                </c:when>
                                <c:otherwise>
                                    ☆
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </span>
                </div>
                <div class="col-9 text-right">
                    <a class="like-button" style="color: black;" data-item-cate="review" data-item-type="Review" data-item-id="${review.reviewID}" data-member-id="${currentMemberID}" href="javascript:void(0);">추천(${review.likeCount})</a>
                    <c:if test="${currentMemberID == review.member.memberID}">
                        | <a style="color: gray;" onclick="return confirm('수정하시겠습니까?')" href="${pageContext.request.contextPath}/review/update/${review.reviewID}">수정</a> |
                        <form id="deleteForm" action="${pageContext.request.contextPath}/review/delete/${review.reviewID}" method="post" style="display:none;">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="_method" value="DELETE">
                        </form>
                        <a style="color: gray;" onclick="if(confirm('삭제하시겠습니까?')) { document.getElementById('deleteForm').submit(); }">삭제</a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</section>
<script>
$(document).ready(function() {
    $('.post-date').each(function() {
        var postDate = $(this).data('post-date');
        var postDateObj = new Date(postDate);
        var now = new Date();

        var isToday = postDateObj.toDateString() === now.toDateString();

        var formattedDate = isToday ?
            postDateObj.getHours() + ':' + (postDateObj.getMinutes() < 10 ? '0' : '') + postDateObj.getMinutes() :
            postDateObj.getFullYear() + '.' + (postDateObj.getMonth() + 1) + '.' + postDateObj.getDate();

        $(this).text(formattedDate);
    });
});
</script>


<!-- likeToggle.js 추가 -->
<script src="${pageContext.request.contextPath}/js/likeToggle.js"></script>

<!-- footer.jsp -->
<%@ include file="../commons/footer.jsp" %>
</body>
</html>
