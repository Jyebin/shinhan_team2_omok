<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>오목눈이</title>
<%--폰트--%>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=IBM+Plex+Sans+KR&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="/css/reset.css"/>
    <link rel="stylesheet" href="/css/common.css"/>
    <link rel="stylesheet" href="/css/main.css"/>
    <link rel="stylesheet" href="/css/modal.css"/>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script>
        $(function() {
            $('#searchBtn').click(function() {
                $.ajax({
                    type: "get",
                    dataType: "html",
                    async: "false",
                    url: "/main.do",
                    data: {name:$('#searchName').val()},
                    success:function(data, textStatus) {
                        $('#rankingPage').html(data);
                    },
                    error:function(data, textStatus) {
                        alert("error");
                    },
                    complete: function(data, textStatus) {
                    }
                }) // end ajax
            }) // end click
            $('#setBtn').click(function() {
                $('.setting-bg').show();
                $('.setting').show();
            })
            $('#exitBtn').click(function() {
                $('.setting-bg').hide();
                $('.setting').hide();
            })
        }) // end function
    </script>
</head>
<body class="body">
<main class="main">
    <section class="body-item">
        <header class="header">
            <div>랭킹</div>
            <img src="/img/setti.png" id="setBtn"/>
        </header>
        <section class="body-container">
            <aside class="body-container-left">
                <div class="medalists">
                    <div class="silver">
                        <div class="medal-name">${secondMember}</div>
                        <img class="medal-silver" src="/img/silver.png" >
                    </div>
                    <div class="gold">
                        <div class="medal-name">${firstMember}</div>
                        <img class="medal-gold" src="${pageContext.request.contextPath}/img/gold.png" >
                    </div>
                    <div class="bronze">
                        <div class="medal-name">${thirdMember}</div>
                        <img class="medal-bronze" src="/img/bronze.png" >
                    </div>
                </div>
                <img class="cloud" src="/img/cloud.png"/>

                <div class="rank">
                    <div class="white-background">
                        <div class="rank-search">
                            <input type="text" id="searchName" placeholder="아이디 검색">
                            <input type="button" id="searchBtn" value="검색">
                        </div>
                        <div class="rank-panel" id="rankingPage">
                            <c:forEach var="mem" items="${userList}" varStatus="status">
                                <div class="rank-panel-item">
                                    <div class="rank-panel-item-rank">${mem.value}</div>
                                    <div class="rank-panel-item-id">${mem.key}</div></div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </aside>
            <section class="body-container-right">
                <div class="fiveomoknuni-container">
                    <img src="/img/fiveomoknuni.png">
                </div>
                <div class="makeroom">방생성하기</div>
                <div class="speedenter">빠른 입장</div>

                <div class="codeenter">
                    <input class="code" type="text" placeholder="코드 입력하기"/>
                    <input class="codebutton" type="button" value="코드 입장">
                </div>
            </section>
        </section>

    </section>
    <div class="setting-bg"></div>
    <div class="setting">
        <div class="setting-header">
            <input id="exitBtn" type="button" value="X">
        </div>
        <input id="logout" type="button" value="로그아웃">
        <input id="pwd" type="password" placeholder="pwd 입력">
        <input id="withdraw" type="button" value="회원탈퇴">
    </div>
</main>
</body>
</html>