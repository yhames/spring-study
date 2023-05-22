<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- jstl(JavaServer Pages Standard Tag Library) : 자바코드를 html태그형식으로 간편하게 사용하기 위해 나온 라이브러리 --%>
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
        <th>No.</th>
        <th>제목</th>
        <th>작성자</th>
        <th>작성일</th>
        <th>조회수</th>
    </tr>
    <c:forEach var="board" items="${boardList}" varStatus="loop">
        <tr>
            <td>${board.seq}</td>
            <td><a href="<c:url value="/board/read/${board.seq}"/>">
                    ${board.title}</a></td>
            <td>${board.writer}</td>
            <td>${board.regDate}</td>
            <td>${board.cnt}</td>
        </tr>
    </c:forEach>
</table>
<a href="<c:url value="/board/write"/>">새글</a>
</body>
</html>