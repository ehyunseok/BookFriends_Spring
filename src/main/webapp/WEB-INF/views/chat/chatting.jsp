<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <!-- fn 태그 라이브러리 선언 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> <!-- fmt 태그 라이브러리 선언 -->
<!DOCTYPE html>
<html>
<head>
    <!-- header.jsp 포함 -->
    <%@ include file="../commons/header.jsp" %>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat.css">
    <!-- 외부 JS 파일 포함 -->
    <script>
        var contextPath = '${pageContext.request.contextPath}';
    </script>
    <script src="${pageContext.request.contextPath}/js/chat.js" defer></script>
</head>

<body>
<!-- body.jsp 포함 -->
<%@ include file="../commons/body.jsp" %>

<!-- hidden input fields -->
<input type="hidden" id="senderID" value="${memberID}">
<input type="hidden" id="receiverID" value="${receiverID}">
<input type="hidden" id="lastID" value="${lastID}">

<!-- navigation.jsp 포함 -->
<%@ include file="../commons/navigation.jsp" %>

<!-- 컨테이너 -->
<section class="container mt-3 mb-5">
    <span class="text-left" id="pageInfo"><h2>채팅</h2></span>
    <div class="row clearfix mt-1">
        <div class="col-lg-12">
            <div class="card chat-app">
                <!-- 채팅 사용자 리스트 -->
                <div id="plist" class="people-list">
                    <!-- 검색창 -->
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="glass"></span>
                        </div>
                        <input type="text" class="form-control" placeholder="Search...">
                    </div>
                    <!-- 사용자 목록 -->
                    <ul class="list-unstyled chat-list mt-2 mb-0" id="peopleList">
                        <c:forEach var="chatFriend" items="${chatFriends}">
                            <c:set var="activeClass" value="${receiverID == chatFriend.memberID ? ' active' : ''}" />
                            <li class="clearfix${activeClass}" data-url="${pageContext.request.contextPath}/chat/chatting/${chatFriend.memberID}">
                                <img src="${pageContext.request.contextPath}/images/icon.png" alt="avatar">
                                <div class="about">
                                    <div class="name">${chatFriend.memberID}</div>
                                    <div class="status">
                                        <c:choose>
                                            <c:when test="${fn:substring(chatFriend.lastMessageTime, 0, 10) == fn:substring(currentDate, 0, 10)}">
                                                <fmt:formatDate value="${chatFriend.lastMessageTime}" pattern="HH:mm"/>
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:formatDate value="${chatFriend.lastMessageTime}" pattern="yyyy.MM.dd"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <!-- 채팅창 -->
                <div class="chat">
                    <div class="chat-header clearfix">
                        <div class="row">
                            <div class="col-lg-6">
                                <a href="javascript:void(0);" data-toggle="modal" data-target="#view_info">
                                    <img src="${pageContext.request.contextPath}/images/icon.png" alt="avatar">
                                </a>
                                <div class="chat-about">
                                    <h6 class="m-b-0">${receiverID}</h6>
                                    <small style="color: gray">실시간 채팅</small>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="chat-history" id="chatHistory">
                        <ul class="m-b-0" id="chatList">
                            <c:forEach var="chat" items="${chatHistory}">
                                <li class="clearfix ${chat.sender.memberID == memberID ? ' align-left' : ' align-right'}" data-chat-id="${chat.chatID}">
                                    <div class="message-data ${chat.sender.memberID == memberID ? 'info-left' : 'info-right'}">
                                        <c:if test="${chat.sender.memberID != memberID}">
                                            <div class="avatar"><img src="${pageContext.request.contextPath}/images/icon.png" alt="avatar"></div>
                                        </c:if>
                                        <div class="message-info">
                                            <span class="sender-id">${chat.sender.memberID == memberID ? 'me' : chat.sender.memberID}</span>
                                            <span class="time">${chat.formattedChatTime}</span>
                                        </div>
                                    </div>
                                    <div class="message ${chat.sender.memberID == memberID ? 'my-message' : 'other-message'}">${chat.message}</div>
                                </li>
                            </c:forEach>
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

<!-- 메시지 작성 응답 -->
<div class="modal fade" id="successModal" tabindex="-1" role="dialog" aria-labelledby="successModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content" id="modalS">
            <div class="modal-body text-center">
                메시지 전송 성공!
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="warningModal" tabindex="-1" role="dialog" aria-labelledby="warningModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content" id="modalW">
            <div class="modal-body text-center">
                데이터베이스 오류 발생!
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="dangerModal" tabindex="-1" role="dialog" aria-labelledby="dangerModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content" id="modalD">
            <div class="modal-body text-center">
                메시지를 입력해주세요.
            </div>
        </div>
    </div>
</div>

<%
    String message = null;
    if (session.getAttribute("message") != null) {
        message = (String) session.getAttribute("message");
    }
    String messageType = null;
    if (session.getAttribute("messageType") != null) {
        messageType = (String) session.getAttribute("messageType");
    }
    if (message != null) {
%>
<div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="vertical-alignment-helper">
        <div class="modal-dialog vertical-align-center">
            <div class="modal-content <% if (messageType.equals("오류메시지")) out.println("panel-warning"); %>">
                <div class="modal-header panel-heading">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden>&times;</span>
                        <span class="sr-only">close</span>
                    </button>
                    <h4 class="modal-title"><%= messageType %></h4>
                </div>
                <div class="modal-body">
                    <%= message %>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $('#messageModal').modal("show");
</script>
<%
        session.removeAttribute("message");
        session.removeAttribute("messageType");
    }
%>

<!-- footer.jsp 포함 -->
<%@ include file="../commons/footer.jsp" %>
</body>
</html>
