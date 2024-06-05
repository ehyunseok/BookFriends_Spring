<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<!-- header.jsp -->
<%@ include file="/WEB-INF/views/commons/header.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat.css">
</head>

<body>
<!-- body.jsp -->
<%@ include file="/WEB-INF/views/commons/body.jsp" %>

<!-- navigation.jsp -->
<%@ include file="/WEB-INF/views/commons/navigation.jsp" %>

<!-- container  -->
<section class="container">
    <div class="ml-auto text-right">
        <a class="btn btn-danger mx-1 mt-2" data-toggle="modal" href="#reportModal">신고</a>
    </div>
    <div class="row clearfix mt-1">
        <div class="col-lg-12">
            <div class="card chat-app">
                <!-- 사용자가 채팅했던 이용자 리스트 -->
                <div id="plist" class="people-list">

                    <!-- 읽지 않은 메시지 알림 -->
                    <ul class="list-unstyled chat-list mt-2 mb-0" id="peopleList">
                        <li class="clearfix">
                            <img src="../images/icon.png" alt="avatar">
                            <div class="about">
                                <div class="name">userID</div>
                                <div class="status">
                                	<i class="fa fa-circle online"></i>online
                                </div>
                            </div>
                        </li>
                    </ul>
                    <ul class="list-unstyled chat-list mt-2 mb-0" id="peopleList">
                        <li class="clearfix">
                            <div class="about">
                            	<div class="card mt-5 text-center" style="width:200px;">
									  <div class="card-header">
									    New Message
									  </div>
									  <div class="card-body">
									    <h5 class="card-title">새로운 메시지</h5>
									    <p class="card-text"><span id="unread" class="badge badge-info">1</span></p>
									  </div>
								</div>
                            </div>
                        </li>
                    </ul>
                </div>
                <!-- 채팅창 -->
                <div class="chat">
                    <div class="chat-header clearfix">
                        <div class="row">
                            <div class="col-lg-6">
                                <a href="javascript:void(0);" data-toggle="modal" data-target="#view_info">
                                    <img src="../images/icon.png" alt="avatar">
                                </a>
                                <div class="chat-about">
                                    <h6 class="m-b-0">석이현</h6>
                                    <small>Last seen: 2 hours ago</small>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="chat-history">
                    	<ul class="m-b-0" id="chatList">
                    	</ul>
                    </div>
                    <div class="chat-message clearfix fixed">
                        <div class="input-group mb-0">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="sendsvg"></span>
                                <div class="clearfix"></div>
                            </div>
                            <textarea id="message" class="form-control" placeholder="메시지를 입력하세요..." maxlength="300" style="resize: none; overflow: auto;"></textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script>
function getUnread() {
	$.ajax({
		type:"POST",
		url:  '<%= request.getContextPath() %>/chatUnread',
		data: {
			userID: encodeURIComponent('<%= userID %>'),
		},
		success: function(result){
			if(result>=1){
				showUnread(result);
			} else{
				showUnread('');
			}
		}
	});
}
function getInfiniteUnread(){
	setInterval(function(){
		getUnread();
	}, 4000);
}
function showUnread(result){
	$('#unread').html(result);
}
</script>

<!-- 신고하기 모달 -->
<div class="modal fade" id="reportModal" tabindex="-1" role="dialog" aria-labelledby="modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modal">신고하기</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="../reportAction.jsp" method="post">
                    <div class="form-group">
                        <label>신고 제목</label>
                        <input type="text" name="reportTitle" class="form-control" maxlength="30">
                    </div>
                    <div class="form-group">
                        <label>신고 내용</label>
                        <textarea name="reportContent" class="form-control" maxlength="2048" style="height: 180px;"></textarea>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
                        <button type="submit" class="btn btn-danger">신고하기</button>
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
