<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.vo.Bookmark" %>
<%@ page import="model.vo.Wifi" %>

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
    <title>북마크 목록</title>
</head>
<body>
<h1>북마크 목록</h1>

<a href="${pageContext.request.contextPath}/view/index.jsp">홈  |</a>
<a href="bookmarkGroup">북마크 그룹 관리</a>

<table>
    <tr>
        <th>ID</th>
        <th>북마크 이름</th>
        <th>와이파이명</th>
        <th>등록일자</th>
        <th>삭제</th>
    </tr>
    <%
        List<Bookmark> bookmarkList = (List<Bookmark>) request.getAttribute("bookmarkList");
        if (bookmarkList != null) {
            for (Bookmark bookmark : bookmarkList) {
    %>
    <tr>
        <td><%= bookmark.getId() %></td>
        <td><%= bookmark.getBookmarkGroupName() %></td>
        <td>
            <a href="${pageContext.request.contextPath}/wifiDetail?wifiId=<%= bookmark.getWifiId() %>">
                <%= bookmark.getWifiName() %>
            </a>
        </td>
        <td><%= bookmark.getCreatedAt() %></td>
        <td>
            <form action="bookmark" method="post">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="id" value="<%= bookmark.getId() %>">
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
