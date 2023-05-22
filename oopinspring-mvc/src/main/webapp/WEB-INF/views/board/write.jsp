<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=chrome">
    <title>Title</title>
</head>
<body>
<form action="<c:url value="/board/write"/>" method="post">
    <table border="1">
        <tr>
            <th>제목</th>
            <td><input name="title"/></td>
        </tr>
        <tr>
            <th>내용</th>
            <td><input name="content"/></td>
        </tr>
        <tr>
            <th>작성자</th>
            <td><input name="writer"/></td>
        </tr>
        <tr>
            <th>비밀번호</th>
            <td><input name="password" type="password"/></td>
        </tr>
    </table>
    <div>
        <input type="submit" value="등록">
        <a href="<c:url value="/board/list"/> ">목록</a>
    </div>
</form>
</body>
</html>