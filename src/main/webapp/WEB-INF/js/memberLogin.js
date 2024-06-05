$(document).ready(function () {

    $('form').submit(function (event) {
        var memberID = $('#memberID').val();
        var memberPassword = $('#memberPassword').val();

        if(!memberID || !memberPassword) {
            if(!memberID){
                alert('아이디를 작성해주세요.');
            } else {
                alert('비밀번호를 작성해주세요.');
            }
            event.preventDefault();
        }
    });
});