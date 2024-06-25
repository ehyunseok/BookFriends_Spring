$(document).ready(function () {
    var errorMessage = $('#errorMessage').val();
    if (errorMessage) {
        if (errorMessage === 'not_found') {
            alert('아이디를 찾을 수 없습니다.');
        } else if (errorMessage === 'incorrect_password') {
            alert('비밀번호가 일치하지 않습니다.');
        } else {
            alert(errorMessage);
        }
    }

    $('#loginBtn').click(function (event) {
        var memberID = $('#memberID').val();
        var memberPassword = $('#memberPassword').val();

        if (!memberID || !memberPassword) {
            if (!memberID) {
                alert('아이디를 작성해주세요.');
            } else {
                alert('비밀번호를 작성해주세요.');
            }
            event.preventDefault();
        }
    });
});
