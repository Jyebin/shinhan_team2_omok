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
    private DataSource dataFactory;

    public MainPageDAO() {
        try {
            Context init = new InitialContext();
            dataFactory = (DataSource) init.lookup("java:comp/env/jdbc/mysql");
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
            con = dataFactory.getConnection();
            String query = "select user_name from member order by user_win_cnt desc";
            pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (!rs.next()) {
                UserVO user = new UserVO();
                user.setUserName(rs.getString("user_name"));
                list.add(user);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
