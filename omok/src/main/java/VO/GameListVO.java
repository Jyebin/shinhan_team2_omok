package VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameListVO {
    private int gameId;
    private Boolean isCustom;
    private String gameCode;
    private String userId;
}
