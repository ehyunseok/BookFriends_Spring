$(document).ready(function () {
    var contextPath = $('body').data('context-path');
    var csrfToken = $('meta[name="_csrf"]').attr('content');
    var csrfHeader = $('meta[name="_csrf_header"]').attr('content');

    // 아이디 중복시 데이터 제출 막기
    var isMemberIDAvailable = false;
    // 인증코드 미인증 시 데이터 제출 막기
    var isCheckedEmail = false;

    $('#checkMemberIDBtn').click(function () {
        var memberID = $('#memberID').val();
        if (!memberID) {
            $('#memberIDFeedback').text('아이디를 입력해주세요.').css('color', 'red');
            isMemberIDAvailable = false;
            return;
        }
        $.ajax({
            url: contextPath + '/member/checkMemberID',
            type: 'GET',
            data: {memberID: memberID},
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (response) {
                if (response) {
                    $('#memberIDFeedback').text('이미 존재하는 아이디입니다.').css('color', 'red');
                    isMemberIDAvailable = false;
                } else {
                    $('#memberIDFeedback').text('사용 가능한 아이디입니다.').css('color', 'green');
                    isMemberIDAvailable = true;
                }
            },
            error: function (xhr, status, error) {
                console.error("Error: " + error);
                $('#memberIDFeedback').text('아이디 중복 확인 중 오류가 발생했습니다.').css('color', 'red');
                isMemberIDAvailable = false;
            }
        });
    });

    $('#sendCodeBtn').click(function () {
        var memberEmail = $('#memberEmail').val();
        if (!memberEmail) {
            $('#authCodeFeedback').text('이메일을 입력해주세요.').css('color', 'orange');
            isCheckedEmail = false;
            return;
        }
        $.ajax({
            url: contextPath + '/member/sendVerificationCode',
            type: 'POST',
            data: {memberEmail: memberEmail},
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                $('#authCodeFeedback').hide();
                alert('인증 코드가 전송되었습니다.');
                $('#verificationSection').show();
                isCheckedEmail = false;
            },
            error: function (xhr, status, error) {
                console.error("Error: " + error);
                $('#authCodeFeedback').text('인증 코드 전송 중 오류가 발생했습니다.').css('color', 'red');
                isCheckedEmail = false;
            }
        });
    });

    $('#verifyCodeBtn').click(function () {
        var memberEmail = $('#memberEmail').val();
        var authCode = $('#verificationCode').val();
        $.ajax({
            url: contextPath + '/member/verifyCode',
            type: 'POST',
            data: {memberEmail: memberEmail, authCode: authCode},
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                if (data) {
                    alert('인증이 완료되었습니다.');
                    sessionStorage.setItem('emailVerified', 'true');
                    $('#verificationSection').hide();
                    $('#memberEmail').prop('readonly', true);
                    $('#sendCodeBtn').hide();
                    $('#authCodeFeedback').text('인증이 완료되었습니다.').css('color', 'green').show();
                    isCheckedEmail = true;
                } else {
                    alert('인증 코드가 올바르지 않거나 만료되었습니다.');
                    isCheckedEmail = false;
                    sessionStorage.removeItem('emailVerified');
                }
            },
            error: function (xhr, status, error) {
                console.error("Error: " + error);
                alert('인증 코드 확인 중 오류가 발생했습니다.');
            }
        });
    });

    $('form').submit(function(event) {
        var memberID = $('#memberID').val();
        var memberPassword = $('#memberPassword').val();
        var memberEmail = $('#memberEmail').val();

        if (!memberID || !memberPassword || !memberEmail) {
            if (!memberID) {
                alert('아이디를 작성해주세요.');
            } else if (!memberPassword) {
                alert('비밀번호를 작성해주세요.');
            } else {
                alert('이메일을 작성해주세요.');
            }
            event.preventDefault();
        } else if (sessionStorage.getItem('emailVerified') !== 'true') {
            alert('이메일 인증을 완료해주세요.');
            event.preventDefault();
        } else if(!isMemberIDAvailable) {
            alert('중복된 아이디입니다. 다른 아이디를 사용해주세요.');
            event.preventDefault();
        } else if(!isCheckedEmail){
            alert('이메일 인증을 완료해주세요.');
            event.preventDefault();
        }else {
            alert('회원가입이 완료되었습니다. 로그인해주세요.');
        }
    });
});
