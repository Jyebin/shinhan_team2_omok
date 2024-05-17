import DAO.MainPageDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "rankingServlet", value = "/main.do")
public class RankingServlet extends HttpServlet {

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
        // 메인 페이지 로직

        // 랭킹을 위해 멤버 정보 받아오기
        MainPageDAO dao = new MainPageDAO();
        String name = req.getParameter("name");
        Map<String, Integer> allUserList = dao.getUserList(name);

        PrintWriter out = res.getWriter();
        for (String names : allUserList.keySet()) {
            out.println("<div class=\"rank-panel-item\">");
            out.println("<div class=\"rank-panel-item-rank\">" + allUserList.get(names) + "</div>");
            out.println("<div class=\"rank-panel-item-id\">" + names + "</div>");
            out.println("</div>");
        }
    }
}
