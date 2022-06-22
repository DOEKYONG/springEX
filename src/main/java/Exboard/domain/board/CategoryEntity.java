package Exboard.domain.board;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // 테이블과 매핑
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "category")
@EntityListeners(AuditingEntityListener.class)// 해당 엔티티를 감지
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno;
    private String cname;

    // board 연관관계

    @OneToMany(mappedBy = "categoryEntity",cascade = CascadeType.ALL)
    @Builder.Default
    private List<BoardEntity> boardEntityList = new ArrayList<>();


}
