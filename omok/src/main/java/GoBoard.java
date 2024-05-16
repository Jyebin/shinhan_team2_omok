import lombok.Getter;
import lombok.Setter;

import javax.websocket.Session;

public class GoBoard {
    @Getter
    private Session winner, loser;

    @Setter
    private Session black, white;

    public boolean check(int x, int y, Session session) {
        // x, y 좌표에 돌 놓고 5개 됐는지 체크
        /* 만약 다섯 개 됐으면 어떤 플레이어가 이겼는지 체크
         * -> 보드 배열 만들어서 O가 5개면 create, 즉 흑 플레이어가 이긴 것 = winner에 대입
         * -> 반대로 X가 5개면 enter, 즉 백 플레이어가 이긴 것 = loser에 대입
         *
         * 누가 놓는지는 세션으로 구분
         * 결정되면 return true, 안 되면 false
         */
        return false;
    }
}
