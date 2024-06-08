document.addEventListener('DOMContentLoaded', function () {
    var contextPath = document.querySelector('body').getAttribute('data-context-path');
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
                url: contextPath + '/board/uploadImage', // 이미지 업로드를 처리할 서블릿 URL
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
        var postCategory = $('#postCategory').val();
        var postTitle = $('#postTitle').val();
        var quill_html = $('#quill_html').val();

        if(!postCategory || postCategory === '' || !postTitle || !quill_html){
            if(!postCategory || postCategory === ''){
                alert('카테고리가 선택되지 않았습니다.');
            } else if(!postTitle){
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
