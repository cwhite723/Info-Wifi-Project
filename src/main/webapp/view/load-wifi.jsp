<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 저장 결과</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            margin-top: 50px;
        }
        .message {
            font-size: 20px;
            margin-bottom: 20px;
        }
        a {
            text-decoration: none;
            color: #0044cc;
            font-size: 16px;
        }
    </style>
</head>
<body>
<div class="message">
    <%= request.getAttribute("savedCount") %>개의 WIFI 정보를 정상적으로 저장하였습니다.
</div>
<a href="<%= request.getContextPath() %>/">홈으로 가기</a>
</body>
</html>
