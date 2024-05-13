import DAO.RandomGameDAO;
import VO.GameListVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "randomGameServlet", value = "/random-game")
public class RandomGameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doHandle(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doHandle(request, response);
    }

    protected void doHandle(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=utf-8");
        req.getRequestDispatcher("/WEB-INF/view/RandomGamePage.jsp").forward(req, res);

        // 방 생성
//        new RandomGameDAO().createGameRoom();

        // 방 전환
//        GameListVO vo = new GameListVO();
//        vo.setGameId(1);
//        new RandomGameDAO().switchCustomRoom(vo, CustomGameServlet.createRandomText().toString());
    }

}
