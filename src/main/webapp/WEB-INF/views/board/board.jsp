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
    <form method="get" action="${pageContext.request.contextPath}/board/board" class="form-inline mt-3">
        <select name="postCategory" class="form-control mx-1 mt-2">
            <option value="전체">전체</option>
            <option value="질문" <% if(postCategory.equals("질문")) out.println("selected"); %>>질문</option>
            <option value="사담" <% if(postCategory.equals("사담")) out.println("selected"); %>>사담</option>
        </select>
        <select name="searchType" class="form-control mx-1 mt-2">
            <option value="최신순" <% if(searchType.equals("최신순")) out.println("selected"); %>>최신순</option>
            <option value="조회수순" <% if(searchType.equals("조회수순")) out.println("selected"); %>>조회수순</option>
            <option value="추천순" <% if(searchType.equals("추천순")) out.println("selected"); %>>추천순</option>
        </select>
        <input type="text" name="search" class="form-control mx-1 mt-2" placeholder="내용을 입력해주세요." value="<%= search %>">
        <button type="submit" class="btn btn-dark mx-1 mt-2">검색</button>
        <div class="ml-auto">
            <a class="btn btn-primary mx-1 mt-2" data-toggle="modal" href="#registerModal">작성하기</a>
            <a class="btn btn-outline-danger mx-1 mt-2" data-toggle="modal" href="#reportModal">신고</a>
        </div>
    </form>
    <div class="card bg-light mt-3">
        <table class="table table-hover">
            <thead>
                <tr>
                    <th scope="col" style="">#</th>
                    <th scope="col" style="">카테고리</th>
                    <th scope="col" style="">제목</th>
                    <th scope="col" style="">작성자</th>
                    <th scope="col" style="">작성일</th>
                    <th scope="col" style="">조회수</th>
                </tr>
            </thead>
            <tbody>
<%
//for(BoardDto board : boardList) {
%>

                <!-- 해당 게시글 번호 페이지로 이동 -->
                <tr onclick="#">
                    <th scope="row">board.getPostID</th>
                    <td>board.getPostCategory()</td>
                    <td>board.getPostTitle()<small>(추천: board.getLikeCount() )</small></td>
                    <td>board.getUserID()</td>
                    <td>SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                           out.println(sdf.format(board.getPostDate()));
                    </td>
                    <td><%= board.getViewCount() %></td>
                </tr>
<%
//}
%>
            </tbody>
        </table>
    </div>




<!-- pagination -->
    <nav aria-label="Page navigation" >
      <ul class="pagination justify-content-center mt-3" style="padding-bottom: 3px;">
        <li class="page-item <% if(pageNumber == 0) out.print("disabled"); %>">
            <a class="page-link" href="?postCategory=<%= postCategory %>&searchType=<%= searchType %>&search=<%= search %>&pageNumber=<%= pageNumber - 1 %>">이전</a>
        </li>
<%
for (int i = startPage; i <= endPage; i++) {
%>
        <li class="page-item<% if(i == pageNumber + 1) out.print("active"); %>">
            <a class="page-link" href="./board.jsp?postCategory=<%= URLEncoder.encode(postCategory, "UTF-8") %>&searchType=<%= URLEncoder.encode(searchType, "UTF-8") %>&search=<%= URLEncoder.encode(search, "UTF-8") %>&pageNumber=<%= i - 1 %>"><%= i %></a>
        </li>
<% }
%>
        <li class="page-item <% if(pageNumber >= totalPages - 1) out.print("disabled"); %>">
            <a class="page-link" href="?postCategory=<%= postCategory %>&searchType=<%= searchType %>&search=<%= search %>&pageNumber=<%= pageNumber + 1 %>">다음</a>
        </li>
      </ul>
    </nav>
</section>

<!-- 게시글 등록하기 모달  -->
<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modal">게시글 작성</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form method="post" action="#">
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
                        <input type="text" name="postTitle" class="form-control" maxlength="30">
                    </div>
                    <div class="form-group">
                        <label>내용</label>
                        <textarea name="postContent" class="form-control" maxlength="2048" style="height: 180px;"></textarea>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
                        <button type="submit" class="btn btn-primary">등록</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<!-- 신고하기 모달  -->
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
                <form action="#" method="post">
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
