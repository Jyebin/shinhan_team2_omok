package DAO;

import VO.UserVO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterDAO {
    private Connection con = null;
    private DataSource dataSource = null;

    public RegisterDAO() {
        try {
            Context init = new InitialContext();
            dataSource = (DataSource)init.lookup("java:comp/env/jdbc/mysql");
            System.out.println("연결 성공");
        } catch (Exception e) {
            System.out.println("연결 실패");
            e.printStackTrace();
        }
    }

    public boolean addMember(String paramId , String paramPwd) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean result = false;
        UserVO vo =null;
        try {
            con = dataSource.getConnection();
            String query = "insert into user ( user_name , user_pw , user_win_cnt , user_game_cnt ) values (? , ? , 0 , 0)";
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1 , paramId);
            preparedStatement.setString(2 , paramPwd);
            int row = preparedStatement.executeUpdate();
            if(row == 0){
                result = false;
            }else {
                result = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
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
        return result;
    }





    public boolean dupCheck(String paramId) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
            boolean result = true;
            UserVO vo =null;
            try {
                con = dataSource.getConnection();
                String query = "select COUNT(*) cnt from user where user_name=?";
                preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1 , paramId);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
            int row = resultSet.getInt("cnt");
            System.out.println("row: "+row);
            if(row == 1){
                result = false;
            } else {
                result = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
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
        return result;
    }



}
