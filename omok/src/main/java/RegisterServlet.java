import DAO.RegisterDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "registerServlet", value = "/register")
public class RegisterServlet extends HttpServlet {

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

        System.out.println("cmd: "+cmd);
        System.out.println("id: "+id);
        System.out.println("pwd: "+pwd);

        if("addmember".equals(cmd)){
            RegisterDAO dao = new RegisterDAO();

            boolean result = dao.addMember(id, pwd);

            if(result){
                request.setAttribute("msg" , "회원이 추가되었습니다.");
                request.setAttribute("url" , "/landing");

                request.getRequestDispatcher("/WEB-INF/view/include/alert.jsp").forward(request, response);
            }else{
                request.setAttribute("msg" , "회원추가가 실패하였습니다.");
                request.setAttribute("url" , "/register");

                request.getRequestDispatcher("/WEB-INF/view/include/alert.jsp").forward(request, response);
            }

        }else if ("dupcheck".equals(cmd)){
            RegisterDAO dao = new RegisterDAO();
            boolean result = dao.dupCheck(id);
            System.out.println("result: "+result);
            response.setContentType("application/x-json; charset=utf-8");
            PrintWriter out = response.getWriter();
            if(result == false){
                out.print("false");
            }else{
                out.print("true");
            }


        }





    }


}
