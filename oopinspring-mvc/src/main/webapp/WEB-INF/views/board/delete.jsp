<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<form:form modelAttribute="boardVO" method="post">
    <form:input path="seq"/>
    비밀번호<input name="pwd"/>
    <input type="submit">
    <a href="<c:url value="/board/read/${boardVO.seq}"/>">취소</a>
</form:form>
<div>${msg}</div>
</body>
</html>