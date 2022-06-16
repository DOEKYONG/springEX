package Exboard.domain.board;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="board")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class BoardEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int bno;
    private String btitle;
    private String bcontent;
    private String bid;
    private String bpassword;
}
