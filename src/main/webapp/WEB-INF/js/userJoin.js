$(document).ready(function () {
    var contextPath = $('body').data('context-path');

    $('#checkUserIdBtn').click(function () {
        var userId = $('#userID').val();
        if (!userId) {
            $('#userIdFeedback').text('아이디를 입력해주세요.').css('color', 'red');
            return;
        }
        $.ajax({
            url: contextPath + '/user/checkUserID', // 수정된 부분
            type: 'GET',
            data: {userID: userId},
            success: function (response) {
                if (response) {
                    $('#userIdFeedback').text('이미 존재하는 아이디입니다.').css('color', 'red');
                } else {
                    $('#userIdFeedback').text('사용 가능한 아이디입니다.').css('color', 'green');
                }
            },
            error: function () {
                $('#userIdFeedback').text('아이디 중복 확인 중 오류가 발생했습니다.').css('color', 'red');
            }
        });
    });

    $('form').submit(function(event){
        var userID = $('#userID').val();
        var userPassword = $('#userPassword').val();
        var userEmail = $('#userEmail').val();

        if(!userID || !userPassword || !userEmail){
            if(!userID){
                alert('아이디를 작성해주세요.');
            } else if(!userPassword){
                alert('비밀번호를 작성해주세요.');
            } else{
                alert('이메일을 작성해주세요.');
            }
            event.preventDefault();
        }
    });
});
