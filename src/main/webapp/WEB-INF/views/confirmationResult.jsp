<!-- confirmationResult.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>독서친구</title>
    <script>
        function showAlertAndRedirect(message, redirectUrl) {
            alert(message);
            window.location.href = redirectUrl;
        }
    </script>
</head>
<body onload="showAlertAndRedirect('${message}', '${redirectUrl}')">
</body>
</html>
