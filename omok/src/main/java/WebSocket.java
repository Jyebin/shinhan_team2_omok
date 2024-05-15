import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

    // WebSocket으로 브라우저가 접속하면 요청되는 함수
    @OnOpen
    public void handleOpen(Session sess, @PathParam("room") String room, @PathParam("type") String type) throws IOException {
        System.out.println("socket loading");
        System.out.println("type : " + type);
        if ("create".equals(type)) {
            // 방 생성 로직
            List<Session> newRoom = new ArrayList<>();
            newRoom.add(sess);
            waitingRoom.put(room, newRoom);
        } else if ("enter".equals(type)) {
            // 방 입장 로직
            List<Session> findRoom = waitingRoom.get(room);
            if (findRoom == null) { // 방이 없을 경우
                System.out.println("create- "+waitingRoom.size() + " 개 존재");
                sess.getAsyncRemote().sendText("방이 존재하지 않습니다.");
                sess.close();
            }
            System.out.println("enter room: "+room);
            System.out.println("waitingRoom: "+waitingRoom);
            System.out.println("waitingRoomSize: "+waitingRoom.size());
            waitingRoom.get(room).add(sess); // 기존 방 session list에 새로 입장한 사람 session 넣고

            fullRoom.put(room, waitingRoom.get(room)); // room, arraylist 그대로 fullRoom에 삽입
            waitingRoom.remove(room); // 기존 대기방에서 삭제
        }
        System.out.println("clear");
    }

    // WebSocket으로 메시지가 오면 요청되는 메서드
    @OnMessage
    public void handleMessage(Session recieveSession, String message, @PathParam("type") String type, @PathParam("room") String room) throws IOException, ParseException {
        System.out.println(room);
        System.out.println(message);
        System.out.println(type);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(message);
        System.out.println("jsonObject.get(\"event\") : "+jsonObject.get("event"));
        if(jsonObject.get("event").equals("chat")){
            if (this.fullRoom.get(room)==null){
                JSONObject data = new JSONObject();
                data.put("room", room);
                data.put("type", type);
                data.put("message", jsonObject.get("message"));
                data.put("event" , "chat");
                recieveSession.getBasicRemote().sendText(data.toString());
            } else {
                for(int i = 0; i < this.fullRoom.get(room).size(); ++i) {
                    Session waitingSession = this.fullRoom.get(room).get(i);
                    JSONObject data = new JSONObject();
                    data.put("room", room);
                    data.put("type", type);
                    data.put("message", jsonObject.get("message"));
                    data.put("event" , "chat");
                    waitingSession.getBasicRemote().sendText(data.toString());
                }
            }
        } // chat 이벤트 끝
        else if ("naming".equals(jsonObject.get("event"))) {
            // 이름 주고받는 이벤트 시작
            while (fullRoom.get(room) == null) {
                // 무한 루프, room 번호로 fullRoom이 생기면 탈출
            }
            int sessionIndex = 0;
            JSONObject data = new JSONObject();
            data.put("enemyName", jsonObject.get("enemyName"));
            data.put("event", "naming");
            if (recieveSession == fullRoom.get(room).get(0)) {
                sessionIndex = 1;
            }
            Session waitingSession = fullRoom.get(room).get(sessionIndex);
            waitingSession.getAsyncRemote().sendText(data.toString());
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