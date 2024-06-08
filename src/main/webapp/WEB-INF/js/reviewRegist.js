document.addEventListener('DOMContentLoaded', function () {
    var contextPath = document.querySelector('body').getAttribute('data-context-path');
    var csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    var csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    // 폼 제출할 때 해당 영역이 비어 있으면 제출 못하게 하기
    $('form').submit(function (event) {
        var category = $('#category').val();
        var reviewTitle = $('#reviewTitle').val();
        var reviewContent = $('#reviewContent').val();
        var bookName = $('#bookName').val();
        var authorName = $('#authorName').val();
        var reviewScore = $('#reviewScore').val();


        if(!category || category === '' ||
            !reviewTitle || !reviewContent ||
            !bookName || !authorName || !reviewScore ||
            reviewScore === 99 || reviewScore === '99'){
            if(!category || category === ''){
                alert('카테고리가 선택되지 않았습니다.');
            } else if(!reviewTitle){
                alert('제목이 작성되지 않았습니다.');
            } else if(!reviewContent) {
                alert('내용이 작성되지 않았습니다.');
            } else if(!bookName){
                alert('서명이 작성되지 않았습니다.');
            } else if(!authorName){
                alert('저자가 작성되지 않았습니다.');
            } else if(!reviewScore || reviewScore===99 || reviewScore==='99'){
                alert('평점이 선택되지 않았습니다.');
            }
            event.preventDefault();
        } else {
            alert('서평이 작성되었습니다.');
        }
    });


});
