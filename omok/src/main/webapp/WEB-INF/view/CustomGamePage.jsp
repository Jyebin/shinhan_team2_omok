<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>오목눈이</title>
    <link rel="stylesheet" href="/css/reset.css?after"/>
    <link rel="stylesheet" href="/css/common.css?after"/>
    <link rel="stylesheet" href="/css/game.css?after"/>
    <link rel="stylesheet" href="/css/clock.css"/>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.0.0/moment.min.js"></script>
    <script src="/clock.js"></script>
    <script>
        var type="<%=session.getAttribute("type") %>";
        var room="<%=session.getAttribute("room")%>";

        console.log(room);

        var webSocket = new WebSocket("ws:/localhost:9090/"+room+"/"+type);
    </script>
    <script>
        function copy() {
            var roomCode = document.getElementById('roomCode2'); //id가 roomCode인 값을 가져와 roomCode에 대입
            var range = document.createRange(); //복사할 텍스트의 범위 지정
            range.selectNode(roomCode); //roomCode 요소의 내용 선택
            //window.getSelection().removeAllRanges(); //범위 제거
            window.getSelection().addRange(range); //위에서 만든 range를 현재 선택에 추가
            document.execCommand('copy'); //클립보드에 복사
            alert('방 코드가 복사되었습니다.');
        }

        function change(){
            alert('공개 방으로 전환합니다.');

            const form = document.createElement('form'); // form 제출용 form 객체 생성
            form.setAttribute('method' , 'get');
            form.setAttribute('action' , '${pageContext.request.contextPath}/createRoom');
            const data = document.createElement('input');
            data.setAttribute('name' , 'type');
            data.setAttribute('type', 'hidden');
            data.setAttribute('value','공개');
            form.appendChild(data);

            document.body.appendChild(form);
            form.submit();
        }

    </script>
</head>

<body class="body">
<main class="main">
    <section class="body-item">

        <section class="body-container">
            <section class="body-container-left">
                <img src="/img/checkerboard.png">
            </section>
            <aside class="body-container-right">
                <div id="clock" class="light">
                    <div class="display">
                        <div class="digits"></div>
                    </div>
                </div>

                <div class="opponents">
                    <div class="opponent">
                        <img class="opponents-dot" src="/img/blackdot.png"/>
                        <img class="opponents-img" src="/img/right_character.png">
                        <div class="opponents-id"><img class="me" src="/img/mestar.png">${user.userName}</div>
                    </div>
                    <div class="opponent opponent2">
                        <img class="opponents-dot" src="/img/whitedot.png"/>
                        <img class="opponents-img" src="/img/left_character.png">
                        <div class="opponents-id">IDIDID</div>
                    </div>
                    <div class="codeBox">
                        <div class="codeBox-title">참여자 대기중</div>
                        <div class="codeBox-code" id="roomCode">
                            <p id="roomCode2">${roomCode}</p>
                        </div>
                        <div class="codeBox-buttons">
                            <input type="button" onclick="copy()" class="codeBox-buttons-copy" value="코드복사"/>
                            <input type="button" onclick="change()" class="codeBox-buttons-convert" value="공개방 전환"/>
                        </div>
                    </div>
                </div>


                <div class="chat">
                    <div class="chatheader"></div>
                    <div class="chatmain">
                        <div class="chatmain-left-container">
                            <div class="chatmain-left">asdlkfjllorem</div>
                        </div>

                        <div class="chatmain-right-container">
                            <div class="chatmain-right">Lorem ipsum dosdfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdlor sitit.
                            </div>
                        </div>

                        <div class="chatmain-left-container">
                            <div class="chatmain-left">asdlkfjllorem</div>
                        </div>
                        <div class="chatmain-left-container">
                            <div class="chatmain-left">asdlkfjllorem</div>
                        </div>
                        <div class="chatmain-left-container">
                            <div class="chatmain-left">asdlkfjllorem</div>
                        </div>
                        <div class="chatmain-right-container">
                            <div class="chatmain-right">werwefdfdsdfsdfsdfsdfsdfsdfsdf</div>
                        </div>
                        <div class="chatmain-right-container">
                            <div class="chatmain-right">Lorem ipsum dolor sit amet consectetur adipisicing elit.
                                Impedit
                                exercitationem hic suscipit quas, aut cum consectetur quasi itaque laboriosam vero
                                quisquam? Qui, quia aut ipsam ipsa consectetur necessitatibus! Placeat, obcaecati.
                            </div>
                        </div>
                    </div>
                    <div class="chatfooter">
                        <div class="chatfooter-inner">
                            <input type="text"/><input type="button" value="전송">
                        </div>
                    </div>
                </div>
                <div class="exit-container">
                    <div class="exit">게임 나가기</div>
                </div>
            </aside>
        </section>
    </section>
</main>
</body>

</html>