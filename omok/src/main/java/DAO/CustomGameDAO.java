package DAO;

import VO.GameListVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CustomGameDAO {

    private Connection con = null;
    // MySQL 연결 정보
    private final String url = "jdbc:mysql://localhost:3306/omok";
    private final String username = "root";
    private final String password = "root";

    public CustomGameDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            System.out.println("연결 성공");
        } catch (Exception e) {
            System.out.println("연결 실패");
            e.printStackTrace();
        }
    }

    public ArrayList<GameListVO> gameListSelect() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<GameListVO> gameList = new ArrayList<>();

        try {
            String query = "select * from gamelist";
            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int gameId = resultSet.getInt("game_id");
                Boolean isCustom = resultSet.getBoolean("is_custom");
                String gameCode = resultSet.getString("game_code");
                int userId = resultSet.getInt("user_id");

                GameListVO vo = new GameListVO();
                vo.setGameId(gameId);
                vo.setIsCustom(isCustom);
                vo.setGameCode(gameCode);
                vo.setUserId(userId);
                gameList.add(vo);
            }
            System.out.println("게임 목록 조회 성공");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("게임 목록 조회 실패");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return gameList;
    }
}
