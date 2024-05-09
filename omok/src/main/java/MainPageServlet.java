import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import DAO.MainPageDAO;

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
        // 메인 페이지 로직
        // 랭킹을 위해 멤버 정보 받아오기
        res.setContentType("text/html; charset=utf-8");
        MainPageDAO dao = new MainPageDAO();
        List<UserVO> list = dao.getMemberList();
        req.setAttribute("userList", list);
        System.out.println(list);
//        req.getRequestDispatcher("/WEB-INF/view/MainPage.jsp").forward(req, res);
    }
}
