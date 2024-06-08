document.addEventListener('DOMContentLoaded', function () {
    // 댓글 작성 폼 제출 시 검증
    $('form#newReplyForm').submit(function (event) {
        var replyContent = $(this).find('textarea[name="replyContent"]').val();
        if (!replyContent.trim()) {
            alert('내용이 작성되지 않았습니다.');
            event.preventDefault();
        } else {
            alert('댓글이 작성되었습니다.');
        }
    });

    // 댓글 수정 폼 제출 시 검증
    $('form.updateReplyForm').submit(function (event) {
        var replyContent = $(this).find('textarea[name="replyContent"]').val();
        if (!replyContent.trim()) {
            alert('내용이 작성되지 않았습니다.');
            event.preventDefault();
        } else {
            alert('댓글이 수정되었습니다.');
        }
    });
});
