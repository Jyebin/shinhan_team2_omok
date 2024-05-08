package VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO {
    private Long userId;
    private String userName;
    private String userPw;
    private Long userWinCnt;
    private Long userGameCnt;
}
