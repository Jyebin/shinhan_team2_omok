import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

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
        List<UserVO> list = dao.getMemberList();
        req.setAttribute("memberList", list);
        for (int i = 0; i < list.size(); i++){
            System.out.println(i + " " + list.get(i).getUserName());
        }
        // 1~3등은 따로 정보 넘겨주기
        req.setAttribute("firstMember", list.get(0).getUserName());
        req.setAttribute("secondMember", list.get(1).getUserName());
        req.setAttribute("thirdMember", list.get(2).getUserName());
        req.getRequestDispatcher("/WEB-INF/view/MainPage.jsp").forward(req, res);
    }
}
