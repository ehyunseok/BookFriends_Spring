<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //String userID = (String) session.getAttribute("userID");
    String userID = "sdf";
    String currentURL = request.getRequestURI();
%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">독서친구</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div id="navbar" class="collapse navbar-collapse">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item <%= currentURL.equals(request.getContextPath() + "/") ? " active" : "" %>">
                <a class="nav-link <%= currentURL.equals(request.getContextPath() + "/") ? " font-weight-bold" : "" %>"
                href="${pageContext.request.contextPath}/">메인</a>
            </li>
            <li class="nav-item <%= currentURL.equals(request.getContextPath() + "/review/bookReview") ? " active" : "" %>">
                <a class="nav-link <%= currentURL.equals(request.getContextPath() + "/review/bookReview") ? " font-weight-bold" : "" %>"
                href="${pageContext.request.contextPath}/review/bookReview">서평</a>
            </li>
            <li class="nav-item <%= currentURL.equals(request.getContextPath() + "/board/board") ? " active" : "" %>">
                <a class="nav-link <%= currentURL.equals(request.getContextPath() + "/board/board") ? " font-weight-bold" : "" %>"
                href="${pageContext.request.contextPath}/board/board">자유게시판</a>
            </li>
            <li class="nav-item <%= currentURL.equals(request.getContextPath() + "/recruit/recruit") ? " active" : "" %>">
                <a class="nav-link <%= currentURL.equals(request.getContextPath() + "/recruit/recruit") ? " font-weight-bold" : "" %>"
                href="${pageContext.request.contextPath}/recruit/recruit">독서모임</a>
            </li>
            <li class="nav-item <%= currentURL.equals(request.getContextPath() + "/market/market") ? " active" : "" %>">
                <a class="nav-link <%= currentURL.equals(request.getContextPath() + "/market/market") ? " font-weight-bold" : "" %>"
                href="${pageContext.request.contextPath}/market/market">중고장터</a>
            </li>
            <li class="nav-item <%= currentURL.equals(request.getContextPath() + "/chat/chat") ? " active" : "" %>">
                <a class="nav-link <%= currentURL.equals(request.getContextPath() + "/chat/chat") ? " font-weight-bold" : "" %>"
                href="${pageContext.request.contextPath}/chat/chat">채팅</a>
            </li>

            <!-- 로그인 상태 -->
            <% if(userID != null){ %>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown">
                    회원관리
                </a>
                <div class="dropdown-menu" aria-labelledby="dropdown">
                    <a class="dropdown-item" style="color: green;"><b>석이현</b> 님 환영합니다.</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/user/logout">로그아웃</a>
                </div>
            </li>
            <% } else { %>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown">
                    회원관리
                </a>
                <div class="dropdown-menu" aria-labelledby="dropdown">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/user/login">로그인</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/user/join">회원가입</a>
                </div>
            </li>
            <% } %>
        </ul>
    </div>
</nav>


