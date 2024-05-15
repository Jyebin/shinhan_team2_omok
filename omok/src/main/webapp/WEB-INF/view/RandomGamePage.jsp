<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>오목눈이</title>

    <link rel="stylesheet" href="/css/reset.css"/>
    <link rel="stylesheet" href="/css/common.css"/>
    <link rel="stylesheet" href="/css/game.css"/>
    <link rel="stylesheet" href="/css/clock.css?after"/>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.0.0/moment.min.js"></script>
    <script src="/clock.js"></script>
    <script> // 변수 초기화
        var room = "<%= session.getAttribute("room")%>";
        var type = "<%= session.getAttribute("type")%>";
        var name = "<%= session.getAttribute("name")%>"

        var webSocket;
        var currentUser;
        var myStoneColor, enemyStoneColor;

        if (type == "create") {
            currentUser = "O";
            myStoneColor = "black";
            enemyStoneColor = "white";
        }
        else {
            currentUser = "X";
            myStoneColor = "white";
            enemyStoneColor = "black";
        }
    </script>
    <script>
        window.onload = function () {
            webSocket = new WebSocket("ws://localhost:9090/" + room + "/" + type);
            let msgbutton = document.querySelector("#msgbutton");
            let msgtext = document.querySelector("#msgtext");
            let chatmain = document.querySelector("#chatmain");

            webSocket.onopen = function (e) { // websocket 서버가 열리면 실행 되는 함수
                chatmain.insertAdjacentHTML('beforeend', "<div class='chatmain-right-container'><div class='chatmain-right'>" + "방에 입장 하였습니다." + "</div></div>");

                const namingData = {
                    enemyName: name,
                    event: "naming"
                }
                webSocket.send(JSON.stringify(namingData));
            };

            webSocket.onmessage = function (e) { // WebSocket 서버로 부터 메시지가 오면 호출되는 함수
                let obj = JSON.parse(e.data); // 받은 데이터 파싱
                if (obj.event == "chat") { // 채팅
                    if (obj.type == type) {
                        chatmain.insertAdjacentHTML('beforeend', "<div class='chatmain-right-container'><div class='chatmain-right'>" + obj.message + "</div></div>");
                    } else {
                        chatmain.insertAdjacentHTML('beforeend', '<div class="chatmain-left-container"><div class="chatmain-left">' + obj.message + '</div></div>');
                    }
                    let scroll = document.querySelector("#chatmain");
                    scroll.scrollTop = scroll.scrollHeight;
                } else if (obj.event == "omok") { // 오목 돌 두기
                    currentUser = "O"; // 데이터를 받았으니 사용자로 턴 돌아옴

                    var x2 = obj.x;
                    var y2 = obj.y;

                    if (x2 < 0 || y2 < 0 || y2 > 12 || x2 > 12) {
                        return;
                    }

                    // 바둑알 이미지 가져오기
                    const enemyStone = document.createElement('img');

                    enemyStone.src = '/img/'+enemyStoneColor+'dot.png';
                    enemyStone.className = 'stone';

                    // 바둑알 위치 초기화 및 크기 지정
                    enemyStone.style.left = '0px';
                    enemyStone.style.right = '0px';
                    enemyStone.style.width = '53px';
                    enemyStone.style.height = '53px';

                    var putX = x2 * 53 + 34;
                    var putY = y2 * 53 + 34;
                    // 클릭한 곳이 바둑알의 중심 좌표가 되게
                    var stoneX = putX - (enemyStone.width / 2);
                    var stoneY = putY - (enemyStone.height / 2);

                    // css 속성으로 바둑알 위치 지정
                    enemyStone.style.position = 'absolute';
                    enemyStone.style.left = stoneX + 'px';
                    enemyStone.style.top = stoneY + 'px';
                    enemyStone.style.zIndex = '6';

                    go.appendChild(enemyStone);

                    // 이김 여부 판별
                    if (obj.state == "win") {
                        // 이겼을 때 로직
                    } else if (obj.state == "lose") {
                        // 졌을 때 로직
                    }
                } else if (obj.event == 'naming') { // 상대방 이름 설정
                    const enemyName = obj.enemyName;
                    document.getElementById("enemy").append(enemyName);
                }
            };

            msgbutton.addEventListener('click', function () { // 전송 버튼 이벤트
                let obj = {};
                obj.message = msgtext.value;
                obj.event = 'chat';

                webSocket.send(JSON.stringify(obj));
                document.querySelector("#msgtext").value = '';
            })
        };
    </script>
    <script>
        // 바둑알 놓기
        document.addEventListener('DOMContentLoaded', function () {
            const board = document.getElementById('checkerboard-img');
            const go = document.getElementById('go');

            board.addEventListener('click', function (event) {
                if (currentUser == 'X') {
                    alert("순서가 아닙니다.");
                }
                else {
                    // 이미지 내에서의 좌표를 구하기 위해 offset 사용
                    const rect = board.getBoundingClientRect();

                    // 클릭한 곳으로부터 가장 가까운 점에 바둑알 놓이게
                    var x = Math.round((Math.round(event.clientX - rect.left - 34) / 53)) * 53 + 34; // X 좌표
                    var y = Math.round((Math.round(event.clientY - rect.top - 34) / 53)) * 53 + 34;// Y 좌표

                    // 반환될 x, y 좌표 계산 (0~12범위)
                    var returnX = (x - 34) / 53;
                    var returnY = (y - 34) / 53;

                    if (returnX < 0 || returnY < 0 || returnY > 12 || returnX > 12) {
                        return;
                    }

                    // 해당 바둑알 이미지 가져오기
                    const myStone = document.createElement('img');
                    myStone.src = '/img/'+myStoneColor+'dot.png';
                    myStone.className = 'stone';

                    // 바둑알 위치 초기화 및 크기 지정
                    myStone.style.left = '0px';
                    myStone.style.right = '0px';
                    myStone.style.width = '53px';
                    myStone.style.height = '53px';

                    // 클릭한 곳이 바둑알의 중심 좌표가 되게
                    var stoneX = x - (myStone.width / 2);
                    var stoneY = y - (myStone.height / 2);

                    // css 속성으로 바둑알 위치 지정
                    myStone.style.position = 'absolute';
                    myStone.style.left = stoneX + 'px';
                    myStone.style.top = stoneY + 'px';
                    myStone.style.zIndex = '6';

                    // 바둑판에 요소 추가해서 돌 놓기
                    go.appendChild(myStone);
                    // 이 부분 겹쳐서 함수로 쓰고 싶은데 어떻게 할 지 모르겠움 ㅠ,ㅠ

                    // 소켓으로 x, y 좌표 보내주기
                    var message = {
                        x: returnX,
                        y: returnY,
                        state: 'continue',
                        event: 'omok'
                    };
                    webSocket.send(JSON.stringify(message));
                    currentUser = "X";
                }
            });
        });
    </script>
</head>

<body class="body">
<main class="main">
    <section class="body-item">
        <section class="body-container">
            <section class="body-container-left">
                <div id="go">
                    <img src="/img/checkerboard.png" id="checkerboard-img">
                </div>
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
                        <div class="opponents-id"><img class="me" src="/img/mestar.png">${name}</div>
                    </div>
                    <div class="opponent">
                        <img class="opponents-dot" src="/img/whitedot.png"/>
                        <img class="opponents-img" src="/img/left_character.png">
                        <div class="opponents-id" id="enemy"></div>
                    </div>
                </div>

                <div class="chat">
                    <div class="chatheader"></div>
                    <div id="chatmain" class="chatmain"></div>
                    <div class="chatfooter">
                        <div class="chatfooter-inner">
                            <input id="msgtext" type="text"/><input type="button" id="msgbutton" value="전송">
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