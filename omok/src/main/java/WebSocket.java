import java.io.IOException;
import java.util.*;

import org.json.JSONObject;

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
    private static Map<String, List<Session>> fullRoom = new HashMap<>();
    // 이 메모리가 공유가 안 됨,, 각자 객체를 생성해서 그런ㄷ ㅡㅅ

    // WebSocket으로 브라우저가 접속하면 요청되는 함수
    // type -> create, enter로 나뉨 ,, 로직 다시 짜야됨
    @OnOpen
    public void handleOpen(Session sess, @PathParam("room") String room, @PathParam("type") String type) throws IOException {
        System.out.println("socket loading");
        System.out.println("type : " + type);
        if ("create".equals(type)) {
            // 방 생성 로직
            List<Session> newRoom = new ArrayList<>();
            newRoom.add(sess);
            waitingRoom.put(room, newRoom);
            System.out.println(waitingRoom.size() + " 개 존재");
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
        System.out.println("clear");
    }

    // WebSocket으로 메시지가 오면 요청되는 메서드
    @OnMessage
    public void handleMessage(Session session, @PathParam("room") String room, String message) {
        try {
            // JSON 파싱
            JSONObject jsonObject = new JSONObject(message);
            String isOmok = jsonObject.getString("event");
            if (isOmok.equals("omok")) {
                System.out.println("메세지 : " + message);

                int x = jsonObject.getInt("x");
                int y = jsonObject.getInt("y");
                System.out.println("세션으로 받은 좌표: x=" + x + ", y=" + y);

                processOmok(x, y, session);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processOmok(int x, int y, Session session){
        //좌표에 돌을 둠
        broadCastOmokMove(x,y,session); //전달
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
                    System.out.println("상대에게 전송된 좌표 x:"+x+"y:"+y);
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