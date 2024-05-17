# 웹 기반 오목 게임 구현 프로젝트 - 현오목

Web-based concave game development project

![오목눈이](https://github.com/Jyebin/shinhan_team3_omok/assets/67955977/cb42b5d4-2d43-4ba4-a9af-7cb96b1f6b2e)

## Team🐥

| 하소영 (Soyeong Ha)                                                                                              | 김민지(Minji Kim)                                                                                              | 서현오(Heonoh Seo)                                                                                              | 조예빈(Yebin Jo)                                                                                                | 최민서(Minseo Choi)                                                                                            |
|---------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|
| ![soyeong](https://github.com/Jyebin/shinhan_team3_omok/assets/67955977/d4212bca-3100-4b21-bc24-3bb171350336) | ![minji](https://github.com/Jyebin/shinhan_team3_omok/assets/67955977/6a5d0a60-8ebb-4548-8071-2957265442ef) | ![hyeono](https://github.com/Jyebin/shinhan_team3_omok/assets/67955977/9efc21d8-3525-4ddf-8126-b2fcda73e8a5)  | ![yebin](https://github.com/Jyebin/shinhan_team3_omok/assets/67955977/47f41339-2451-4c5b-9461-e83772ad8ecc)| ![minseo](https://github.com/Jyebin/shinhan_team3_omok/assets/67955977/1135661a-ed49-4b5a-9962-16fb87eb53d4) |
| @soyoungxxx                                                                                                    | @wing-beat                                                                                                  | @ohhyeonn                                                                                                    | @Jyebin                                                                                                      | @cmsxi                                                                                                      |
| 팀장, 기획, 프론트엔드, 백엔드                                                                                            | 기획, 프론트엔드, 백엔드                                                                                              | 기획, 프론트엔드, 백엔드, 웹 퍼블리싱                                                                                       | 기획, 프론트엔드, 백엔드                                                                                               | 기획, 프론트엔드, 백엔드, 디자인                                                                                         |
| 웹 소켓 구현 (실시간 게임)                                                                                              | 웹 소켓 구현 (실시간 채팅)                                                                                            | 웹 소켓 구현 (실시간 게임)                                                                                             | 웹 소켓 구현 (실시간 게임)                                                                                             | 웹 소켓 구현 (실시간 게임)                                                                                            |

## Project

- 프로젝트 목표
    - 오목 게임을 웹 버전으로 개발합니다.
    - JSP, Servlet, AJAX, 웹소켓을 적용시켜 개발합니다.
    - 데이터베이스와 연동하여 사용자 정보와 게임 기록을 관리합니다.
    - 팀 내 협의를 통해 추가 기능을 구현합니다.
    - 팀별로 발표를 진행합니다.

---

## 주요 기능 📦

1. **사용자 인증**: 로그인 및 회원가입 기능을 통한 사용자 인증 및 탈퇴, 로그아웃 기능 구현
2. **실시간 대전**: 웹소켓을 활용한 실시간 오목 대전 기능 구현
3. **랜덤 입장 기능**: 생성된 공개방에 빠른 입장으로 게임 상대를 매칭해주는 기능 구현
4. **사용자 커스텀 입장 기능**: 생성된 비공개방에 랜덤으로 발급되는 코드를 통해 원하는 상대와 게임할 수 있는 기능 구현
5. **실시간 채팅**: 웹소켓을 활용한 실시간 오목 대전 중 채팅 기능 구현
6. **랭킹 시스템**: 게임 기록을 데이터베이스에 저장하여 게임 결과에 따른 사용자 랭킹 시스템 구현
7. **사용자 서치 기능** : 이용자의 닉네임을 서치하여 등수를 볼 수 있는 기능 구현

---

## 화면 구성 📺

| LandingPage - 랜딩 페이지                                                                                                                        |
|---------------------------------------------------------------------------------------------------------------------------------------------|
| <img width="962" alt="LandingPage" src="https://github.com/Jyebin/shinhan_team3_omok/assets/67955977/91199c40-6e45-46a5-a5f2-e8413820b18a"> |

| RegisterPage - 회원가입 페이지                                                                                                                      |
|----------------------------------------------------------------------------------------------------------------------------------------------|
| <img width="968" alt="RegisterPage" src="https://github.com/Jyebin/shinhan_team3_omok/assets/67955977/c8042f40-965f-4b9c-ae5b-18937d4e0bcd"> |

| MainPage - 메인 페이지                                                                                                                        |
|------------------------------------------------------------------------------------------------------------------------------------------|
| <img width="965" alt="MainPage" src="https://github.com/Jyebin/shinhan_team3_omok/assets/67955977/87688be8-e167-451c-9adb-539c7671196e"> |

| MainPage - 로그아웃, 사용자 탈퇴                                                                                                                        | MainPage - 공개방/비공개방 생성 선택                                                                                                                          |
|------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| <img width="965" alt="MainPage-Popup" src="https://github.com/Jyebin/shinhan_team3_omok/assets/67955977/2e710d0b-ba97-4768-b582-c97877abf1fa"> | <img width="966" alt="MainPage-EnterRoom" src="https://github.com/Jyebin/shinhan_team3_omok/assets/67955977/6200905d-3f79-4d7f-82ab-e4f3c97e3036"> |

| RandomGamePage - 공개방                                                                                                                  |
|---------------------------------------------------------------------------------------------------------------------------------------|
| <img width="965" alt="image" src="https://github.com/Jyebin/shinhan_team3_omok/assets/67955977/2e1d909e-8ced-4b20-a695-57c90964f1f9"> |

| CustomGamePage - 비공개방                                                                                                                                  |
|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img width="965" alt="RandomGameToCustomGame" src="https://github.com/Jyebin/shinhan_team3_omok/assets/67955977/19ca1bfb-2154-44cf-a3d8-7078f02c51b6"> |

| GamePage - 실시간 게임 중인 화면                                                                                                              |
|--------------------------------------------------------------------------------------------------------------------------------------|
| <img width="641" alt="Game" src="https://github.com/Jyebin/shinhan_team3_omok/assets/67955977/44f7400d-5b04-45d4-9b9f-cb7560099f35"> |

| GamePage - Win/Lose 판별                                                                                                                  |
|-----------------------------------------------------------------------------------------------------------------------------------------|
| <img width="635" alt="WinGame" src="https://github.com/Jyebin/shinhan_team3_omok/assets/67955977/170c8cbc-8d65-4df0-bec9-fc1a6b945f7d"> |

---

## Stacks 🛠️

<img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> <img src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white"> <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/apache tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">

- 프론트엔드: HTML, CSS, JavaScript
- 백엔드: Java, JSP, Servlet
- 실시간 통신: WebSocket
- AJAX: 비동기 데이터 페칭
- 데이터베이스: MySQL, 서버 사이드 스크립팅을 위한 Expression Language와 JSTL 사용
- 서버: Apache Tomcat
- 개발 환경: IntelliJ IDEA
- 버전 관리: GitHub
- 통신 도구: Discord
- 디자인 : Figma


---

## 구조 Architecture 🌳

![architecture](https://github.com/Jyebin/shinhan_team3_omok/assets/67955977/15efac75-fcfd-490a-98cf-b5837c792c93)

| 1. 회원 관리 시스템 (로그인, 로그아웃, 회원가입, 회원 탈퇴)                                                                                         |
|-------------------------------------------------------------------------------------------------------------------------------|
| ![flowchart1](https://file.miricanvas.com/user_image/2024/05/16/19/50/k2gyfoz9nllt7v3h/mermaid-diagram-2024-05-16-195829.png) |

| 2-1. 게임방(공개) 생성 및 참여                                                                                                          | 2-2. 게임방(비공개) 생성 및 참여                                                                                                         |
|-------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------|
| ![flowchart2](https://file.miricanvas.com/user_image/2024/05/16/19/50/k2gyfoz9nllt7v3h/mermaid-diagram-2024-05-16-195829.png) | ![flowchart3](https://file.miricanvas.com/user_image/2024/05/16/21/00/kktben38v2nywdmo/mermaid-diagram-2024-05-16-210831.png) |

| 3. 게임 실행 및 종료                            |
|-----------------------------------------------------------------------|
| ![flowchart4](https://file.miricanvas.com/user_image/2024/05/16/21/10/k6c56ctvpatwvyjm/mermaid-diagram-2024-05-16-211821.png) |

| 4. 웹 소켓 시스템 구조 (방 생성 / 입장)       |
|------------------------------------|
| ![flowchart5](https://file.miricanvas.com/user_image/2024/05/16/21/40/kuqsdbx2h2fwfh9r/image.png) |

---

## Database: ERD

![erd1](https://file.miricanvas.com/user_image/2024/05/16/20/10/k72lwu2mz7ntel00/image.png)


---

## 필요 조건

- Java JDK 설치
- MySQL 설치 및 구성
- Apache Tomcat 서버 설치
- Intellij 환경 세팅

---

## Intellij 환경 세팅 방법

1. Project Clone

```
git clone https://github.com/Jyebin/shinhan_team3_omok.git
```

2. 인텔리제이에서 프로젝트 열기

- 인텔리제이에서 프로젝트를 열 때, shinhan_team3_omok 폴더 내의 omok 하위 폴더를 선택한다.
  omok 폴더를 선택하면 프로젝트가 올바르게 로드된다.

3. 메이븐, 모듈, 라이브러리, 아티팩트 확인

- 메이븐(Maven): pom.xml 파일이 정상적으로 나타나거나, 오른쪽 사이드바에 'm' 아이콘이 보이면 메이븐이 잘 설정된 것이다.

- 모듈(Module): 메이븐 업데이트 후 Project Structure에서 모듈을 확인해보자. 업데이트 전에는 모듈이 보이지 않을 수 있다.

- 라이브러리(Library): 프로젝트에 필요한 라이브러리가 없다면, + 버튼을 클릭하여 java를 선택하고, lib 폴더 내의 모든 파일을 선택한 후 OK를 클릭하여 추가한다.

- 아티팩트(Artifact): Project Structure에서 아티팩트를 확인할 수 있다.

---

## 프로젝트 보완 사항 Future Improvements

1. 인터페이스 개선 (게임 규칙 설명, 사용자 피드백 메시지, 애니메이션 효과 추가)
2. 반응형 웹디자인 (모든 기기에서 일관된 사용자 경험을 제공)
3. HTTPS 사용 : 사용자 데이터 보안 강화
4. 외부 서버 연결 (외부 클라이언트간 통신)
5. 랭킹 시스템 확장 : 승률, 연승 기록 등을 도입해 사용자간 경쟁 촉진
6. 코드 리팩토링
7. TDD 중심 테스트 주도 개발 도입 (Test Driven Development)
