$(document).ready(function () {
    var contextPath = $('body').data('context-path');

    $('#checkMemberIDBtn').click(function () {
        var memberID = $('#memberID').val();
        if (!memberID) {
            $('#memberIDFeedback').text('아이디를 입력해주세요.').css('color', 'red');
            return;
        }
        $.ajax({
            url: contextPath + '/member/checkMemberID', // 수정된 부분
            type: 'GET',
            data: {memberID: memberID},
            success: function (response) {
                if (response) {
                    $('#memberIDFeedback').text('이미 존재하는 아이디입니다.').css('color', 'red');
                } else {
                    $('#memberIDFeedback').text('사용 가능한 아이디입니다.').css('color', 'green');
                }
            },
            error: function () {
                $('#memberIDFeedback').text('아이디 중복 확인 중 오류가 발생했습니다.').css('color', 'red');
            }
        });
    });

    $('form').submit(function(event){
        var memberID = $('#memberID').val();
        var memberPassword = $('#memberPassword').val();
        var memberEmail = $('#memberEmail').val();

        if(!memberID || !memberPassword || !memberEmail){
            if(!memberID){
                alert('아이디를 작성해주세요.');
            } else if(!memberPassword){
                alert('비밀번호를 작성해주세요.');
            } else {
                alert('이메일을 작성해주세요.');
            }
            event.preventDefault();
        }
    });
});
