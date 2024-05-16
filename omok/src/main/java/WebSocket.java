import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/{room}/{type}")
public class WebSocket {
    // gameRoom = 게임 방, gameBoard = 게임 방에 맞는 보드
    // 둘 다 key를 room으로 가짐
    private static Map<String, List<Session>> gameRoom = new LinkedHashMap<>();
    private static Map<String, GoBoard> gameBoard = new HashMap<>();

    @OnOpen
    public void handleOpen(Session session, @PathParam("room") String room, @PathParam("type") String type) throws IOException {
        if ("create".equals(type)) {
            // 방 생성 로직
            List<Session> newRoom = new ArrayList<>();
            newRoom.add(session);

            GoBoard board = new GoBoard();
            board.setBlack(session);

            gameRoom.put(room, newRoom);
            gameBoard.put(room, board);
        } else if ("enter".equals(type)) {
            // 방 입장 로직
            gameBoard.get(room).setWhite(session);
            gameRoom.get(room).add(session); // 기존 방 session list에 새로 입장한 사람 session 넣고
        }
    }

    @OnMessage
    public void handleMessage(Session recieveSession, String message, @PathParam("type") String type, @PathParam("room") String room) throws IOException {
        JSONObject jsonObject = new JSONObject(message);
        JSONObject data = new JSONObject();
        if(jsonObject.get("event").equals("chat")){
            for(int i = 0; i < gameRoom.get(room).size(); ++i) {
                Session waitingSession = gameRoom.get(room).get(i);
                data.put("room", room);
                data.put("type", type);
                data.put("message", jsonObject.getString("message"));
                data.put("event" , "chat");
                waitingSession.getBasicRemote().sendText(data.toString());
            } // gameRoom.get(room)이 비어있을 수 없기 때문에, 예외 삭제
        } // chat 이벤트 끝
        else if ("naming".equals(jsonObject.getString("event"))) {
            // 이름 주고받는 이벤트 시작
            while (gameRoom.get(room).size() < 2) {
                // 무한 루프, room 번호로 fullRoom이 생기면 탈출
            }
            int sessionIndex = 0;
            data.put("enemyName", jsonObject.getString("enemyName"));
            data.put("event", "naming");
            if (recieveSession == gameRoom.get(room).get(0)) {
                sessionIndex = 1;
            }
            Session waitingSession = gameRoom.get(room).get(sessionIndex);
            waitingSession.getAsyncRemote().sendText(data.toString());
        }
        else if ("omok".equals(jsonObject.getString("event"))) {
            int x = jsonObject.getInt("x");
            int y = jsonObject.getInt("y");
            GoBoard board = gameBoard.get(room);
            processOmok(x, y, board, recieveSession, type);
        }
    }

    private void processOmok(int x, int y, GoBoard board, Session session, String type) {
        broadCastOmokMove(x, y, session); // 좌표 전달
        System.out.println(x + " " + y);
        if (board.check(x, y, session)) {
            // 승패가 갈렸는지 여부 체크
            // 갈렸으면 -> winner에게 win 담긴 msg 전달, loser에게 lose 담긴 msg 전달

            JSONObject stateJson = new JSONObject();
            stateJson.put("event", "state");
            stateJson.put("win", board.getWinner());
            stateJson.put("lose", board.getLoser());

            board.getBlack().getAsyncRemote().sendText(stateJson.toString());
            board.getWhite().getAsyncRemote().sendText(stateJson.toString());
        }
    }

    private void broadCastOmokMove(int x, int y, Session session) {
        // 현재 방에 있는 모든 클라이언트에게 오목 돌 놓기 이벤트 메시지 전송
        for (List<Session> sessions : gameRoom.values()) {
            for (Session s : sessions) {
                if (!s.equals(session)) { // 다른 클라이언트에게 전송
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("event", "omok");
                    jsonObject.put("x", x);
                    jsonObject.put("y", y);
                    s.getAsyncRemote().sendText(jsonObject.toString());
                }
            }
        }
    }

    // WebSocket과 브라우저가 접속이 끊기면 요청되는 함수
    @OnClose
    public void handleClose(@PathParam("room") String room, Session session) throws IOException {
        System.out.println("게임 나가기! 소켓 연결 종료");
        gameBoard.remove(room);
        gameRoom.remove(room);
        session.close();
        System.out.println("여기도되나");
    }

    // WebSocket과 브라우저 간에 통신 에러가 발생하면 요청되는 함수.
    @OnError
    public void handleError(Throwable t) {
        System.out.println("HandleError 호출");
        t.printStackTrace();
    }
}