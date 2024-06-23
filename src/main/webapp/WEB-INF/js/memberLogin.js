$(document).ready(function () {
    // URL에서 파라미터 읽기
    function getUrlParameter(name) {
        name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
        var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
        var results = regex.exec(location.search);
        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
    }

    // 로그인 실패 시 알림창 표시
    if (getUrlParameter('error')) {
        alert('아이디 또는 비밀번호를 다시 입력해주세요.');
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
