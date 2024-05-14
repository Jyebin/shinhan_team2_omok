import DAO.LandingDAO;
import VO.UserVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "landingServlet", value = "/landing")
public class LandingPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/LandingPage.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String pwd = request.getParameter("pwd");

        // 메인 페이지 로직
        // 랭킹을 위해 멤버 정보 받아오기
        LandingDAO dao = new LandingDAO();

        UserVO vo = dao.loginCheck(id, pwd);
        System.out.println(vo);
        if (vo == null) {
            request.setAttribute("msg", "회원정보가 일치하지 않습니다.");
            request.setAttribute("url", "/landing");
            request.getRequestDispatcher("/WEB-INF/view/include/alert.jsp").forward(request, response);
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", vo);
        response.sendRedirect("/main");
    }
}