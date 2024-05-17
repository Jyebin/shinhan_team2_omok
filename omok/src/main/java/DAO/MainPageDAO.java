package DAO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

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

    public List<String> getTopRank() {
        List<String> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        try {
            con = dataSource.getConnection();

            String query = "select user_name from user order by user_win_cnt desc limit 3";
            pstmt = con.prepareStatement(query);

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
        while (list.size() < 5) {
            list.add(""); // 빈 문자열 추가로 에러 방지
        }
        return list;
    }

    public Map<String, Integer> getUserList(String name) {
        Map<String, Integer> list = new LinkedHashMap<>();
        PreparedStatement pstmt = null;
        try {
            con = dataSource.getConnection();

            String query = "select user_name, ranking" +
                    " from (select user_name," +
                    " row_number() over(order by user_win_cnt desc) as ranking from user) rk";
            if (name != null && !"".equals(name)) {
                query += " where user_name = ?";
                pstmt = con.prepareStatement(query);
                pstmt.setString(1, name);
            } else {
                pstmt = con.prepareStatement(query);
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.put(rs.getString("user_name"), rs.getInt("ranking"));
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
