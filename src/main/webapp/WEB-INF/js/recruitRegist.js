document.addEventListener('DOMContentLoaded', function () {
    var csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    var csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    // Initialize Quill editor
    const quill = new Quill('#editor', {
        modules: {
            toolbar: [
                [{ header: [1, 2, 3, false] }],
                ['bold', 'italic', 'underline', 'strike'],
                ['image', 'link'],
                [{ list: 'ordered' }, { list: 'bullet' }]
            ]
        },
        theme: 'snow'
    });

    // Update hidden input field with editor content
    quill.on('text-change', function (delta, oldDelta, source) {
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

    // Handle image upload
    function selectLocalImage() {
        const fileInput = document.createElement('input');
        fileInput.setAttribute('type', 'file');
        fileInput.click();

        fileInput.addEventListener("change", function () {
            const formData = new FormData();
            const file = fileInput.files[0];
            formData.append('uploadFile', file);

            $.ajax({
                type: 'POST',
                enctype: 'multipart/form-data',
                url: contextPath + '/recruit/uploadImage', // 이미지 업로드를 처리할 서블릿 URL
                data: formData,
                processData: false,
                contentType: false,
                dataType: 'json',
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(csrfHeader, csrfToken);
                },
                success: function (response) {
                    const imageUrl = contextPath +  '/uploads/' + response.fileName;
                    const range = quill.getSelection();
                    quill.insertEmbed(range.index, 'image', imageUrl);
                },
                error: function () {
                    alert('이미지 업로드 실패');
                }
            });
        });
    }

    // Add image handler to toolbar
    quill.getModule('toolbar').addHandler('image', selectLocalImage);

    // 폼 제출할 때 해당 영역이 비어 있으면 제출 못하게 하기
    $('form').submit(function (event) {
        var recruitTitle = $('#recruitTitle').val();
        var quill_html = $('#quill_html').val();

        if(!recruitTitle || !quill_html){
            if(!recruitTitle){
                alert('제목이 작성되지 않았습니다.');
            } else {
                alert('내용이 작성되지 않았습니다.');
            }
            event.preventDefault();
        } else {
            alert('게시글이 작성되었습니다.');
        }
    });


});
