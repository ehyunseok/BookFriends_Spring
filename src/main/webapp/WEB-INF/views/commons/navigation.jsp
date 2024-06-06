<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
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
            <li class="nav-item <%= currentURL.equals(request.getContextPath() + "/review") ? " active" : "" %>">
                <a class="nav-link <%= currentURL.equals(request.getContextPath() + "/review") ? " font-weight-bold" : "" %>"
                href="${pageContext.request.contextPath}/review">서평</a>
            </li>
            <li class="nav-item <%= currentURL.equals(request.getContextPath() + "/board") ? " active" : "" %>">
                <a class="nav-link <%= currentURL.equals(request.getContextPath() + "/board") ? " font-weight-bold" : "" %>"
                href="${pageContext.request.contextPath}/board">자유게시판</a>
            </li>
            <li class="nav-item <%= currentURL.equals(request.getContextPath() + "/recruit") ? " active" : "" %>">
                <a class="nav-link <%= currentURL.equals(request.getContextPath() + "/recruit") ? " font-weight-bold" : "" %>"
                href="${pageContext.request.contextPath}/recruit">독서모임</a>
            </li>
            <li class="nav-item <%= currentURL.equals(request.getContextPath() + "/market") ? " active" : "" %>">
                <a class="nav-link <%= currentURL.equals(request.getContextPath() + "/market") ? " font-weight-bold" : "" %>"
                href="${pageContext.request.contextPath}/market">중고장터</a>
            </li>
            <li class="nav-item <%= currentURL.equals(request.getContextPath() + "/chat") ? " active" : "" %>">
                <a class="nav-link <%= currentURL.equals(request.getContextPath() + "/chat") ? " font-weight-bold" : "" %>"
                href="${pageContext.request.contextPath}/chat">채팅</a>
            </li>

            <!-- 로그인 상태 -->
            <c:choose>
                <c:when test="${not empty sessionScope.SPRING_SECURITY_CONTEXT}">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown">
                            회원관리
                        </a>
                        <div class="dropdown-menu" aria-labelledby="dropdown">
                            <a class="dropdown-item" style="color: green;">
                                <b><c:out value="${pageContext.request.userPrincipal.name}"/></b> 님 환영합니다.
                            </a>
                            <form action="${pageContext.request.contextPath}/member/logout" method="post">
                                <button type="submit" class="dropdown-item">로그아웃</button>
                            </form>
                        </div>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown">
                            회원관리
                        </a>
                        <div class="dropdown-menu" aria-labelledby="dropdown">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/member/login">로그인</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/member/join">회원가입</a>
                        </div>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>
