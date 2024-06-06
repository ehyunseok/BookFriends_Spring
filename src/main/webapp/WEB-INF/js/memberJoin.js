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

    // 인증 코드 전송 버튼 클릭 시
    $('#sendCodeBtn').click(function (){
        var email = $('#memberEmail').val();
        if(!email) {    //이메일을 입력하지 않았을 때
            $('#authCodeFeedback').text('이메일을 입력해주세요.').css('color', 'orange');
            return;
        }
        $.post(contextPath + '/member/sendVerificationCode', {email: email}, function (data) {
            alert('인증 코드가 전송되었습니다.');
            $('#verificationSection').show();
        });
    });

    //인증 확인 버튼 클릭 시
    $('#verifyCodeBtn').click(function () {
       var email = $('#memberEmail').val();
       var code = $('#verificationCode').val();
       $.post(contextPath + '/member/verifyCode', {email: email, code: code}, function (data) {
           if(data){
               alert('인증이 완료되었습니다.');
               //인증 성공 상태를 세션 스토리지에 저장
               sessionStorage.setItem('emailVerified', 'true');
               $('#verificationSection').hide();
               $('#memberEmail').prop('readonly', true);
               $('#sendCodeBtn').hide();
               $('#authCodeFeedback').text('인증이 완료되었습니다.').css('color', 'green');
           } else {
               alert('인증 코드가 올바르지 않거나 만료되었습니다.');
               // 인증 실패 시 인증 상태 초기화
               sessionStorage.removeItem('emailVerified');
           }
       });
    });

    // 폼 제출 시
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
            event.preventDefault(); // 비어 있는 항목이 있으면 제출 못하게
        }
        //이메일 인증 여부 확인
        if( sessionStorage.getItem('emailVerified') !== 'true'){
            alert('이메일 인증을 완료해주세요.');
            event.preventDefault();
        }
        alert('회원가입이 완료되었습니다. 로그인해주세요.');
    });
});
