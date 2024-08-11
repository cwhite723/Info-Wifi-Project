<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.vo.BookmarkGroup" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 20px;
    }

    table {
      width: 50%;
      border-collapse: collapse;
      margin-top: 20px;
      margin-left: auto;
      margin-right: auto;
    }

    th, td {
      padding: 12px;
      text-align: left;
      border: 1px solid #ddd;
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

    .btn {
      padding: 5px 10px;
      background-color: #04AA6D;
      color: white;
      border: none;
      border-radius: 3px;
      cursor: pointer;
      text-decoration: none;
    }

    .btn:hover {
      background-color: #028658;
    }

    .btn-secondary {
      background-color: #ddd;
      color: black;
    }

    .btn-secondary:hover {
      background-color: #bbb;
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
      width: 100%;
    }

    .form-inline td {
      text-align: center;
    }
  </style>
</head>
<body>
<div class="header">북마크 그룹 수정</div>

<%
  BookmarkGroup group = (BookmarkGroup) request.getAttribute("group");
  if (group != null) {
%>
<form action="${pageContext.request.contextPath}/bookmarkGroup" method="post">
  <input type="hidden" name="id" value="<%= group.getId() %>">
  <table>
    <tr>
      <th>북마크 이름</th>
      <td><input type="text" name="groupName" value="<%= group.getGroupName() %>"></td>
    </tr>
    <tr>
      <th>순서</th>
      <td><input type="text" name="sequence" value="<%= group.getSequence() %>"></td>
    </tr>
    <tr>
      <td colspan="2" style="text-align: center;">
        <a href="bookmarkGroup" class="btn btn-secondary">돌아가기</a>
        <button type="submit" class="btn">수정</button>
      </td>
    </tr>
  </table>
</form>
<%
} else {
%>
<div style="text-align: center;">수정할 북마크 그룹을 찾을 수 없습니다.</div>
<%
  }
%>
</body>
</html>
