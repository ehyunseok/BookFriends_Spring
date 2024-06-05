<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<!-- header.jsp -->
	<%@ include file="../commons/header.jsp" %>
</head>
<body>
<!-- navigation.jsp -->
<%@ include file="../commons/navigation.jsp" %>
<!-- container  -->
	<section class="container mt-5 text-center" style="max-width: 560px">
		<div class="alert alert-warning mt-5" role="alert">
			⚠️
			<br>이메일 인증이 완료되어야 이용할 수 있습니다. 
			<br>인증 메일을 받지 못 하셨나요?
		</div>
		<a href="" class="btn btn-primary btn-block">인증 메일 다시 받기</a>
	</section>


<%@ include file="../commons/footer.jsp" %>
</body>
</html>