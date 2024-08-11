<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.vo.LocationHistory" %>

<!DOCTYPE html>
<html>
<head>
    <title>위치 히스토리 목록</title>
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
            padding: 8px 12px;
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
        .delete-btn {
            background-color: #f8f9fa;
            color: black;
            border: 1px solid #ced4da;
            padding: 3px 10px;
            text-align: center;
            font-size: 12px;
            cursor: pointer;
            border-radius: 4px;
            display: inline-block;
        }
    </style>
</head>
<body>
<h1>위치 히스토리 목록</h1>
<a href="${pageContext.request.contextPath}/view/index.jsp">홈  |</a>

<table id="history_table">
    <tr>
        <th>ID</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>조회일자</th>
        <th>비고</th>
    </tr>
    <%
        List<LocationHistory> locationHistoryList = (List<LocationHistory>) request.getAttribute("locationHistoryList");
        if (locationHistoryList != null) {
            for (LocationHistory history : locationHistoryList) {
    %>
    <tr>
        <td><%= history.getId() %></td>
        <td><%= history.getLatitude() %></td>
        <td><%= history.getLongitude() %></td>
        <td><%= history.getCreatedAt() %></td>
        <td>
            <form action="locationHistory" method="post" style="display:inline;">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="id" value="<%= history.getId() %>">
                <button type="submit" class="delete-btn">삭제</button>
            </form>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>
