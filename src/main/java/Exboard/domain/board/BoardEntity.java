package Exboard.domain.board;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name="board")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString( exclude = "categoryEntity")
@EntityListeners(AuditingEntityListener.class)// 해당 엔티티를 감지
public class BoardEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int bno;
    private String btitle;
    private String bcontent;
    private String bid;
    private String bpassword;

    @ManyToOne
    @JoinColumn(name = "cno")
    private CategoryEntity categoryEntity;


}
