package Exboard.service;

import Exboard.domain.board.BoardEntity;
import Exboard.domain.board.BoardRepository;
import Exboard.domain.board.CategoryEntity;
import Exboard.domain.board.CategoryRepository;
import Exboard.dto.BoardDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
            System.out.println("gggggg"+ entity.getCname());
            System.out.println("TTTTTTTTTTT"+ entity.getCno());
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
    public JSONArray board_list(int cno) {
        JSONArray jsonArray = new JSONArray();
        List<BoardEntity> boardEntityList = boardRepository.findAll();

        for (BoardEntity boardEntity : boardEntityList) {
            System.out.println("CCCCCC"+boardEntity.getCategoryEntity().getCno());
            if(boardEntity.getCategoryEntity().getCno() == cno) {
                JSONObject object = new JSONObject();
                object.put("bno", boardEntity.getBno());
                object.put("btitle", boardEntity.getBtitle());
                object.put("bcontent", boardEntity.getBcontent());
                object.put("bid", boardEntity.getBid());
                object.put("bpassword", boardEntity.getBpassword());
                jsonArray.put(object);
            }

        }

        return jsonArray;
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