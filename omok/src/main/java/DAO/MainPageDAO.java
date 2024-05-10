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

    public List<String> getMemberList(String name) {
        List<String> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        try {
            con = dataSource.getConnection();
            String query = "select * from member";
            if (name != null) {
                query += " where name = ?";
                pstmt = con.prepareStatement(query);
                pstmt.setString(1, name);
            } else {
                query += " order by user_win_cnt";
                pstmt = con.prepareStatement(query);
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("user_name"));
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
