document.addEventListener('DOMContentLoaded', function () {
    const contextPath = document.getElementById('contextPath').value; // contextPath를 가져오는 부분 추가

    function handleLikeButtonClick(event) {
        var target = event.target;
        var itemType = target.getAttribute('data-item-type');
        var itemId = target.getAttribute('data-item-id');
        var memberId = target.getAttribute('data-member-id');
        var csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        var csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        var requestUrl = `${contextPath}/board/like${itemType}/${itemId}`;
        console.log(`Requesting URL: ${requestUrl}`);

        fetch(requestUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                [csrfHeader]: csrfToken
            },
            body: `memberID=${memberId}`
        })
            .then(response => response.text())
            .then(data => {
                if (data === 'liked') {
                    alert('추천되었습니다.');
                    target.textContent = `추천(${parseInt(target.textContent.match(/\d+/)[0]) + 1})`;
                } else if (data === 'unliked') {
                    alert('추천이 취소되었습니다.');
                    target.textContent = `추천(${parseInt(target.textContent.match(/\d+/)[0]) - 1})`;
                }
            })
            .catch(error => console.error('Error:', error));
    }

    document.querySelectorAll('.like-button').forEach(function (button) {
        button.addEventListener('click', handleLikeButtonClick);
    });
});
