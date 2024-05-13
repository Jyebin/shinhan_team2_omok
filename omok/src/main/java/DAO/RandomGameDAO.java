package DAO;

import VO.GameListVO;
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
    PreparedStatement pstmt = null;
    ResultSet resultSet = null;
    DataSource dataSource = null;


    // 생성자에서 DB연결 설정
    public RandomGameDAO() {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            Context init = new InitialContext();
            dataSource = (DataSource) init.lookup("java:comp/env/jdbc/mysql");
            System.out.println("연결 성공");
        } catch (Exception e) {
            System.out.println("연결 실패");
            e.printStackTrace();
        }
    }

    // user name으로 DB상 유저 조회
    public UserVO getMember(String userName) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        UserVO user = new UserVO();

        try {
            con = dataSource.getConnection();
            String query = "select * from user where user_name = " + userName;
            pstmt = con.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserPw(resultSet.getString("user_pw"));
                user.setUserName(resultSet.getString("user_name"));
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
                pstmt.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    // 랜덤 게임 방 생성
    public void createGameRoom( ) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = dataSource.getConnection();
            String query = "insert into GameList (is_custom) values(?)";
            pstmt = con.prepareStatement(query);
            pstmt.setBoolean(1, false);
            pstmt.executeUpdate();

            System.out.println("방 생성 성공");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("방 생성 실패");
        } finally {
            try {
                pstmt.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    // db에서 커스텀 게임으로 전환
    public void switchCustomRoom(GameListVO vo, String gameCode) {
        Connection con = null;
        PreparedStatement pstmt = null;

        int gameId = vo.getGameId();

        try {
            con = dataSource.getConnection();
            String query = "update GameList set is_custom = ?, game_code = ? where game_id = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setBoolean(1, true);
            pstmt.setString(2, gameCode);
            pstmt.setInt(3, vo.getGameId());
            pstmt.executeUpdate();

            System.out.println("방 전환 성공");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("방 전환 실패");
        } finally {
            try {
                pstmt.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}