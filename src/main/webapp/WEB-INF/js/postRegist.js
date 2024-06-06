document.addEventListener('DOMContentLoaded', function () {

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
});
