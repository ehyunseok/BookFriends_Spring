<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<!-- header.jsp -->
<%@ include file="/WEB-INF/views/commons/header.jsp" %>
</head>

<body>
<!-- body.jsp -->
<%@ include file="/WEB-INF/views/commons/body.jsp" %>

<!-- navigation.jsp -->
<%@ include file="/WEB-INF/views/commons/navigation.jsp" %>

<!-- container  -->
<section class="container">
		<div>
			<div class="card bg-light mt-3">
				<div class="card-header bg-light">
					<!-- 회원아이디 영역 클릭 -> 채팅하기 드랍다운 토글 -->
					<div class="btn-group dropright">
						<button id="goToChat" class="dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">
								<h5 class="card-title"><img src="../images/icon.png" style="height:30px;"> <b><%= board.getUserID() %></b></h5>
						</button>
						<div class="dropdown-menu" aria-labelledby="dropdown">
							<a class="dropdown-item" href="../chat/chat2.jsp?receiverID=<%= board.getUserID() %>">채팅하기</a>
						</div>
					</div>
					<p class="card-text">조회수: <%= board.getViewCount() %> | 작성일: <%= board.getPostDate() %></p>
				</div>
				<div class="card-body">
					<h4 class="card-title">[<%= board.getPostCategory() %>]<b><%= board.getPostTitle() %></b></h4>
					<p class="card-text" style="text-align:justify; white-space:pre-wrap;"><%= board.getPostContent() %>
					</p>
					<div class="row">
						<div class="col-12 text-right">
							<a style="color: black;" onclick="return confirm('추천하시겠습니까?')" href="./likePostAction.jsp?postID=<%= board.getPostID() %>">추천(<%= board.getLikeCount() %>)</a>
<%
// 사용자가 작성자와 동일한 경우 수정, 삭제버튼 노출
		//if(userID.equals(board.getUserID())){
%>

							 | <a style="color: gray;" onclick="return confirm('수정하시겠습니까?')" data-toggle="modal" href="#updateModal">수정</a> |
							<a style="color: gray;" onclick="return confirm('삭제하시겠습니까?')" href="./deletePostAction.jsp?postID=<%= board.getPostID() %>">삭제</a>
						</div>
<%
		} else {
%>
						</div>
<%
		}
%>
					</div>
				</div>
			</div>
		</div>



<!-- 댓글 작성 -->
		<div class="card mt-3">
			<div class="card-header">comment</div>
			<div class="card-body">
				<form method="post" action="./replyRegisterAction.jsp">
					<input type="hidden" name="postID" value="<%= postID %>">
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
				<div class="card-header"><b><%= countReply %></b>개의 댓글</div>
				<div class="card-body">
					<ul class="list-group list-group-flush">
<%
	for(ReplyDto replyDto : replyList){
%>
						<li class="list-group-item m-1">
							<!-- 회원아이디 영역 클릭 -> 채팅하기 드랍다운 토글 -->
							<div class="btn-group dropright">
								<button id="goToChat" class="dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">
										<h5 class="card-title"><img src="../images/icon.png" style="height:30px;"> <b><%= replyDto.getUserID() %></b></h5>
								</button>
								<div class="dropdown-menu" aria-labelledby="dropdown">
									<a class="dropdown-item" href="../chat/chat2.jsp?receiverID=<%= board.getUserID() %>">채팅하기</a>
								</div>
							</div>
							<small><%= replyDto.getReplyDate() %></small>
							<p style="text-align:justify; white-space:pre-wrap; padding-top:10px; font-size:large;"><%= replyDto.getReplyContent() %></p>
							<div class="text-right">

								<a style="color: black;" onclick="return confirm('추천하시겠습니까?')" href="./likeReplyAction.jsp?replyID=<%= replyDto.getReplyID() %>">추천(<%= replyDto.getLikeCount() %>)</a>
<%
		if(userID.equals(replyDto.getUserID())){
%>
								 | <a style="color: gray;" onclick="return confirm('수정하시겠습니까?')" data-toggle="modal" href="#updateReplyModal<%= replyDto.getReplyID() %>">수정</a> |
								<a style="color: gray;" onclick="return confirm('삭제하시겠습니까?')" href="./deleteReplyAction.jsp?replyID=<%= replyDto.getReplyID() %>">삭제</a>
<%
		}
%>
							</div>
						</li>
<!-- 댓글 수정하기 모달 -->
					<div class="modal fade" id="updateReplyModal<%= replyDto.getReplyID() %>" tabindex="-1" role="dialog" aria-labelledby="modal">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="modal">댓글 수정</h5>
									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">
									<form method="post" action="./updateReplyAction.jsp?replyID=<%= replyDto.getReplyID() %>">
										<div class="form-row">
											<textarea name="replyContent" class="form-control" maxlength="2048" style="height: 180px;"><%= replyDto.getReplyContent() %></textarea>
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
<%
	}
%>
					</ul>
				</div>
			</div>
		</div>
	</section>



<!-- 게시글 수정하기 모달  -->
	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="modal">게시글 수정</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form method="post" action="./postUpdateAction.jsp?postID=<%= postID %>">
						<div class="form-row">
							<div class="form-group col-sm-4">
								<label>카테고리</label>
								<select name="postCategory" class="form-control">
									<option value="질문">질문</option>
									<option value="사담">사담</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label>제목</label>
							<input type="text" name="postTitle" class="form-control" maxlength="30" value="<%= board.getPostTitle() %>">
						</div>
						<div class="form-group">
							<label>내용</label>
							<textarea name="postContent" class="form-control" maxlength="2048" style="height: 180px;" ><%= board.getPostContent() %></textarea>
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

<!-- footer.jsp -->
<%@ include file="/WEB-INF/views/commons/footer.jsp" %>
</body>
</html>
