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
//에디터에 작성한 내용을 input요소로 변경
quill.on('text-change', function(delta, oldDelta, source){
	document.getElementById("quill_html").value = quill.root.innerHTML;
});

//HTML을 텍스트로 변환하는 함수
function htmlToText(html) {
    var tempDiv = document.createElement("div");
    tempDiv.innerHTML = html;
    return tempDiv.textContent || tempDiv.innerText || "";
}

//수정 전 양식
const initialData = {
	recruitTitle: '<%= recruit.getRecruitTitle() %>',
	content: '<%= recruit.getRecruitContent().replaceAll("\n", "\\n").replaceAll("\r", "\\r").replaceAll("\"", "\\\"") %>',  // 문자열 이스케이프
};
const beforeForm = () => {
	document.querySelector('[name="recruitTitle"]').value = initialData.recruitTitle;
	quill.clipboard.dangerouslyPasteHTML(initialData.content);  // HTML 콘텐츠를 그대로 설정
};
beforeForm();


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
			url: 'display', // 이미지 업로드를 처리할 서블릿 URL
			data: formData,
			processData: false,
			contentType: false,
			dataType: 'json',
			success: function(data){
				const range = quill.getSelection();
				const imageUrl = 'display?fileName=' + data.fileName;
				console.log("Inserting image at index: ", range.index, " with URL: ", imageUrl); // 디버그 로그
				quill.insertEmbed(range.index, 'image', imageUrl);
			},
			error: function(err){
				console.log(err);
			}
		});
	});
}

//Ajax 콜백 함수로 툴바의 이미지를 컨트롤함
quill.getModule('toolbar').addHandler('image', function(){
	selectLocalImage();
});
</script>


<!-- footer.jsp -->
<%@ include file="/WEB-INF/views/commons/footer.jsp" %>
</body>
</html>
