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

<!-- container  -->
<section class="container mt-3 mb-5">
    <div class="card bg-light mt-3">
        <div class="card-header bg-light">
            <div class="btn-group dropright">
                <button id="goToChat" class="dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">
                    <h5 class="card-title"><img src="../../images/icon.png" style="height:30px;"> <b>${board.member.memberID}</b></h5>
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdown">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/chat/chat/${board.member.memberID}">채팅하기</a>
                </div>
            </div>
            <p class="card-text">조회수: ${board.viewCount} | 작성일: <fmt:formatDate value="${board.postDate}" pattern="yyyy-MM-dd HH:mm" /></p>
        </div>
        <div class="card-body">
            <h4 class="card-title">[${board.postCategory}]<b>${board.postTitle}</b></h4>
            <p class="card-text" style="text-align:justify; white-space:pre-wrap;">${board.postContent}</p>
            <div class="row">
                <div class="col-12 text-right">
                    <a style="color: black;" onclick="return confirm('추천하시겠습니까?')" href="${pageContext.request.contextPath}/board/likePost/${board.postID}">추천(${board.likeCount})</a>
                    <c:if test="${currentMemberID == board.member.memberID}">
                        | <a style="color: gray;" onclick="return confirm('수정하시겠습니까?')" href="${pageContext.request.contextPath}/board/update/${board.postID}">수정</a> |
                        <form id="deleteForm" action="${pageContext.request.contextPath}/board/delete/${board.postID}" method="post" style="display:none;">
                            <input type="hidden" name="_method" value="DELETE">
                        </form>
                        <a style="color: gray;" onclick="return confirm('삭제하시겠습니까?')" href="#" onclick="document.getElementById('deleteForm').submit();">삭제</a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

    <!-- 댓글 작성 -->
    <div class="card mt-3">
        <div class="card-header">댓글 작성</div>
        <div class="card-body">
            <form method="post" action="">
                <input type="hidden" name="postID" value="${board.postID}">
                <textarea name="replyContent" class="form-control" maxlength="2048" style="height: 100px;"></textarea>
                <div class="text-right">
                    <button type="submit" class="btn btn-primary mt-1">작성</button>
                </div>
            </form>
        </div>
    </div>

    <!-- 댓글 리스트 -->
    <div id="comments-area">
        <div class="card mb-5 mt-2">
            <div class="card-header"><b>${board.replies.size()}</b>개의 댓글</div>
            <div class="card-body">
                <ul class="list-group list-group-flush">
                    <c:forEach var="reply" items="${board.replies}">
                        <li class="list-group-item m-1">
                            <div class="btn-group dropright">
                                <button id="goToChat" class="dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">
                                    <h5 class="card-title"><img src="../../images/icon.png" style="height:30px;"> <b>${reply.member.memberID}</b></h5>
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdown">
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/chat/chat2/${reply.member.memberID}">채팅하기</a>
                                </div>
                            </div>
                            <small><fmt:formatDate value="${reply.replyDate}" pattern="yyyy-MM-dd HH:mm" /></small>
                            <p style="text-align:justify; white-space:pre-wrap; padding-top:10px; font-size:large;">${reply.replyContent}</p>
                            <div class="text-right">
                                <a style="color: black;" onclick="return confirm('추천하시겠습니까?')" href="${pageContext.request.contextPath}/board/likeReply?replyID=${reply.replyID}">추천(${reply.likeCount})</a>
                                <c:if test="${memberID == reply.member.memberID}">
                                    | <a style="color: gray;" onclick="return confirm('수정하시겠습니까?')" data-toggle="modal" href="#updateReplyModal${reply.replyID}">수정</a> |
                                    <a style="color: gray;" onclick="return confirm('삭제하시겠습니까?')" href="${pageContext.request.contextPath}/board/deleteReply?replyID=${reply.replyID}">삭제</a>
                                </c:if>
                            </div>
                        </li>
                        <!-- 댓글 수정하기 모달 -->
                        <div class="modal fade" id="updateReplyModal${reply.replyID}" tabindex="-1" role="dialog" aria-labelledby="modal">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="modal">댓글 수정</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <form method="post" action="${pageContext.request.contextPath}/board/updateReplyAction?replyID=${reply.replyID}">
                                            <div class="form-row">
                                                <textarea name="replyContent" class="form-control" maxlength="2048" style="height: 180px;">${reply.replyContent}</textarea>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
                                                <button type="submit" class="btn btn-primary">수정</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</section>



<!-- footer.jsp -->
<%@ include file="../commons/footer.jsp" %>
</body>
</html>
