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
        var chatHistory = $('#chatHistory');
        setTimeout(function() {
            chatHistory.scrollTop(chatHistory[0].scrollHeight);
        }, 100); // DOM 변경 후 스크롤 이동을 보장하기 위해 약간의 지연 추가
    }

    $("#peopleList").on("click", "li", function() {
        var url = $(this).data("url");
        window.location.href = encodeURI(url);
    });

    // 이벤트 핸들러 중복 등록 방지
    $('#sendsvg').off('click').on('click', function() {
        var message = $('#message').val().trim();
        if (message.length === 0) {
            $('#dangerModal').modal('show');
        } else {
            submitFunction();
        }
    });

    $('#message').off('keypress').on('keypress', function(event) {
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
                var trimmedResult = (typeof result === 'string' ? result.trim() : JSON.stringify(result).trim());
                if (trimmedResult == "1") {
                    $('#message').val('');
                    chatListFunction('recent', false, function() {
                        scrollToBottom(); // 메시지 전송 후 새 메시지 추가 후 스크롤 이동
                    });
                    $('#successModal').modal('show');
                } else {
                    $('#dangerModal').modal('show');
                }
            },
            error: function(xhr, status, error) {
                console.error("Error 발생: " + status + ", " + error);
            }
        });
    }

    function chatListFunction(type, loadAll = false, callback) {
        var memberID = $('#senderID').val();
        var receiverID = $('#receiverID').val();
        var lastIDParam = loadAll ? null : (lastID || null);

        console.log("Requesting chat history with parameters:");
        console.log("senderID: " + memberID);
        console.log("receiverID: " + receiverID);
        console.log("lastID: " + lastIDParam);

        if (loadAll) {
            $('#chatList').empty();
        }

        $.ajax({
            type: "GET",
            url: contextPath + "/chat/getChatHistory",
            data: {
                senderID: memberID,
                receiverID: receiverID,
                listType: type,
                lastID: lastIDParam
            },
            success: function(result) {
                console.log("서버 응답 데이터:", result);
                if (!result || result.length === 0) return;
                for (var i = 0; i < result.length; i++) {
                    addChat(result[i].chatID, result[i].sender.memberID, result[i].message, result[i].formattedChatTime);
                }
                if (result.length > 0) {
                    lastID = result[result.length - 1].chatID;
                    $('#lastID').val(lastID);
                }
                if (typeof callback === 'function') {
                    callback();
                }
            },
            error: function(xhr, status, error) {
                console.error("Error 발생: " + status + ", " + error);
                console.error("Response Text: " + xhr.responseText);
            }
        });
    }

    function getInfiniteChat() {
        setInterval(function() {
            chatListFunction('ten');
        }, 3000);
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

    chatListFunction('all', true, function() {
        scrollToBottom(); // 초기 로드 시 스크롤을 맨 아래로 이동
    });
    markAsRead();
    getInfiniteChat();
});
