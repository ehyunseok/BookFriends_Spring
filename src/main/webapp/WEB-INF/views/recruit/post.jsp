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

<!-- container  -->
<section class="container mt-3 mb-5">
    <span class="text-left" id="pageInfo"><h2>독서모임</h2></span>
    <div class="card bg-light mt-1">
        <div class="card-header bg-light">
            <div class="row">
                <div class="ml-1 btn-group dropright">
                    <button id="goToChat" class="dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">
                        <h5 class="card-title"><img src="${pageContext.request.contextPath}/images/icon.png" style="height:30px;"> <b>${recruit.member.memberID}</b></h5>
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdown">
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/chat/chatting/${recruit.member.memberID}">채팅하기</a>
                    </div>
                </div>
                <div class="ml-auto mr-3 mt-1">
                    <p class="card-text"><img class="mb-2" src="${pageContext.request.contextPath}/images/eye-icon.png" style="height: 20px;"> ${recruit.viewCount}  <img class="mb-1" src="${pageContext.request.contextPath}/images/clock.png" style="height: 17px;"> <span class="post-date" data-post-date="${recruit.registDate}"></span></p>
                </div>
            </div>
        </div>
        <div class="card-body">
            <div class="row">
                <div>
                <c:choose>
                    <c:when test="${'모집중' == recruit.recruitStatus}">
                        <span class="badge badge-success p-2">모집중</span>
                    </c:when>
                    <c:otherwise>
                        <span class="badge badge-secondary p-2">모집완료</span>
                    </c:otherwise>
                </c:choose>
                </div>
                <h4 class="card-title"><b>${recruit.recruitTitle}</b></h4>
            </div>
            <p class="card-text" style="text-align:justify; white-space:pre-wrap;">${recruit.recruitContent}</p>
            <div class="row">
                <c:if test="${currentMemberID == recruit.member.memberID}">
                    <div class="col-12 text-right">
                            <a style="color: gray;" onclick="return confirm('수정하시겠습니까?')" href="${pageContext.request.contextPath}/recruit/update/${recruit.recruitID}">수정</a> |
                            <form id="deleteForm" action="${pageContext.request.contextPath}/recruit/delete/${recruit.recruitID}" method="post" style="display:none;">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="_method" value="DELETE">
                            </form>
                            <a style="color: gray; cursor: pointer;" onclick="if(confirm('삭제하시겠습니까?')) { document.getElementById('deleteForm').submit(); }">삭제</a>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <!-- 댓글 작성 -->
    <div class="card mt-3">
        <div class="card-header">댓글 작성</div>
        <div class="card-body">
            <form id="newReplyForm" method="post" action="${pageContext.request.contextPath}/recruit/registReply">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="recruitID" value="${recruit.recruitID}">
                <textarea name="replyContent" class="form-control" id="newReplyContent"
                          maxlength="2048" style="height: 100px;" placeholder="댓글을 작성해주세요."></textarea>
                <div class="text-right">
                    <button type="submit" class="btn btn-primary mt-1">작성</button>
                </div>
            </form>
        </div>
    </div>

    <!-- 댓글 리스트 -->
    <div id="comments-area">
        <div class="card mb-5 mt-2">
            <div class="card-header"><b>${recruit.replies.size()}</b>개의 댓글</div>
            <div class="card-body">
                <ul class="list-group list-group-flush">
                    <c:forEach var="reply" items="${recruit.replies}">
                        <li class="list-group-item m-1">
                            <div class="btn-group dropright">
                                <button id="goToChat" class="dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">
                                    <h5 class="card-title"><img src="../../images/icon.png" style="height:30px;"> <b>${reply.member.memberID}</b></h5>
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdown">
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/chat/chatting/${reply.member.memberID}">채팅하기</a>
                                </div>
                            </div>
                            　<small class="reply-date" data-post-date="${reply.replyDate}"></small>
                            <p style="text-align:justify; white-space:pre-wrap; padding-top:10px; font-size:large;">${reply.replyContent}</p>
                            <div class="text-right">
                                <c:if test="${currentMemberID == reply.member.memberID}">
                                    <a style="color: gray;" onclick="return confirm('수정하시겠습니까?')" data-toggle="modal" href="#updateReplyModal${reply.replyID}">수정</a> |
                                    <form id="deleteReplyForm${reply.replyID}" action="${pageContext.request.contextPath}/recruit/deleteReply/${recruit.recruitID}/${reply.replyID}" method="post" style="display:none;">
                                        <input type="hidden" name="_method" value="DELETE">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    </form>
                                    <a style="color: gray; cursor: pointer;" onclick="if(confirm('삭제하시겠습니까?')) { document.getElementById('deleteReplyForm${reply.replyID}').submit(); }">삭제</a>
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
                                        <form class="updateReplyForm" method="post"
                                              action="${pageContext.request.contextPath}/recruit/updateReply">
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <input type="hidden" name="replyID" value="${reply.replyID}">
                                            <input type="hidden" name="recruitID" value="${recruit.recruitID}">
                                            <div class="form-row">
                                                    <textarea name="replyContent" class="form-control" id="replyContent${reply.replyID}"
                                                              maxlength="2048" style="height: 180px;">${reply.replyContent}</textarea>
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

<%--시간 양식--%>
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

<!-- Include dateFormatter.js  -->
<script src="${pageContext.request.contextPath}/js/dateFormatter.js"></script>

<!-- postReplyRegist.js 추가 -->
<script src="${pageContext.request.contextPath}/js/postReplyRegist.js"></script>

<!-- likeToggle.js 추가 -->
<script src="${pageContext.request.contextPath}/js/likeToggle.js"></script>

<!-- footer.jsp -->
<%@ include file="../commons/footer.jsp" %>
</body>
</html>
