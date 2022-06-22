package Exboard.service;

import Exboard.domain.board.BoardEntity;
import Exboard.domain.board.BoardRepository;
import Exboard.domain.board.CategoryEntity;
import Exboard.domain.board.CategoryRepository;
import Exboard.dto.BoardDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    HttpServletRequest request;
    @Transactional
    public boolean boardwrite(BoardDto boardDto) {


        boolean sw = false;
        int cno = 0;
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        for(CategoryEntity entity : categoryEntityList) {

            if(entity.getCname().equals(boardDto.getCategory())) {
                sw = true;
                cno = entity.getCno();
            }
        }
        CategoryEntity categoryEntity = null;
        if(!sw) {
            categoryEntity = CategoryEntity.builder()
                    .cname(boardDto.getCategory())
                    .build();
            categoryRepository.save(categoryEntity);
        } else {
            categoryEntity = categoryRepository.findById(cno).get();
        }
        BoardEntity boardEntity = boardRepository.save(boardDto.toentity());

        boardEntity.setCategoryEntity( categoryEntity );
        categoryEntity.getBoardEntityList().add(boardEntity);
        return true;
    }

    // json 글목록 출력
    public JSONObject getboardlist( int cno ,String key , String keyword , int page  ){

        JSONObject object = new JSONObject();

        Page<BoardEntity> boardEntities = null ; // 선언만

        // Pageable : 페이지처리 관련 인테페이스
        // PageRequest : 페이징처리 관련 클래스
        // PageRequest.of(  page , size ) : 페이징처리  설정
        // page = "현재페이지"   [ 0부터 시작 ]
        // size = "현재페이지에 보여줄 게시물수"
        // sort = "정렬기준"  [   Sort.by( Sort.Direction.DESC , "정렬필드명" )   ]
        // sort 문제점 : 정렬 필드명에 _ 인식 불가능 ~~~  ---> SQL 처리
        Pageable pageable = PageRequest.of( page , 3 , Sort.by( Sort.Direction.DESC , "bno")    ); // SQL : limit 와 동일 한 기능처리

        // 필드에 따른 검색 기능
        if(  key.equals("btitle") ){
            boardEntities = boardRepository.findBybtitle( cno ,  keyword , pageable );
        }else if( key.equals("bcontent") ){
            boardEntities = boardRepository.findBybcontent(  cno , keyword ,  pageable );
        }else if( key.equals("mid") ){
            boardEntities = boardRepository.findBybid( cno ,  keyword , pageable  ); // 찾은 회원 엔티티를 -> 인수로 전달

        }else{ // 검색이 없으면
            boardEntities = boardRepository.findBybtitle( cno , keyword ,  pageable );
        }

        // 페이지에 표시할 총 페이징 버튼 개수
        int btncount = 5;
        // 시작번호버튼 의 번호      [   ( 현재페이지 / 표시할버튼수 ) * 표시할버튼수 +1
        int startbtn  = ( page / btncount ) * btncount + 1;
        // 끝 번호버튼의 번호       [  시작버튼 + 표시할버튼수-1 ]
        int endhtn = startbtn + btncount -1;
        // 만약에 끝번호가 마지막페이지보다 크면 끝번호는 마지막페이지 번호로 사용
        if( endhtn > boardEntities.getTotalPages() ) endhtn = boardEntities.getTotalPages();

        // 엔티티 반환타입을 List 대신 Page 인터페이스 할경우에
//        System.out.println( "검색된 총 게시물 수 : "  + boardEntities.getTotalElements() );
//           System.out.println( "검색된 총 페이지 수 : " + boardEntities.getTotalPages() );
//        System.out.println( "검색된 게시물 정보 : " + boardEntities.getContent() );
//        System.out.println( "현재 페이지수 : " + boardEntities.getNumber() );
//        System.out.println( "현재 페이지의 게시물수 : " + boardEntities.getNumberOfElements() );
//        System.out.println( "현재 페이지가 첫페이지 여부 확인  : " +  boardEntities.isFirst() );
//        System.out.println( "현재 페이지가 마지막 페이지 여부 확인  : " +  boardEntities.isLast() );

        //*  data : 모든 엔티티 -> JSON 변환
        JSONArray jsonArray = new JSONArray();
        for( BoardEntity entity : boardEntities ){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bno", entity.getBno());
            jsonObject.put("btitle", entity.getBtitle());
            jsonObject.put("mid", entity.getBid());
            jsonArray.put(jsonObject);
        }

        // js 보낼 jsonobect 구성
        object.put( "startbtn" , startbtn );       //  시작 버튼
        object.put( "endhtn" , endhtn );         // 끝 버튼
        object.put( "totalpages" , boardEntities.getTotalPages() );  // 전체 페이지 수
        object.put( "data" , jsonArray );  // 리스트를 추가

        return object;
    }



    // json 글 정보 출력
    public JSONObject board_view(int bno) {
        BoardEntity board = boardRepository.findById(bno).get();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bno", board.getBno());
        jsonObject.put("btitle", board.getBtitle());
        jsonObject.put("bcontent", board.getBcontent());
        jsonObject.put("bid", board.getBid());
        jsonObject.put("bpassword", board.getBpassword());
        return jsonObject;

    }
//  삭제메소드
    @Transactional
    public boolean delete( int bno,String mpassword ){
        BoardEntity boardEntity = boardRepository.findById( bno ).get();
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        for(BoardEntity entity : boardEntityList) {
            if(mpassword.equals(boardEntity.getBpassword())) {
                boardRepository.delete(  boardEntity );
                return true;
            }

        }

        return false;
    }

    // 수정메소드
    @Transactional
    public boolean update( BoardDto boardDto , String mpassword){
        Optional<BoardEntity> optionalBoard
                =  boardRepository.findById( boardDto.getBno() );
        BoardEntity boardEntity =  optionalBoard.get();
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        for(BoardEntity entity : boardEntityList) {
            if(entity.getBpassword().equals(mpassword)) {
                System.out.println(mpassword);
                boardEntity.setBtitle( boardDto.getBtitle() );
                boardEntity.setBcontent( boardDto.getBcontent() );
                boardEntity.setBid(boardDto.getBid());
                return true;

            }
        }



        return false;

    }

    // 카테고리 호출 메ㅐ소드
    public JSONArray getcategoryList() {
        List<CategoryEntity> categoryEntityList =categoryRepository.findAll();

        JSONArray jsonArray = new JSONArray();
        for (CategoryEntity entity : categoryEntityList) {
            JSONObject object = new JSONObject();
            object.put("cno",entity.getCno());
            object.put("cname",entity.getCname());
            jsonArray.put(object);
        }
        return jsonArray;
    }

}