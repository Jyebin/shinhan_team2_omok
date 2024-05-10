package DAO;

import VO.UserVO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RandomGameDAO {
    private Connection con = null;
    DataSource dataSource = null;

    // 생성자에서 DB연결 설정
    public RandomGameDAO() {
        try {
            Context init = new InitialContext();
            dataSource = (DataSource)init.lookup("java:comp/env/jdbc/mysql");
            System.out.println("연결 성공");
        } catch (Exception e) {
            System.out.println("연결 실패");
            e.printStackTrace();
        }
    }

    public UserVO getMember(String userName){
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        UserVO user = new UserVO();

        try {
            con = dataSource.getConnection();
            String query = "select * from user where user_name = "+userName;
            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserPw( resultSet.getString("user_pw"));
                user.setUserName( resultSet.getString("user_name"));
                user.setUserWinCnt(resultSet.getInt("user_win_cnt"));
                user.setUserGameCnt(resultSet.getInt("user_win_cnt"));
            }
            System.out.println("user 조회 성공");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("user 조회 실패");
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public ArrayList<UserVO> getMemberList() {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<UserVO> userList = new ArrayList<UserVO>();

        try {
            con = dataSource.getConnection();
            String query = "select * from user";
            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery(query);

            while(resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String pw = resultSet.getString("user_pw");
                String name = resultSet.getString("user_name");
                int userWinCnt = resultSet.getInt("user_win_cnt");
                int userGameCnt = resultSet.getInt("user_win_cnt");

                UserVO vo = new UserVO();
                vo.setUserId(id);
                vo.setUserPw(pw);
                vo.setUserName(name);
                vo.setUserWinCnt(userWinCnt);
                vo.setUserGameCnt(userGameCnt);

                userList.add(vo);
            }
            System.out.println("user 조회 성공");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("user 조회 실패");
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userList;
    }
}
