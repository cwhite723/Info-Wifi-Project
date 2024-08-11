<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.vo.BookmarkGroup" %>
<%@ page import="java.util.List" %>
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

        .header {
            text-align: center;
            font-size: 24px;
            margin-top: 20px;
        }

        .btn {
            padding: 5px 10px;
            color: royalblue;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }

        .form-inline {
            display: flex;
            align-items: center;
        }

        .form-inline input {
            margin-right: 10px;
            padding: 5px;
            border: 1px solid #ddd;
            border-radius: 3px;
        }
    </style>
</head>
<body>
<a href="${pageContext.request.contextPath}/view/index.jsp">홈  |</a>
<a href="bookmark">북마크 보기  |</a>

<div class="header">북마크 그룹 관리</div>

<form class="form-inline" action="bookmarkGroup" method="post">
    <label for="groupName">그룹 이름:</label>
    <input type="text" name="groupName" id="groupName">
    <label for="sequence">순서:</label>
    <input type="text" name="sequence" id="sequence">
    <button type="submit" class="btn">추가</button>
</form>

<table>
    <tr>
        <th>ID</th>
        <th>그룹 이름</th>
        <th>순서</th>
        <th>등록일자</th>
        <th>수정일자</th>
        <th>비고</th>
    </tr>
    <%
        List<BookmarkGroup> groupList = (List<BookmarkGroup>) request.getAttribute("groupList");
        if (groupList != null) {
            for (BookmarkGroup group : groupList) {
    %>
    <tr>
        <td><%= group.getId() %></td>
        <td>
            <%
                if (request.getParameter("editId") != null && request.getParameter("editId").equals(String.valueOf(group.getId()))) {
            %>
            <form class="form-inline" action="bookmarkGroup" method="post">
                <input type="hidden" name="id" value="<%= group.getId() %>">
                <input type="text" name="groupName" value="<%= group.getGroupName() %>">
                <input type="text" name="sequence" value="<%= group.getSequence() %>">
                <a href="bookmarkGroup" class="btn">돌아가기</a>
                <button type="submit" class="btn">수정</button>
            </form>
            <%
                } else {
                    out.print(group.getGroupName());
                }
            %>
        </td>
        <td><%= group.getSequence() %></td>
        <td><%= group.getCreatedAt() %></td>
        <td>
            <%
                if (group.getUpdatedAt() != null) {
                    out.print(group.getUpdatedAt());
                } else {
                    out.print("");
                }
            %>
        </td>
        <td>
            <form action="bookmarkGroup" method="get" style="display: inline;">
                <input type="hidden" name="editId" value="<%= group.getId() %>">
                <button type="submit" class="btn">수정</button>
            </form>
            <form action="bookmarkGroup" method="post" style="display: inline;">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="id" value="<%= group.getId() %>">
                <button type="submit" class="btn">삭제</button>
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
