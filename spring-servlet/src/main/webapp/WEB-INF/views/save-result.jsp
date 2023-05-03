<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Title</title>
</head>
<body>
성공
<ul>
    <%-- EL(Expression Language)표현식, 자바빈의 프로퍼티 값을 편하게 다룰 수 있음 --%>
    <%-- JSP에서 PageContext의 저장소는 차례대로 JspContext, ServletRequest, HttpSesstion, ServletContext가 있다. --%>
    <%-- request에 attribute를 지정하는 방식은 ServletRequest 저장소를 사용한다. --%>
    <%-- 다른 저장소도 사용할 수 있으며 찾는 순서는 위와 같고, 각 보관소의 참조 이름으로 접근 가능하다.(해당 내용은 겁색 ㄱㄱ) --%>
    <li>id=${member.id}</li>
    <li>username=${member.username}</li>
    <li>age=${member.age}</li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>
