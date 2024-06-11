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
    <div class="card">
        <div class="card-header">
            <h5 class="card-title font-weight-bold mt-2">모집글 수정</h5>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/recruit/update/${recruit.recruitID}" method="post" enctype="multipart/form-data">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="form-group">
                    <select id="recruitStatus" name="recruitStatus" class="form-control">
                        <option value="">카테고리</option>
                        <option value="모집중" <c:if test="${recruit.recruitStatus == '모집중'}">selected</c:if>>모집중</option>
                        <option value="모집완료" <c:if test="${recruit.recruitStatus == '모집완료'}">selected</c:if>>모집완료</option>
                    </select>
                </div>
                <div class="form-group">
                    <input class="form-control" name="recruitTitle" placeholder="제목을 작성해주세요." id="recruitTitle">
                </div>
                <div class="form-group">
                    <div id="editor"></div>
                    <input type="hidden" id="quill_html" name="recruitContent">
                </div>
                <div class="row ml-auto mt-3">
                    <a class="btn btn-outline-secondary mr-1" href="${pageContext.request.contextPath}/recruit">취소</a>
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
</script>
<script>
    var contextPath = "${pageContext.request.contextPath}"; // jsp 표현식 언어로 현재 웹앱의 컨텍스트 경로를 반환함
    /*csrf 토큰과 csrf 헤더 이름을 추출함.
    document.querySelector 메소드를 사용하여 메타에서 contex 속성값을 가져옴.
    이 값들을 ajax 요청에 포함하여 csrf 보호를 수행하는 데 사용된다.*/
    var csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    var csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    //Quill 에디터 초기화
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

    //quill 에디터 동기화. 에디터에 작성한 내용을 input요소로 변경
    quill.on('text-change', function(delta, oldDelta, source){
        document.getElementById("quill_html").value = quill.root.innerHTML;
    });

    //HTML을 텍스트로 변환하는 함수
    function htmlToText(html) {
        var tempDiv = document.createElement("div");
        tempDiv.innerHTML = html;
        return tempDiv.textContent || tempDiv.innerText || "";
    }

    //수정 전 양식 설정 및 양식 초기화
    const initialData = {
        recruitTitle: `${recruit.recruitTitle}`,
        recruitContent: `${recruit.recruitContent}`,
    };
    console.log('initialData=' + initialData);
    const beforeForm = () => {
        document.querySelector('[name="recruitTitle"]').value = initialData.recruitTitle;
        quill.clipboard.dangerouslyPasteHTML(initialData.recruitContent);  // HTML 콘텐츠를 그대로 설정
    };
    beforeForm();

    // 로컬 이미지 선택 및 업로드 함수
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
                url: contextPath + '/recruit/uploadImage',
                data: formData,
                processData: false,
                contentType: false,
                dataType: 'json',
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(csrfHeader, csrfToken);
                },
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

    //Ajax 콜백 함수로 툴바의 이미지를 컨트롤함(이미지 업로드 핸들러 설정)
    quill.getModule('toolbar').addHandler('image', function(){
        selectLocalImage();
    });

    // 폼 제출할 때 해당 영역이 비어 있으면 제출 못하게 하기
    $('form').submit(function (event) {
        var recruitStatus = $('#recruitStatus').val();
        var recruitTitle = $('#recruitTitle').val();
        var quill_html = $('#quill_html').val();

        if(!recruitStatus || recruitStatus === '' || !recruitTitle || !quill_html){
            if(!recruitStatus || recruitStatus === ''){
                alert('모집상태가 선택되지 않았습니다.');
            } else if(!recruitTitle){
                alert('제목이 작성되지 않았습니다.');
            } else {
                alert('내용이 작성되지 않았습니다.');
            }
            event.preventDefault();
        } else {
            alert('게시글이 작성되었습니다.');
        }
    });
</script>

<!-- footer.jsp -->
<%@ include file="../commons/footer.jsp" %>
</body>
</html>
