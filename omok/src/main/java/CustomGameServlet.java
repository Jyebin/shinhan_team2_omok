import DAO.CustomGameDAO;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.SecureRandom;


@WebServlet(name = "CustomGameServlet", value = "/custom-game")
public class CustomGameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder roomCode = createRandomText(); //roomCode 생성
        System.out.println("입장 코드 : " + roomCode);

        CustomGameDAO customGameDAO = new CustomGameDAO();
        customGameDAO.createGame(roomCode.toString()); //저장
        StringBuilder gameId = customGameDAO.findRoomId(roomCode.toString());
        request.setAttribute("roomCode", roomCode); //프론트로 전송
        request.setAttribute("game_id", gameId);

        System.out.println("게임 방 id : " + gameId);
        doHandle(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomCode = request.getParameter("roomCode"); //roomCode값을 받아옴
        if (roomCode != null) {
            System.out.println("roomCode:" + roomCode);
            CustomGameDAO customGameDAO = new CustomGameDAO();
            customGameDAO.changeIsCustom(roomCode);
        } else {
            System.out.println("roomCode가 null값입니다");
        }
    }

    protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 메인 페이지 로직
        // 랭킹을 위해 멤버 정보 받아오기
        request.getRequestDispatcher("/WEB-INF/view/CustomGamePage.jsp").forward(request, response);
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

}
