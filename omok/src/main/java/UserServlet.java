import DAO.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "userServlet", value = "/register")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/view/RegisterPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String cmd = request.getParameter("cmd");
        String id = request.getParameter("id");
        String pwd = request.getParameter("pwd");

//        System.out.println("cmd: " + cmd);
//        System.out.println("id: " + id);
//        System.out.println("pwd: " + pwd);

        // 회원 추가
        if ("addmember".equals(cmd)) {
            UserDAO dao = new UserDAO();
            boolean result = dao.addMember(id, pwd);
            if (result) {
                request.setAttribute("msg", "회원이 추가되었습니다.");
                request.setAttribute("url", "/landing");
                request.getRequestDispatcher("/WEB-INF/view/include/alert.jsp").forward(request, response);
            } else {
                request.setAttribute("msg", "회원 추가가 실패하였습니다.");
                request.setAttribute("url", "/register");
                request.getRequestDispatcher("/WEB-INF/view/include/alert.jsp").forward(request, response);
            }
        // 회원 삭제
        } else if ("delmember".equals(cmd)) {
            UserDAO dao = new UserDAO();
            HttpSession session = request.getSession();
            String userName = (String) session.getAttribute("name");

            boolean result = dao.delMember(userName, pwd);
            if (result) {
                session.invalidate();
                request.setAttribute("msg", "회원이 삭제되었습니다.");
                request.setAttribute("url", "/landing");
                request.getRequestDispatcher("/WEB-INF/view/include/alert.jsp").forward(request, response);
            } else {
                request.setAttribute("msg", "비밀번호를 틀렸습니다. 회원 삭제에 실패했습니다.");
                request.setAttribute("url", "/main");
                request.getRequestDispatcher("/WEB-INF/view/include/alert.jsp").forward(request, response);
            }
        // 아이디 중복 체크
        } else if ("dupcheck".equals(cmd)) {
            UserDAO dao = new UserDAO();
            boolean result = dao.dupCheck(id);
            response.setContentType("application/x-json; charset=utf-8");
            PrintWriter out = response.getWriter();
            if (!result) {
                out.print("false");
            } else {
                out.print("true");
            }
        }
    }
}
