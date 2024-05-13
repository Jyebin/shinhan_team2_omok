import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "enterRoomServlet", value = "/enterRoom")
public class EnterRoomServlet extends HttpServlet {
    // enterRoom으로 오면
    // DB내에 is_custom이 false인 방 중 room_code로 랜덤 참여가 가능한 방인지 확인
    // room_code = true, false <- true는 참여 가능한 방, false 참여 불가능한 방(2인 이상 들어간 방)
    // if(is_custom == false && room_code == true) -> limit = 1해서(그냥 젤 앞 값 가져옴) 방 참여
    // else if 출력 값 null 이라면 alert"입장가능한 방이 없습니다"

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=utf-8");

        String roomType = req.getParameter("name");

        String room = "1";
        req.setAttribute("room",room);
        req.setAttribute("color", "white");
        req.setAttribute("type", "enter");

        String redirectURL = "/random-game?room="+room+"&color=white";
        res.sendRedirect(redirectURL);
    }
}
