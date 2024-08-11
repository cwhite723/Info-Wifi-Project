<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.vo.WifiDistance" %>
<%@ page import="model.vo.Wifi" %>
<%@ page import="model.vo.BookmarkGroup" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <style>
        body {
            line-height: 2.5;
        }

        table {
            font-size: 14px;
            font-family: Arial, Helvetica, sans-serif;
            border: 1px solid #808080;
            border-collapse: collapse;
            width: 60%;
            margin: auto;
            margin-top: 20px;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #04AA6D;
            color: white;
        }

        .header {
            text-align: center;
            font-size: 24px;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="header">와이파이 상세 정보</div>
<%
    WifiDistance wifiDistance = (WifiDistance) request.getAttribute("wifiDetail");
    if (wifiDistance != null) {
        Wifi wifi = wifiDistance.getWifi();
%>
<form action="${pageContext.request.contextPath}/wifiDetail" method="post">
    <label for="groupId">북마크 그룹 이름 선택:</label>
    <select name="groupId" id="groupId" required>
        <%
            List<BookmarkGroup> groupList = (List<BookmarkGroup>) request.getAttribute("groupList");
            if (groupList != null && !groupList.isEmpty()) {
                for (BookmarkGroup group : groupList) {
        %>
        <option value="<%= group.getId() %>"><%= group.getGroupName() %></option>
        <%
            }
        } else {
        %>
        <option value="">북마크 그룹이 없습니다</option>
        <%
            }
        %>
    </select>
    <input type="hidden" name="wifiId" value="<%= wifi.getId() %>">
    <button type="submit">북마크 추가하기</button>
</form>

<table>
    <tr><th>거리(Km)</th><td><%= wifiDistance.getDistance() %></td></tr>
    <tr><th>관리번호</th><td><%= wifi.getManagementNumber() %></td></tr>
    <tr><th>자치구</th><td><%= wifi.getCity() %></td></tr>
    <tr><th>와이파이명</th><td><%= wifi.getWifiName() %></td></tr>
    <tr><th>도로명주소</th><td><%= wifi.getRoadAddress() %></td></tr>
    <tr><th>상세주소</th><td><%= wifi.getSubAddress() %></td></tr>
    <tr><th>설치위치(층)</th><td><%= wifi.getInstallFloor() %></td></tr>
    <tr><th>설치유형</th><td><%= wifi.getInstallType() %></td></tr>
    <tr><th>설치기관</th><td><%= wifi.getInstallAgency() %></td></tr>
    <tr><th>서비스구분</th><td><%= wifi.getServiceType() %></td></tr>
    <tr><th>망종류</th><td><%= wifi.getNetworkType() %></td></tr>
    <tr><th>설치년도</th><td><%= wifi.getInstallYear() %></td></tr>
    <tr><th>실내외구분</th><td><%= wifi.getLocationType() %></td></tr>
    <tr><th>WIFI접속환경</th><td><%= wifi.getConnectionEnvironment() %></td></tr>
    <tr><th>X좌표</th><td><%= wifi.getLatitude() %></td></tr>
    <tr><th>Y좌표</th><td><%= wifi.getLongitude() %></td></tr>
    <tr><th>작업일자</th><td><%= wifi.getCreatedAt() %></td></tr>
</table>

<%
} else {
%>
<div style="text-align: center;">와이파이 정보를 찾을 수 없습니다.</div>
<%
    }
%>
</body>
</html>
