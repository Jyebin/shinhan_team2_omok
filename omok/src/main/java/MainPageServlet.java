import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import DAO.MainPageDAO;

import VO.UserVO;

@WebServlet(name = "mainServlet", value = "/main")
public class MainPageServlet extends HttpServlet {

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

        // 상위 3명 정보 불러오기
        List<String> topRank = dao.getTopRank();

        // 상위 3명 정보 불러오기, 데이터가 부족할 경우 빈 값을 사용
        String firstMember = topRank.size() > 0 ? topRank.get(0) : "데이터 없음";
        String secondMember = topRank.size() > 1 ? topRank.get(1) : "데이터 없음";
        String thirdMember = topRank.size() > 2 ? topRank.get(2) : "데이터 없음";

        // 전체 멤버 정보 or 검색 멤버 정보 불러오기
        String name = req.getParameter(null);
        Map<String, Integer> allUserList = dao.getUserList(name);

        // attribute 설정
        req.setAttribute("userList", allUserList);
        req.setAttribute("firstMember", firstMember);
        req.setAttribute("secondMember", secondMember);
        req.setAttribute("thirdMember", thirdMember);


        // 유저 검색
        req.getRequestDispatcher("/WEB-INF/view/MainPage.jsp").forward(req, res);
    }
}