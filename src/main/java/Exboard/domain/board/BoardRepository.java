package Exboard.domain.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity , Integer> {


    @Query( value = "select * from board where cno = :cno and btitle like %:keyword%" , nativeQuery = true )
    Page<BoardEntity> findBybtitle(int cno , @Param("keyword")  String keyword , Pageable pageable);
    // List 대신 Page 사용하는이유 : Page 관련된 메소드를 사용하기 위해

    // 2. 내용 검색
    @Query( value = "select * from board where cno = :cno and bcontent like %:keyword%" , nativeQuery = true )
    Page<BoardEntity> findBybcontent(  int cno ,    @Param("keyword") String keyword , Pageable pageable  );
    // 3. 작성자 검색
    @Query( value = "select * from board where cno = :cno and bid like %:keyword%" , nativeQuery = true  )
    Page<BoardEntity> findBybid(   int cno ,     @Param("keyword") String keyword , Pageable pageable   );
}
