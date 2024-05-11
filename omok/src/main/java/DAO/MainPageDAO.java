package DAO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import VO.UserVO;

public class MainPageDAO {
    private Connection con = null;
    private DataSource dataSource = null;

    public MainPageDAO() {
        try {
            Context init = new InitialContext();
            dataSource = (DataSource) init.lookup("java:comp/env/jdbc/mysql");
            System.out.println("Success");
        } catch (Exception e) {
            System.out.println("Fail");
            e.printStackTrace();
        }
    }

    public List<UserVO> getMemberList() {
        List<UserVO> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        try {
            con = dataSource.getConnection();
            String query = "select * from user order by user_id";
            pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery(query);
            while (rs.next()) {
                // 한 행(회원 1명당) 처리
                int id = rs.getInt("user_id");
                String pw = rs.getString("user_pw");
                String name = rs.getString("user_name");
                int userWinCnt = rs.getInt("user_win_cnt");
                int userGameCnt = rs.getInt("user_win_cnt");

                UserVO vo = new UserVO();
                vo.setUserId(id);
                vo.setUserPw(pw);
                vo.setUserName(name);
                vo.setUserWinCnt(userWinCnt);
                vo.setUserGameCnt(userGameCnt);
                list.add(vo);
            }
            rs.close();
            pstmt.close();
            con.close();
            System.out.println("read success");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("read fail");
        }
        return list;
    }
}
