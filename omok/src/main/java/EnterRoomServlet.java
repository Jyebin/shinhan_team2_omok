import DAO.RandomGameDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "enterRoomServlet", value = "/enterRoom")
public class EnterRoomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=utf-8");

        String roomId = "0";
        String gameType = "";
        RandomGameDAO randomGameDAO = new RandomGameDAO();

        if(req.getParameter("roomType").equals("코드 입장")){
            // custom 입장인 경우
            String code = req.getParameter("code");
            roomId = String.valueOf(randomGameDAO.canJoinCustomRoomId(code));
            gameType = "custom";
        }else{
           // random 입장인 경우
            roomId = String.valueOf(randomGameDAO.canJoinRandomRoomId());
            gameType = "random";
        }

        HttpSession session = req.getSession();

        if("0".equals(roomId)){
            // 방 없을 경우 alert 띄움
            req.setAttribute("msg" , "참여할 수 있는 방이 없습니다.");
            req.setAttribute("url" , "/main");

            req.getRequestDispatcher("/WEB-INF/view/include/alert.jsp").forward(req, res);
        }
        else{
            session.setAttribute("type", "enter");
            session.setAttribute("room", roomId);

            System.out.println("enter room 서블릿 실행");
            String redirectURL = "/"+ gameType+"-game?room="+roomId+"&type=enter";

            res.sendRedirect(redirectURL);
        }

    }
}
