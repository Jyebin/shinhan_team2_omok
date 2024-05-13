import DAO.MainPageDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet(name = "createRoomServlet", value = "/createRoom")
public class CreateRoomServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=utf-8");
        // 메인 페이지 로직

        String roomType = req.getParameter("name");

        // 버튼 클릭 -> db 생성 -> db에서 생성된 roomnumber 가져옴
        // 근데 그 로직 모르겠음 ㅜㅜ

        String room = "1";
        req.setAttribute("room",room);
        req.setAttribute("color", "black");
        req.setAttribute("type", "create");

        String redirectURL = "/random?room="+room+"&color=black";
        res.sendRedirect(redirectURL);
    }
}