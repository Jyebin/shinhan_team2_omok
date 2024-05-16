import DAO.GameDAO;
import DAO.WinLoseDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.SecureRandom;

@WebServlet(name = "createRoomServlet", value = "/createRoom")
public class CreateRoomServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String room = null;
        String type = "create"; // room number와 type

        String roomCode = null;
        boolean isCustom = false; // db에 삽입될 내용

        String redirectURL = "";
        GameDAO gameDAO = new GameDAO();

        String roomType = req.getParameter("type"); // random / custom 구분
        if ("공개".equals(roomType)) { // random이면 url만 변경
            redirectURL += "/random-game?";
        } else { // custom이면 url, 나머지 db 변수 모두 변경
            redirectURL += "/custom-game?";
            isCustom = true;
            roomCode = createRandomText().toString();
        }

        // 방 생성 및 번호 받아오기
        gameDAO.createGame(isCustom, roomCode);
        room = gameDAO.findRoomId().toString();

        // 세션에 정보 저장
        HttpSession session = req.getSession();
        session.setAttribute("roomCode", roomCode);
        session.setAttribute("room", room);
        session.setAttribute("type", type);

        redirectURL += "room="+room+"&type="+type;
        res.sendRedirect(redirectURL);
    }

    public static StringBuilder createRandomText() { //방 입장 코드 생성 로직
        String range = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //4자리 수를 추출할 문자열
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder roomCode = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int rand = secureRandom.nextInt(range.length()); //문자열 범위 안에서 하나 선택
            roomCode.append(range.charAt(rand));
        }
        return roomCode;
    }

    @WebServlet(name = "winLoseServlet", value = "/winLose")
    public static class WinLoseServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session = request.getSession();
            String loginUser = (String) session.getAttribute("name");
            System.out.println("로그인한 이긴 유저 : " + loginUser);
            WinLoseDAO winLoseDAO = new WinLoseDAO();
            winLoseDAO.updateWinCnt(loginUser);
            doHandle(request, response);
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doHandle(request, response);
        }

        protected void doHandle(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
            res.setContentType("text/html; charset=utf-8");
        }
    }
}