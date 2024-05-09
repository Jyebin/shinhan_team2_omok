<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Main</title>
</head>
<body>
<%-- 빠른 입장 버튼 / 사용자 설정 방 생성 버튼 / 사용자 코드 input , 확인 버튼 / 랭킹 목록 --%>
<%-- 랭킹 컴포넌트 --%>
<h1> 랭킹 </h1>
<table border="1">
    <tr>
        <th>순번</th>
        <th>이름</th>
    </tr>
    <c:forEach var="mem" items="${memberList}" begin="1">
        <tr>
            <td>${begin}</td>
            <td>${mem.id}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
