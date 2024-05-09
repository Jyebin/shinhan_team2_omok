package VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO {
    private int userId;
    private String userName;
    private String userPw;
    private int userWinCnt;
    private int userGameCnt;
}
