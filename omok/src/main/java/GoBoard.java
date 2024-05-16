import lombok.Getter;
import lombok.Setter;

import javax.websocket.Session;

public class GoBoard {
    @Getter
    String winner, loser;

    @Setter
    @Getter
    private Session black, white;

    int size = 13;
    private String[][] board = new String[size][size];

    public GoBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ".";
                // 게임판 생성 시, 초기화
            }
        }
    }

    public boolean check(int x, int y, Session session) {
        String go;
        if (black.equals(session)) go = "O"; // 검은 돌 = o
        else go = "X"; // 흰 돌 = x

        board[x][y] = go;

        return isGameOver();
        // x, y 좌표에 돌 놓고 5개 됐는지 체크
        /* 만약 다섯 개 됐으면 어떤 플레이어가 이겼는지 체크
         * -> 보드 배열 만들어서 O가 5개면 create, 즉 흑 플레이어가 이긴 것 = winner에 대입
         * -> 반대로 X가 5개면 enter, 즉 백 플레이어가 이긴 것 = loser에 대입
         *
         * 누가 놓는지는 세션으로 구분
         * 결정되면 return true, 안 되면 false
         */
    }

    private boolean isGameOver() {
        boolean isVisited[][] = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!isVisited[i][j] && board[i][j] != ".") {
                    isVisited[i][j] = true;
                    if (searchOmok(i, j, isVisited)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean searchOmok(int i, int j, boolean isVisited[][]) {
        int[] dx = {-1, 1, 0, 0, -1, 1, -1, 1};
        int[] dy = {0, 0, -1, 1, -1, 1, 1, -1};

        int cnt = 0;
        boolean isContinued = true;

        for (int d = 0; d < dx.length; d++) {
            int x = i + dx[d];
            int y = j + dy[d];
            isContinued = true;
            cnt = 0;
            if (x < 0 || y < 0 || x >= size || y >= size) continue;
            while(isContinued) {
                if (board[x][y].equals(board[i][j])) cnt += 1;
                else isContinued = false;
                x += dx[d];
                y += dy[d];
            }
            if (cnt >= 4) {
                System.out.println(cnt);
                if (board[i][j].equals("X")) {
                    winner = "white";
                    loser = "black";
                }
                else if (board[i][j].equals("O"))  {
                    winner = "black";
                    loser = "white";
                }
                return true;
            }
        }
        return false;
    }
}