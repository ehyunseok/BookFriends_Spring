<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<!-- header.jsp -->
<%@ include file="../commons/header.jsp" %>

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
<%@ include file="../commons/body.jsp" %>

<!-- navigation.jsp -->
<%@ include file="../commons/navigation.jsp" %>

<!-- container  -->
<section class="container mt-3 mb-5">
    <div class="">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/board/update/${board.postID}" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <select id="postCategory" name="postCategory">
                        <option value="">카테고리</option>
                        <option value="질문" <c:if test="${board.postCategory == '질문'}">selected</c:if>>질문</option>
                        <option value="사담" <c:if test="${board.postCategory == '사담'}">selected</c:if>>사담</option>
                    </select>
                </div>
                <div class="form-group">
                    <input class="" name="postTitle"
                           style="height: 50px; width:100%; border: none; background:transparent;">
                </div>
                <div class="form-group">
                    <div id="editor"></div>
                    <input type="hidden" id="quill_html" name="postContent" value="">
                </div>
                <div class="row ml-auto mt-3">
                    <a class="btn btn-outline-secondary mr-1" href="${pageContext.request.contextPath}/board">취소</a>
                    <button type="submit" class="btn btn-primary">작성</button>
                </div>
            </form>
        </div>
    </div>
</section>

<!-- Include the Quill library -->
<script src="https://cdn.jsdelivr.net/npm/quill@2.0.2/dist/quill.js"></script>
<script>
    var contextPath = "${pageContext.request.contextPath}";

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
        postTitle: `${board.postTitle}`,
        postContent: `${board.postContent}`,
    };
    console.log('initialData=' + initialData);
    const beforeForm = () => {
        document.querySelector('[name="postTitle"]').value = initialData.postTitle;
        quill.clipboard.dangerouslyPasteHTML(initialData.postContent);  // HTML 콘텐츠를 그대로 설정
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
                url: contextPath + '/board/uploadImage',
                data: formData,
                processData: false,
                contentType: false,
                dataType: 'json',
                success: function(data){
                    const range = quill.getSelection();
                    const imageUrl = contextPath + '/uploads/' + data.fileName;
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
<%@ include file="../commons/footer.jsp" %>
</body>
</html>
