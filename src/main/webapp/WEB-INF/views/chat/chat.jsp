<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <!-- header.jsp -->
    <%@ include file="../commons/header.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat.css">
    <title>채팅 메인 페이지</title>
</head>
<body>
<!-- body.jsp -->
<%@ include file="../commons/body.jsp" %>

<!-- navigation.jsp -->
<%@ include file="../commons/navigation.jsp" %>

<!-- container -->
<section class="container mt-3 mb-5">
    <span class="text-left" id="pageInfo"><h2>채팅</h2></span>
    <div class="row clearfix mt-1">
        <!-- 왼쪽 컬럼 -->
        <div class="col-lg-3">
            <div class="card profile-section">
                <div class="profile d-flex align-items-center">
                    <img src="${pageContext.request.contextPath}/images/icon.png" alt="avatar" class="profile-img">
                    <div class="profile-info ml-3">
                        <div class="name">${memberID}</div>
                        <div class="status">${lastMessageTimeAgo}</div>
                    </div>
                </div>
                <div class="unread-messages mt-4">
                    <div class="card text-center">
                        <div class="card-header">
                            New Messages
                        </div>
                        <div class="card-body">
                            <h5 class="card-title">새로운 메시지</h5>
                            <p class="card-text">
                                <span id="unread" class="badge badge-info">
                                    <c:choose>
                                        <c:when test="${0 < unreadMessageCount}">
                                            ${unreadMessageCount}
                                        </c:when>
                                        <c:otherwise>0</c:otherwise>
                                    </c:choose>
                                </span>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 오른쪽 컬럼 -->
        <div class="col-lg-9">
            <div class="card chat-list-section">
                <div class="chat-list-header">
                    <h5>채팅 목록 (${chatList.size()})</h5>
                    <hr class="header-divider">
                </div>
                <div class="chat-list2" style="max-height: 400px; overflow-y: auto;">
                    <ul class="list-unstyled chat-list mt-2 mb-0" id="chatList">
                        <c:forEach var="chat" items="${chatList}">
                            <li class="clearfix d-flex align-items-center" onclick="window.location.href='${pageContext.request.contextPath}/chat/chatting/${chat.otherMemberID}'">
                                <img src="${pageContext.request.contextPath}/images/icon.png" alt="avatar" class="chat-img mr-3">
                                <div class="chat-about">
                                    <div class="name">${chat.otherMemberID}</div>
                                    <div class="message">${chat.message}
                                        <c:choose>
                                            <c:when test="${not empty chat.lastMessageTime and fn:substring(chat.lastMessageTime, 0, 10) == currentDate}">
                                                <fmt:formatDate value="${chat.lastMessageTime}" pattern="HH:mm"/>
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:formatDate value="${chat.lastMessageTime}" pattern="yyyy.MM.dd"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <c:if test="${chat.unreadCount > 0}">
                                    <span class="badge badge-danger ml-auto">${chat.unreadCount}</span>
                                </c:if>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- footer.jsp -->
<%@ include file="../commons/footer.jsp" %>
</body>
</html>
