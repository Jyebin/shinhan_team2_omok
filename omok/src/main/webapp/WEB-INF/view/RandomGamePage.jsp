<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>오목눈이</title>

    <link rel="stylesheet" href="/css/reset.css"/>
    <link rel="stylesheet" href="/css/common.css"/>
    <link rel="stylesheet" href="/css/game.css"/>
    <link rel="stylesheet" href="/css/clock.css"/>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.0.0/moment.min.js"></script>
    <script>
        // 변수 초기화
        var room = "<%= session.getAttribute("room")%>";
        var type = "<%= session.getAttribute("type")%>";
        var name = "<%= session.getAttribute("name")%>";

        var webSocket;
        var currentUser;
        var myStoneColor, enemyStoneColor;

        if (type == "create") {
            currentUser = "O";
            myStoneColor = "black";
            enemyStoneColor = "white";
        } else {
            currentUser = "X";
            myStoneColor = "white";
            enemyStoneColor = "black";
        }

        function setStoneColor(stoneColor, elementId) {
            document.querySelector(elementId).src = '/img/' + stoneColor + 'dot.png';
        }

        function appendChatMessage(obj, type) {
            let chatmain = document.querySelector("#chatmain");
            if (obj.type == type) {
                chatmain.insertAdjacentHTML('beforeend', "<div class='chatmain-right-container'><div class='chatmain-right'>" + obj.message + "</div></div>");
            } else {
                chatmain.insertAdjacentHTML('beforeend', '<div class="chatmain-left-container"><div class="chatmain-left">' + obj.message + '</div></div>');
            }
            let scroll = document.querySelector("#chatmain");
            scroll.scrollTop = scroll.scrollHeight;
        }

        function sendMessage(obj) {
            webSocket.send(JSON.stringify(obj));
        }

        function placeStone(x, y, stoneColor) {
            const go = document.getElementById('go');

            // 바둑알 이미지 가져오기
            const stone = document.createElement('img');
            stone.src = '/img/' + stoneColor + 'dot.png';
            stone.className = 'stone';

            stone.style.left = '0px';
            stone.style.right = '0px';
            stone.style.width = '53px';
            stone.style.height = '53px';

            // 클릭한 곳이 바둑알의 중심 좌표가 되게
            var stoneX = (x * 53 + 34) - (stone.width / 2);
            var stoneY = (y * 53 + 34) - (stone.height / 2);

            // css 속성으로 바둑알 위치 지정
            stone.style.position = 'absolute';
            stone.style.left = stoneX + 'px';
            stone.style.top = stoneY + 'px';
            stone.style.zIndex = '6';

            go.appendChild(stone);
        }

        function showAlert(alertTitle, alertText, alertIcon, alertConfirmButtonText) {
            Swal.fire({
                title: alertTitle,
                text: alertText,
                icon: alertIcon,
                confirmButtonText: alertConfirmButtonText
            });
        }

    </script>
    <script>

        let update_time3;

        $(function () {

            // Cache some selectors

            var clock = $('#clock'),
                alarm = clock.find('.alarm'),
                ampm = clock.find('.ampm');

            // Map digits to their names (this will be an array)
            var digit_to_name = 'zero one two three four five six seven eight nine'.split(' ');

            // This object will hold the digit elements
            var digits = {};

            // Positions for the hours, minutes, and seconds
            var positions = [
                'h1', 'h2', ':', 'm1', 'm2', ':', 's1', 's2'
            ];

            // Generate the digits with the needed markup,
            // and add them to the clock

            var digit_holder = clock.find('.digits');

            $.each(positions, function () {

                if (this == ':') {
                    digit_holder.append('<div class="dots">');
                }
                else {

                    var pos = $('<div>');

                    for (var i = 1; i < 8; i++) {
                        pos.append('<span class="d' + i + '">');
                    }

                    // Set the digits as key:value pairs in the digits object
                    digits[this] = pos;

                    // Add the digit elements to the page
                    digit_holder.append(pos);
                }

            });

            // Add the weekday names

            var weekday_names = 'MON TUE WED THU FRI SAT SUN'.split(' '),
                weekday_holder = clock.find('.weekdays');

            $.each(weekday_names, function () {
                weekday_holder.append('<span>' + this + '</span>');
            });

            var weekdays = clock.find('.weekdays span');


            // Run a timer every second and update the clock
            let second = 0;
            function update_time2() {

                // Use moment.js to output the current time as a string
                // hh is for the hours in 12-hour format,
                // mm - minutes, ss-seconds (all with leading zeroes),
                // d is for day of week and A is for AM/PM

                //var now = moment().format("hhmmssdA");
                var now = moment(new Date(2019, 12, 25)).add(second, 's').format("hhmmssdA");
                digits.h1.hide();
                digits.h2.hide();
                $(".dots:first").hide();
                digits.m1.attr('class', digit_to_name[now[2]]);
                digits.m2.attr('class', digit_to_name[now[3]]);
                digits.s1.attr('class', digit_to_name[now[4]]);
                digits.s2.attr('class', digit_to_name[now[5]]);

                // The library returns Sunday as the first day of the week.
                // Stupid, I know. Lets shift all the days one position down,
                // and make Sunday last

                var dow = now[6];
                dow--;

                // Sunday!
                if (dow < 0) {
                    // Make it last
                    dow = 6;
                }

                // Mark the active day of the week
                weekdays.removeClass('active').eq(dow).addClass('active');

                // Set the am/pm text:
                ampm.text(now[7] + now[8]);

                // Schedule this function to be run again in 1 sec
                setTimeout(update_time3, 1000);
                second++;
            };
            update_time3 = update_time2;

            // Switch the theme

            $('a.button').click(function () {
                clock.toggleClass('light dark');
            });

        });
























        window.onload = function (){
            document.querySelector(".lose-modal-exit").addEventListener("click" , function (){
                window.location.replace("/main");
            });
            document.querySelector(".win-modal-exit").addEventListener("click" , function (){
                window.location.replace("/main");
            });






            webSocket = new WebSocket("ws://localhost:9090/" + room + "/" + type);
            $(".opponent2").hide();

            // 캐릭터 머리 위 바둑알 색 설정
            setStoneColor(myStoneColor, "#myStone");
            setStoneColor(enemyStoneColor, "#enemyStone");

            let msgbutton = document.querySelector("#msgbutton");
            let msgtext = document.querySelector("#msgtext");

            webSocket.onopen = function (e) { // websocket 서버가 열리면 실행 되는 함수
                appendChatMessage({message: "방에 입장 하였습니다.", type}, type);
                sendMessage({enemyName: name, event: "naming"});
            };

            webSocket.onmessage = function (e) { // WebSocket 서버로 부터 메시지가 오면 호출되는 함수
                let obj = JSON.parse(e.data); // 받은 데이터 파싱
                if (obj.event == "chat") { // 채팅
                    appendChatMessage(obj, type);
                } else if (obj.event == "omok") { // 오목 돌 두기
                    currentUser = "O"; // 데이터를 받았으니 사용자로 턴 돌아옴
                    var x2 = obj.x;
                    var y2 = obj.y;

                    if (x2 < 0 || y2 < 0 || y2 > 12 || x2 > 12) {
                        return;
                    }
                    placeStone(x2, y2, enemyStoneColor);

                } else if (obj.event == 'naming') { // 상대방 이름 설정
                    document.getElementById("enemy").append(obj.enemyName);
                    // 상대방 캐릭터 띄우고, 코드박스 사라지게
                    $(".randomBox").hide();
                    $(".opponent2").show();
                    // clock 돌아가도록 설정
                    update_time3();

                } else if (obj.event == 'state') {
                    //여기서 구현
                    if (obj.win == myStoneColor) { //내가 블랙이고 내가 이겼으면
                        const xhr = new XMLHttpRequest(); //XMLHttpRequest 객체 생성
                        xhr.open("GET", "/winLose"); //HTTP Method, URL 정의
                        xhr.setRequestHeader("content-type", "application/json; charset=UTF-8"); //헤더값 중 content-type 정의

                        xhr.send()

                        document.querySelector(".win-modal").style.display = 'block';
                    } else {
                        document.querySelector(".lose-modal").style.display ='block';
                    }
                    webSocket.close();
                }
            };

            msgbutton.addEventListener('click', function () { // 전송 버튼 이벤트
                sendMessage({message: msgtext.value, event: 'chat'});
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
                    // alert("순서가 아닙니다.");
                    showAlert("경고!","순서가 아닙니다.","warning","확인");
                } else {
                    // 이미지 내에서의 좌표를 구하기 위해 offset 사용
                    const rect = board.getBoundingClientRect();

                    // 클릭한 곳으로부터 가장 가까운 점에 바둑알 놓이게 + 반환될 x, y 좌표 계산 (0~12범위)
                    var x = Math.round((Math.round(event.clientX - rect.left - 34) / 53));
                    var y = Math.round((Math.round(event.clientY - rect.top - 34) / 53));

                    if (x < 0 || y < 0 || y > 12 || x > 12) {
                        return;
                    }

                    placeStone(x, y, myStoneColor);
                    sendMessage({x: x, y: y, state: 'continue', event: 'omok'});

                    currentUser = "X";
                }
            });

            var exit = document.getElementById("exit");
            exit.addEventListener('click', function (event) {
                webSocket.close();
            })
        });
    </script>

    <script>
        function change() {
            // alert('비공개 방으로 전환합니다.');
            showAlert("안내","비공개 방으로 전환합니다.","info","확인");

            const form = document.createElement('form'); // form 제출용 form 객체 생성
            form.setAttribute('method', 'get');
            form.setAttribute('action', '${pageContext.request.contextPath}/createRoom');
            const data = document.createElement('input');

            data.setAttribute('name', 'type');
            data.setAttribute('type', 'hidden');
            data.setAttribute('value', '비공개');

            form.appendChild(data);

            document.body.appendChild(form);
            form.submit();
        }
    </script>
</head>

<body class="body">
<div class="win-modal" style='z-index: 10; font-family: "Jua", sans-serif'>
    <div class="win-modal-container">
        <h2 class="win-modal-title">YOU WIN!!!</h2>
        <input class="win-modal-exit" type="button" value="나가기">
        <img class="win-modal-img1" src="/img/firework.png" width="200" height="200" />
        <img class="win-modal-img2" src="/img/firework.png" width="200" height="200" />
        <img class="win-modal-img3" src="/img/firework.png" width="200" height="200" />
        <img class="win-modal-img4" src="/img/firework.png" width="200" height="200" />
    </div>
</div>
<div class="lose-modal" style='z-index: 10; font-family: "Jua", sans-serif'>
    <div class="lose-modal-container">
        <h2 class="lose-modal-title">YOU LOSE...</h2>
        <input class="lose-modal-exit" type="button" value="나가기">
    </div>
</div>
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
                        <img class="opponents-dot" id="myStone"/>
                        <img class="opponents-img" src="/img/right_character.png">
                        <div class="opponents-id"><img class="me" src="/img/mestar.png">${name}</div>
                    </div>
                    <div class="opponent opponent2">
                        <img class="opponents-dot" id="enemyStone"/>
                        <img class="opponents-img" src="/img/left_character.png">
                        <div class="opponents-id" id="enemy"></div>
                    </div>
                    <div class="randomBox">
                        <div class="randomBox-title">참여자 대기중</div>
                        <div class="randomBox-button">
                            <input type="submit" onclick="change()" class="randomBox-buttons-convert" value="비공개방 전환"/>
                        </div>
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
                    <form action="/main" method="get">
                        <input type="submit" class="exit" id="exit" value="게임 나가기"/>
                    </form>
                </div>
            </aside>
        </section>
    </section>
</main>
</body>
</html>