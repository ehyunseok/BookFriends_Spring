<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<!-- header.jsp -->
<%@ include file="/WEB-INF/views/commons/header.jsp" %>

<style>
    #editor {
        width: auto;
        margin: 0 auto;
        min-height: 300px;
    }
</style>

<!-- Quill styleSheet -->
<link href="https://cdn.jsdelivr.net/npm/quill@2.0.2/dist/quill.snow.css" rel="stylesheet" />

</head>

<body>
<!-- body.jsp -->
<%@ include file="/WEB-INF/views/commons/body.jsp" %>

<!-- navigation.jsp -->
<%@ include file="/WEB-INF/views/commons/navigation.jsp" %>

<!-- container  -->
<section class="container mt-5" style="max-width: 560px">
    <div class="">
        <div class="card-body">
            <form action="<%= request.getContextPath() %>/recruitWrite" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input class="" name="recruitTitle" placeholder="모집글 제목을 작성해주세요."
                           style="height: 50px; width:100%; border: none; background:transparent;"></input>
                </div>
                <div class="form-group">
                    <input name="recruitStatus" value="모집중" type="hidden">
                </div>
                <div class="form-group">
                	<div id="editor"></div>
                    <input type="hidden" id="quill_html" name="recruitContent" maxlength="2048">
                </div>
                <div class="row ml-auto mt-3">
                    <a class="btn btn-outline-secondary mr-1" href="./recruit.jsp">취소</a>
                    <button type="submit" class="btn btn-primary">작성</button>
                </div>
            </form>
        </div>
    </div>
</section>

<!-- Include the Quill library -->
<script src="https://cdn.jsdelivr.net/npm/quill@2.0.2/dist/quill.js"></script>

<!--  -->
<script>
//Initialize Quill editor
const quill = new Quill('#editor', {
	modules:{
		toolbar: [	//툴바 구성 custom
			[{header:[1,2,3, false]}],
			['bold','italic','underline','strike',],
			['image', 'link'],
			[{list:'ordered'},{list:'bullet'}]
		]
	},
	   	theme: 'snow'
});

//에디터에 작성한 내용을 input요소로 변경, 에디터의 내용이 변경될 때마다 quill_html 숨겨진 입력 필드에 에디터의 HTML 내용을 업데이트
quill.on('text-change', function(delta, oldDelta, source){
	document.getElementById("quill_html").value = quill.root.innerHTML;
});

//기본 양식
const initialData = {
	content: [
		{
			insert:
				'[독서 모임 모집 내용 예시]\n- 모임 주제 : \n- 모임 목표 : \n- 예상 모임 일정(횟수) : \n- 예상 모임 내용 간략히 : \n- 예상 모집 인원 : \n- 모임 소개와 개설 이유 : \n- 모임 관련 주의 사항 : \n- 모임에 지원할 수 있는 방법을 남겨주세요. (이메일, 카카오 오픈채팅방, 구글폼 등.)',
		},
	],
};
const basicForm = () => {quill.setContents(initialData.content);};
basicForm();


//로컬 이미지를 선택하고 업로드하는 기능 정의. 파일 선택 후 AJAX 요청으로 이미지를 서버에 업로드하고 에디터에 삽입함
function selectLocalImage(){
	const fileInput = document.createElement('input');
	fileInput.setAttribute('type', 'file');
	console.log("input.type" + fileInput.type);
	fileInput.click();


	fileInput.addEventListener("change", function(){
		const formData = new FormData();
		const file = fileInput.files[0];
		formData.append('uploadFile', file);

		$.ajax({
			type: 'POST',
			enctype: 'multipart/form-data',
			url: '<%= request.getContextPath() %>/display', // 이미지 업로드를 처리할 서블릿 URL
			data: formData,
			processData: false,
			contentType: false,
			dataType: 'json',
			success: function(response){
				const imageUrl = '<%= request.getContextPath() %>/display?fileName=' + response.fileName;
				const range = quill.getSelection();
				console.log("Inserting image at index: ", range.index, " with URL: ", imageUrl); // 디버그 로그
				quill.insertEmbed(range.index, 'image', imageUrl);
			},
			error: function(){
				alert('이미지 업로드 실패');
			}
		});
	});
}

// 툴바의 이미지 버튼에 대한 이벤트 핸들러를 추가함 -> 로컬 이미지를 선택하고 업로드할 수 있게~
quill.getModule('toolbar').addHandler('image', function(){
	selectLocalImage();
});
</script>


<!-- footer.jsp -->
<%@ include file="/WEB-INF/views/commons/footer.jsp" %>
</body>
</html>
