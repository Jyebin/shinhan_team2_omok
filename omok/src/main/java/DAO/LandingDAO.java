package DAO;

import VO.UserVO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LandingDAO {
    private Connection con = null;
    private DataSource dataSource = null;

    public LandingDAO() {
        try {
            Context init = new InitialContext();
            dataSource = (DataSource) init.lookup("java:comp/env/jdbc/mysql");
            System.out.println("연결 성공");
        } catch (Exception e) {
            System.out.println("연결 실패");
            e.printStackTrace();
        }
    }

    public UserVO loginCheck(String paramId, String paramPwd) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        UserVO vo = null;
        try {
            con = dataSource.getConnection();
            String query = "select user_id , user_name , user_pw , user_win_cnt , user_game_cnt  from user where user_name ='" + paramId + "' and user_pw = '" + paramPwd + "'";
            preparedStatement = con.prepareStatement(query);
            //preparedStatement.setString(1 , paramId);
            //preparedStatement.setString(2 , paramPwd);
            resultSet = preparedStatement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String pw = resultSet.getString("user_pw");
                String name = resultSet.getString("user_name");
                int userWinCnt = resultSet.getInt("user_win_cnt");
                int userGameCnt = resultSet.getInt("user_win_cnt");

                vo = new UserVO();
                vo.setUserId(id);
                vo.setUserPw(pw);
                vo.setUserName(name);
                vo.setUserWinCnt(userWinCnt);
                vo.setUserGameCnt(userGameCnt);
            }
            System.out.println("회원 조회 성공");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("회원 조회 실패");
        } finally {
            try {
                resultSet.close();
            } catch (Exception e) {
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
            }
            try {
                con.close();
            } catch (Exception e) {
            }
        }
        return vo;
    }
}
