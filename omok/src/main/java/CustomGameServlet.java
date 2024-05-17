import DAO.GameDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CustomGameServlet", value = "/custom-game")
public class CustomGameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/view/CustomGamePage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomId = request.getParameter("roomId"); //roomCode값을 받아옴
        if (roomId != null) {
            System.out.println("roomCode:" + roomId);
            GameDAO gameDAO = new GameDAO();
            gameDAO.changeIsCustom(Integer.parseInt(roomId));
        } else {
            System.out.println("roomCode가 null값입니다");
        }
    }

    protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
