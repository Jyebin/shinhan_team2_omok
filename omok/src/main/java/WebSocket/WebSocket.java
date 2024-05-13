package WebSocket;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

// WebSocket의 호스트 주소 설정
@ServerEndpoint("/websocket")
public class WebSocket {
    // WebSocket으로 브라우저가 접속하면 요청되는 함수
    @OnOpen
    public void handleOpen(){
        //콘솔에 접속 로그 출력
        System.out.println("연결되었습니다.");
    }

    //WebSocket으로 메세지가 오면 요청되는 함수
    @OnMessage
    public String handleMessage(String message){
        //콘솔에 메세지 내용 출력
        System.out.println("메세지 : " + message);

        String replyMessage = "응답 : " + message;
        //응답 메세지 콘솔에 출력
        System.out.println("응답 메세지 : " + replyMessage);

        return replyMessage;
    }

    //연결이 끊겼을 경우
    @OnClose
    public void handleClose(){
        System.out.println("연결 끊김");
    }

    //통신 에러가 발생한 경우
    @OnError
    public void handleError(Throwable t){
        t.printStackTrace();
    }
}
