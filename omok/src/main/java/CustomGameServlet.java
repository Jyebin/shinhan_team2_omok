import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.SecureRandom;

@WebServlet(name = "CustomGameServlet", value = "/custom-game")
public class CustomGameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder roomCode = createRandomText();
        System.out.println(roomCode);
        doHandle(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doHandle(request, response);
    }

    protected void doHandle(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // 메인 페이지 로직
        // 랭킹을 위해 멤버 정보 받아오기

        req.getRequestDispatcher("/WEB-INF/view/CustomGamePage.jsp").forward(req, res);
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
