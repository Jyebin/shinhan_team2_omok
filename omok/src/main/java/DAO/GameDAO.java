package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GameDAO {
    private Connection connection = null;

    // MySQL 연결 정보
    private final String url = "jdbc:mysql://localhost:3306/omok";
    private final String username = "root";
    private final String password = "root";

    public GameDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createGame(boolean isCustom, String roomCode) { //게임 방 생성
        try {
            String query = "insert into GameList (is_custom, game_code, can_enter) values(?,?,true)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, isCustom);
            preparedStatement.setString(2, roomCode);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeIsCustom(int roomId) { //현재 방의 roomId 기반으로 그 방의 is_custom 값 변경
        try {
            boolean isCustom = true;
            String query = "select is_custom where game_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, roomId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isCustom = resultSet.getBoolean("is_custom");
            } else {
                System.out.println("일치하는 방이 없습니다.");
            }
            query = "update gamelist set is_custom=? where game_id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, !isCustom);
            preparedStatement.setInt(2, roomId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StringBuilder findRoomId() {
        StringBuilder gameId = new StringBuilder();
        try {
            String query = "select last_insert_id() from gamelist";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String temp = String.valueOf(resultSet.getInt("last_insert_id()"));
                gameId.append(temp);
            } else {
                System.out.println("일치하는 방이 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameId;
    }
}