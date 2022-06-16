package Exboard.dto;

import Exboard.domain.board.BoardEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {

    private int bno;
    private String btitle;
    private String bcontent;
    private String bid;
    private String bpassword;

    public BoardEntity toentity(){
        return BoardEntity.builder()
                .btitle(this.btitle)
                .bcontent(this.bcontent)
                .bid(this.bid)
                .bpassword(this.bpassword)
                .build();
    }
}
