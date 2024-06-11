$(document).ready(function() {
    var seenChatIDs = new Set(); // 중복 메시지 추적을 위한 Set
    var lastID = parseInt($('#lastID').val(), 10); // 초기 로드 시의 마지막 메시지 ID

    function addChat(chatID, senderID, message, formattedChatTime) {
        if (seenChatIDs.has(chatID)) return; // 이미 본 메시지는 건너뜀

        seenChatIDs.add(chatID);

        var chatList = $('#chatList');
        var alignClass = senderID === $('#senderID').val() ? 'align-left' : 'align-right';
        var messageClass = senderID === $('#senderID').val() ? 'my-message' : 'other-message';
        var infoClass = senderID === $('#senderID').val() ? 'info-left' : 'info-right';
        var senderName = senderID === $('#senderID').val() ? 'me' : senderID;

        var chatItem = `
            <li class="clearfix ${alignClass}" data-chat-id="${chatID}">
                <div class="message-data ${infoClass}">
                    ${senderID !== $('#senderID').val() ? '<div class="avatar"><img src="' + contextPath + '/images/icon.png" alt="avatar"></div>' : ''}
                    <div class="message-info">
                        <span class="sender-id">${senderName}</span>
                        <span class="time">${formattedChatTime}</span>
                    </div>
                </div>
                <div class="message ${messageClass}">${message}</div>
            </li>
        `;
        chatList.append(chatItem);

        // 마지막 메시지 ID 업데이트
        $('#lastID').val(chatID);
    }

    function scrollToBottom() {
        var chatList = $('#chatList');
        chatList.scrollTop(chatList[0].scrollHeight);
    }

    $("#peopleList").on("click", "li", function() {
        var url = $(this).data("url");
        window.location.href = encodeURI(url);
    });

    $('#sendsvg').click(function() {
        var message = $('#message').val().trim();
        if (message.length === 0) {
            $('#dangerModal').modal('show');
        } else {
            submitFunction();
        }
    });

    $('#message').keypress(function(event) {
        if (event.which === 13 && !event.shiftKey) {
            event.preventDefault();
            var message = $('#message').val().trim();
            if (message.length > 0) {
                submitFunction();
            }
        }
    });

    function handleServerResponse(result) {
        switch (result) {
            case "1":
                $('#successModal').modal('show');
                break;
            case "0":
                $('#dangerModal').modal('show');
                break;
            default:
                $('#warningModal').modal('show');
                break;
        }
    }

    function submitFunction() {
        var senderID = $('#senderID').val();
        var receiverID = $('#receiverID').val();
        var message = $('#message').val();
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            type: "POST",
            url: contextPath + "/chat/sendMessage",
            data: {
                senderID: senderID,
                receiverID: receiverID,
                message: message
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(result) {
                result = result.trim();
                handleServerResponse(result);
                if (result == "1") {
                    $('#message').val('');
                    scrollToBottom();
                }
            },
            error: function(xhr, status, error) {
                console.error("Error 발생: " + status + ", " + error);
            }
        });
    }

    function chatListFunction(type, loadAll = false) {
        var memberID = $('#senderID').val();
        var receiverID = $('#receiverID').val();
        var lastIDParam = loadAll ? null : lastID;
        $.ajax({
            type: "GET",
            url: contextPath + "/chat/getChatHistory",
            data: {
                senderID: memberID,
                receiverID: receiverID,
                listType: type,
                lastID: lastIDParam
            },
            success: function(data) {
                console.log("서버 응답 데이터:", data);
                if (data == "") return;
                var result = data;
                for (var i = 0; i < result.length; i++) {
                    addChat(result[i].chatID, result[i].sender.memberID, result[i].message, result[i].formattedChatTime);
                }
                if (loadAll) {
                    if (result.length > 0) {
                        lastID = result[result.length - 1].chatID;
                        $('#lastID').val(lastID);
                    }
                } else {
                    if (result.length > 0) {
                        lastID = Math.max(lastID, result[result.length - 1].chatID);
                    }
                }
                scrollToBottom();
            },
            error: function(xhr, status, error) {
                console.error("Error 발생: " + status + ", " + error);
            }
        });
    }

    function getInfiniteChat() {
        setInterval(function() {
            chatListFunction('ten');
        }, 300000);
    }

    function markAsRead() {
        var senderID = $('#senderID').val();
        var receiverID = $('#receiverID').val();
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            type: "POST",
            url: contextPath + "/chat/markAsRead",
            data: {
                senderID: senderID,
                receiverID: receiverID
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(result) {
                console.log("메시지가 읽음 처리되었습니다.");
            },
            error: function(xhr, status, error) {
                console.error("Error 발생: " + status + ", " + error);
            }
        });
    }

    chatListFunction('all', true);
    getInfiniteChat();
    markAsRead();
    scrollToBottom();
});
