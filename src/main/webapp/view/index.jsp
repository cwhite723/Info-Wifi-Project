<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.vo.Wifi" %>
<%@ page import="model.vo.WifiDistance" %>
<%@ page import="model.vo.WifiDistance" %>

<!DOCTYPE html>
<html>
<head>
    <style>
        body {
            line-height: 2.5;
            font-family: Arial, Helvetica, sans-serif;
        }
        table {
            font-size: 12px;
            font-family: Arial, Helvetica, sans-serif;
            border: 1px solid #808080;
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            padding: 8px;
            text-align: left;
            border: 1px solid #ddd;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        th {
            background-color: #05aa6d;
            color: white;
            text-align: center;
        }
        tbody tr:hover {
            background-color: #f1f1f1;
        }
    </style>
</head>
<body>

<h1>와이파이 정보 구하기</h1>

<a href="${pageContext.request.contextPath}/view/index.jsp">홈  |</a>
<a href="locationHistory">위치 히스토리 목록  |</a>
<a href="fetchWifiData" onclick="document.getElementById('fetchForm').submit(); return false;">Open API 와이파이 정보 가져오기  |</a>
<a href="bookmark">북마크 보기  |</a>
<a href="bookmarkGroup">북마크 그룹 관리</a>

<form id="fetchForm" action="${pageContext.request.contextPath}/fetchWifiData" method="get" style="display: none;">
</form>

<form action="${pageContext.request.contextPath}/getNearestWifi" method="post">
    <label>LAT:</label>
    <input id="latitude" name="latitude" type="number" step="any" size="20">
    <label>, LNT:</label>
    <input id="longitude" name="longitude" type="number" step="any" size="20">
    <button type="button" onclick="getMyLocation()">내 위치 가져오기</button>
    <button type="submit">근처 WIFI 정보 보기</button>
</form>

<table id="wifi_data">
    <tr>
        <th>거리(Km)</th>
        <th>관리번호</th>
        <th>자치구</th>
        <th>와이파이명</th>
        <th>도로명주소</th>
        <th>상세주소</th>
        <th>설치위치(층)</th>
        <th>설치유형</th>
        <th>설치기관</th>
        <th>서비스구분</th>
        <th>망종류</th>
        <th>설치년도</th>
        <th>실내외구분</th>
        <th>WIFI접속환경</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>작업일자</th>
    </tr>
    <%
        List<WifiDistance> wifiList = (List<WifiDistance>) request.getAttribute("wifiList");
        if (wifiList != null) {
            for (WifiDistance wifiDistance : wifiList) {
                Wifi wifi = wifiDistance.getWifi();
    %>
    <tr>
        <td><%= wifiDistance.getDistance() %></td>
        <td><%= wifi.getManagementNumber() %></td>
        <td><%= wifi.getCity() %></td>
        <td>
            <form action="${pageContext.request.contextPath}/wifiDetail" method="get" style="display: inline;">
                <input type="hidden" name="wifiId" value="<%= wifi.getId() %>">
                <input type="hidden" name="distance" value="<%= wifiDistance.getDistance() %>">
                <button type="submit" style="border: none; background: none; padding: 0; margin: 0; color: #0044cc; text-decoration: underline; cursor: pointer;">
                    <%= wifi.getWifiName() %>
                </button>
            </form>
        </td>
        <td><%= wifi.getRoadAddress() %></td>
        <td><%= wifi.getSubAddress() %></td>
        <td><%= wifi.getInstallFloor() %></td>
        <td><%= wifi.getInstallType() %></td>
        <td><%= wifi.getInstallAgency() %></td>
        <td><%= wifi.getServiceType() %></td>
        <td><%= wifi.getNetworkType() %></td>
        <td><%= wifi.getInstallYear() %></td>
        <td><%= wifi.getLocationType() %></td>
        <td><%= wifi.getConnectionEnvironment() %></td>
        <td><%= wifi.getLatitude() %></td>
        <td><%= wifi.getLongitude() %></td>
        <td><%= wifi.getCreatedAt() %></td>
    </tr>
    <%
            }
        }
    %>
</table>

<script>
    function getMyLocation() {
        console.log("getMyLocation 함수가 호출되었습니다.");

        if ("geolocation" in navigator) {
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    const latitude = position.coords.latitude;
                    const longitude = position.coords.longitude;

                    document.getElementById('latitude').value = latitude;
                    document.getElementById('longitude').value = longitude;

                    saveLocationToSession(latitude, longitude);
                    saveLocationHistory(latitude, longitude);
                },
                (error) => {
                    switch (error.code) {
                        case error.PERMISSION_DENIED:
                            alert("위치 정보 사용 권한이 거부되었습니다.");
                            break;
                        case error.POSITION_UNAVAILABLE:
                            alert("위치 정보를 사용할 수 없습니다.");
                            break;
                        case error.TIMEOUT:
                            alert("위치 정보를 가져오는 데 시간이 초과되었습니다.");
                            break;
                        case error.UNKNOWN_ERROR:
                            alert("알 수 없는 오류가 발생했습니다.");
                            break;
                    }
                },
                {
                    enableHighAccuracy: true,
                    timeout: 20000,
                    maximumAge: 0
                }
            );
        } else {
            alert("브라우저가 위치 서비스를 지원하지 않습니다.");
        }
    }

    function saveLocationToSession(latitude, longitude) {
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "${pageContext.request.contextPath}/saveLocationToSession", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.send("latitude=" + latitude + "&longitude=" + longitude);
    }

    function saveLocationHistory(latitude, longitude) {
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "${pageContext.request.contextPath}/locationHistory", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.send("latitude=" + latitude + "&longitude=" + longitude);
    }
</script>
</body>
</html>
