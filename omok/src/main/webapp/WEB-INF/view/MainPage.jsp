<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>오목눈이</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=IBM+Plex+Sans+KR&family=Jua&display=swap" rel="stylesheet">


    <link rel="stylesheet" href="/css/reset.css"/>
    <link rel="stylesheet" href="/css/modal.css?after"/>
    <link rel="stylesheet" href="/css/common.css?after"/>
    <link rel="stylesheet" href="/css/main.css?after"/>
    <link rel="stylesheet" href="/css/font.css?after"/>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script>
        $(function() {
            // 랭킹 검색 버튼
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
                $('#set').show();
            })
            $('#exitBtn').click(function() {
                $('.setting-bg').hide();
                $('#set').hide();
            })
            $('#makeRoom').click(function() {
                $('.setting-bg').show();
                $('#select').show();
            })
            $('#selectExitBtn').click(function() {
                $('.setting-bg').hide();
                $('#select').hide();
            })
            $("#logout").click(function() {
                $.get("/logout", function(data, status) {
                    alert("로그아웃 됐습니다.");
                    window.location.href = "/landing";
                });
            });
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
                        <img class="medal-silver" src="/img/silver.png">
                    </div>
                    <div class="gold">
                        <div class="medal-name">${firstMember}</div>
                        <img class="medal-gold" src="${pageContext.request.contextPath}/img/gold.png">
                    </div>
                    <div class="bronze">
                        <div class="medal-name">${thirdMember}</div>
                        <img class="medal-bronze" src="/img/bronze.png">
                    </div>
                </div>
                <img class="cloud" src="/img/cloud.png"/>

                <div class="rank">
                    <div class="white-background">
                        <div class="rank-search">
                            <input type="text" id="searchName" placeholder="아이디 검색">
                            <input type="button" id="searchBtn" value="🔍">
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
                    <img src="/img/fiveomoknuni.png" class="fiveomoknuni">
                </div>
                <input type="button" class="makeRoom" id="makeRoom" value="방 생성하기">

                <form action="${pageContext.request.contextPath}/enterRoom" method="get">
                    <input type="submit" class="speedEnter" id="speedEnter" value="빠른 입장" name="roomType">
                </form>

                <form action="${pageContext.request.contextPath}/enterRoom" method="get">
                    <div class="codeEnter">
                        <input class="code" type="text" placeholder="코드 입력하기" name="code"/>
                        <input class="codeButton" type="submit" value="코드 입장" name="roomType">
                    </div>
                </form>
            </section>
        </section>

    </section>

    <div class="modal-bg"></div>
    <div class="modal" id="set">
        <div class="modal-header">
            <input class="exit" id="exitBtn" type="button" value="X">
        </div>
        <input id="logout" type="button" value="로그아웃">
        <form class="formClass" action="${pageContext.request.contextPath}/register" method="post">
            <input name="pwd" id="pwd" type="password" placeholder="pwd 입력">
            <input name="cmd" type="hidden" value="delmember" >
            <input id="withdraw" type="submit" value="회원탈퇴">
        </form>
    </div>
    <div class="modal" id="select">
        <div class="modal-header">
            <input class="exit" id="selectExitBtn" type="button" value="X">
        </div>
        <form class="formClass" action="${pageContext.request.contextPath}/createRoom" method="get">
            <input class="modal-long-button" id="public" name="type" type="submit" value="공개">
            <input class="modal-long-button" id="private" name="type" type="submit" value="비공개">
        </form>
    </div>
</main>
</body>
</html>