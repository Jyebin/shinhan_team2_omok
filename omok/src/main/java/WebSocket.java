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
    // 대기 방, 꽉찬 방 리스트
     private static Map<String, List<Session>> waitingRoom = new LinkedHashMap<>();
     private static Map<String, List<Session>> fullRoom = new LinkedHashMap<>();

    @OnOpen
    public void handleOpen(Session sess, @PathParam("room") String room, @PathParam("type") String type) throws IOException {
        if ("create".equals(type)) {
            // 방 생성 로직
            List<Session> newRoom = new ArrayList<>();
            newRoom.add(sess);
            waitingRoom.put(room, newRoom);
        } else if ("enter".equals(type)) {
            // 방 입장 로직
            List<Session> findRoom = waitingRoom.get(room);
            if (findRoom == null) { // 방이 없을 경우
                sess.getAsyncRemote().sendText("방이 존재하지 않습니다.");
                sess.close();
            }
            waitingRoom.get(room).add(sess); // 기존 방 session list에 새로 입장한 사람 session 넣고

            fullRoom.put(room, waitingRoom.get(room)); // room, arraylist 그대로 fullRoom에 삽입
            waitingRoom.remove(room); // 기존 대기방에서 삭제
        }
    }

    @OnMessage
    public void handleMessage(Session recieveSession, String message, @PathParam("type") String type, @PathParam("room") String room) throws IOException {
        JSONObject jsonObject = new JSONObject(message);
        JSONObject data = new JSONObject();
        if(jsonObject.get("event").equals("chat")){
            if (this.fullRoom.get(room)==null){
                data.put("room", room);
                data.put("type", type);
                data.put("message", jsonObject.getString("message"));
                data.put("event" , "chat");
                recieveSession.getBasicRemote().sendText(data.toString());
            } else {
                for(int i = 0; i < this.fullRoom.get(room).size(); ++i) {
                    Session waitingSession = this.fullRoom.get(room).get(i);
                    data.put("room", room);
                    data.put("type", type);
                    data.put("message", jsonObject.getString("message"));
                    data.put("event" , "chat");
                    waitingSession.getBasicRemote().sendText(data.toString());
                }
            }
        } // chat 이벤트 끝
        else if ("naming".equals(jsonObject.getString("event"))) {
            // 이름 주고받는 이벤트 시작
            while (fullRoom.get(room) == null) {
                // 무한 루프, room 번호로 fullRoom이 생기면 탈출
            }
            int sessionIndex = 0;
            data.put("enemyName", jsonObject.getString("enemyName"));
            data.put("event", "naming");
            if (recieveSession == fullRoom.get(room).get(0)) {
                sessionIndex = 1;
            }
            Session waitingSession = fullRoom.get(room).get(sessionIndex);
            waitingSession.getAsyncRemote().sendText(data.toString());
        }
        else if ("omok".equals(jsonObject.getString("event"))) {
            int x = jsonObject.getInt("x");
            int y = jsonObject.getInt("y");
            processOmok(x, y, recieveSession);
        }
    }

    private void processOmok(int x, int y, Session session) {
        //좌표에 돌을 둠
        broadCastOmokMove(x, y, session); //전달
    }

    private void broadCastOmokMove(int x, int y, Session session) {
        // 현재 방에 있는 모든 클라이언트에게 오목 돌 놓기 이벤트 메시지 전송
        for (List<Session> sessions : fullRoom.values()) {
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
    public void handleClose() {
        System.out.println("client is now disconnected...");
    }

    // WebSocket과 브라우저 간에 통신 에러가 발생하면 요청되는 함수.
    @OnError
    public void handleError(Throwable t) {
        t.printStackTrace();
    }
}