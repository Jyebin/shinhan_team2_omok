import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CustomGameServlet", value = "/custom-game")
public class CustomGameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
}
