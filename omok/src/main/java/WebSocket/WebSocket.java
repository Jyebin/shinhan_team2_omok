package WebSocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ServerEndpoint("/websocket")
public class WebSocket {
    private static final List<Session> sessionList = new ArrayList<>();

    @OnOpen
    public void handleOpen(Session session) {
        sessionList.add(session);
        System.out.println("연결되었습니다.");
    }

    @OnMessage
    public void handleMessage(String message, Session session) throws IOException {
        System.out.println("메세지 : " + message);

        // 받은 좌표를 모든 클라이언트에게 전송
        for (Session s : sessionList) {
            if (!s.equals(session)) {
                s.getBasicRemote().sendText(message);
            }
        }
    }

    @OnClose
    public void handleClose() {
        System.out.println("연결 끊김");
    }

    @OnError
    public void handleError(Throwable t) {
        t.printStackTrace();
    }
}
