<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<%-- 상대경로 사용, "현재 URL 속한 경로 + /save" --%>
<%-- 일반적으로는 절대경로를 사용하는 것이 좋음 --%>
<form action="save" method="post">
    username: <input type="text" name="username"/>
    age: <input type="text" name="age"/>
    <button type="submit">전송</button>
</form>
</body>
</html>
