<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--사용자 설정 코드 + 복붙 버튼 추가 필요--%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>오목눈이</title>

    <link rel="stylesheet" href="/css/reset.css" />
    <link rel="stylesheet" href="/css/common.css" />
    <link rel="stylesheet" href="/css/game.css" />
    <link rel="stylesheet" href="/css/clock.css" />
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.0.0/moment.min.js"></script>
    <script src="/clock.js"></script>

    <script>
        // 바둑알 놓기
        document.addEventListener('DOMContentLoaded', function() {
            const board = document.getElementById('checkerboard-img');
            const go = document.getElementById('go');

            board.addEventListener('click', function(event) {
                // 이미지 내에서의 좌표를 구하기 위해 offset 사용
                const rect = board.getBoundingClientRect();

                // 클릭한 곳으로부터 가장 가까운 점에 바둑알 놓이게
                var x = Math.round((Math.round(event.clientX - rect.left - 34) / 53 ))* 53 + 34; // X 좌표
                var y = Math.round((Math.round(event.clientY - rect.top - 34) / 53 )) * 53 + 34;// Y 좌표

                // 콘솔에 좌표 표시
                <%--console.log(`X 좌표: ${x}, Y 좌표: ${y}`);--%>

                // 검은 바둑알 이미지 가져오기
                const blackStone = document.createElement('img');
                blackStone.src = '/img/blackdot.png';
                blackStone.className = 'stone';

                // 위치 초기화
                blackStone.style.left = '0px';
                blackStone.style.right = '0px';

                // 바둑알 크기 지정
                blackStone.style.width='53px';
                blackStone.style.height='53px';

                // 클릭한 곳이 바둑알의 중심 좌표가 되게
                var stoneX = x - (blackStone.width / 2);
                var stoneY = y - (blackStone.height / 2);

                // css 속성으로 바둑알 위치 지정
                blackStone.style.position = 'absolute';
                blackStone.style.left= stoneX + 'px';
                blackStone.style.top= stoneY + 'px';
                blackStone.style.zIndex = '6';

                // 놓인 곳에 바둑알 다시 못 놓게 << 수정해야 함
                blackStone.style.userSelect = 'none';
                blackStone.style.pointerEvents = 'none';

                // 바둑판에 요소 추가해서 돌 놓기
                go.appendChild(blackStone);
            });
        });
    </script>

</head>

<body class="body">
<main class="main">
    <section class="body-item">

        <section class="body-container">
            <section class="body-container-left">

                <div id = "go" >
                    <img src="/img/checkerboard.png" id = "checkerboard-img">
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
                        <img class="opponents-dot" src="/img/blackdot.png" />
                        <img class="opponents-img" src="/img/right_character.png">
                        <div class="opponents-id"><img class="me" src="/img/mestar.png">IDIDID</div>
                    </div>
                    <div class="opponent">
                        <img class="opponents-dot" src="/img/whitedot.png" />
                        <img class="opponents-img" src="/img/left_character.png">
                        <div class="opponents-id">IDIDID</div>
                    </div>
                </div>


                <div class="chat">
                    <div class="chatheader"></div>
                    <div class="chatmain">
                        <div class="chatmain-left-container">
                            <div class="chatmain-left">asdlkfjllorem</div>
                        </div>

                        <div class="chatmain-right-container">
                            <div class="chatmain-right">Lorem ipsum dosdfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdlor sitit.</div>
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
                            <input type="text" /><input type="button" value="전송">
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