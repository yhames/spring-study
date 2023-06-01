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
<table border="1">
    <tr>
        <th>제목</th>
        <td>${boardVO.title}</td>
    </tr>
    <tr>
        <th>내용</th>
        <td>${boardVO.content}</td>
    </tr>
    <tr>
        <th>작성자</th>
        <td>${boardVO.writer}</td>
    </tr>
    <tr>
        <th>작성일</th>
        <td>${boardVO.regDate}</td>
    </tr>
    <tr>
        <th>조회수</th>
        <td>${boardVO.cnt}</td>
    </tr>
</table>
<div>
    <a href="<c:url value="/board/edit"/>">수정</a>
    <a href="<c:url value="/board/delete"/>">삭제</a>
    <a href="<c:url value="/board/list"/> ">목록</a>
</div>
</body>
</html>