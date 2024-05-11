package DAO;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class CustomGameDAO {

    private Connection connection = null;

    // MySQL 연결 정보
    private final String url = "jdbc:mysql://localhost:3306/omok";
    private final String username = "root";
    private final String password = "root";


    public CustomGameDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("연결 성공");
        } catch (Exception e) {
            System.out.println("연결 실패");
            e.printStackTrace();
        }
    }

    public void createGame(String roomCode) { //게임 방 생성
        try {
            String query = "insert into GameList (is_custom, game_code) values(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1,true);
            preparedStatement.setString(2,roomCode);
            preparedStatement.executeUpdate();
            System.out.println("방 생성 성공");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("방 생성 실패");
        }
    }


//    public ArrayList<GameListVO> gameListSelect() { //게임 목록 조회
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//
//        ArrayList<GameListVO> gameList = new ArrayList<>();
//
//        try {
//            String query = "select * from GameList";
//            preparedStatement = connection.prepareStatement(query);
//            resultSet = preparedStatement.executeQuery();
//
//            while(resultSet.next()) {
//                int gameId = resultSet.getInt("game_id");
//                Boolean isCustom = resultSet.getBoolean("is_custom");
//                String gameCode = resultSet.getString("game_code");
//                int userId = resultSet.getInt("user_id");
//
//                GameListVO vo = new GameListVO();
//                vo.setGameId(gameId);
//                vo.setIsCustom(isCustom);
//                vo.setGameCode(gameCode);
//                vo.setUserId(userId);
//                gameList.add(vo);
//            }
//            System.out.println("게임 목록 조회 성공");
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("게임 목록 조회 실패");
//        } finally {
//            try {
//                if (resultSet != null) resultSet.close();
//                if (preparedStatement != null) preparedStatement.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return gameList;
//    }
}
