package Exboard.service;

import Exboard.domain.board.BoardEntity;
import Exboard.domain.board.BoardRepository;
import Exboard.dto.BoardDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    HttpServletRequest request;

    public boolean boardwrite(BoardDto boardDto) {

        BoardEntity boardEntity = boardDto.toentity();
        boardRepository.save(boardEntity);
        return true;
    }

    // json 글목록 출력
    public JSONArray board_list() {
        JSONArray jsonArray = new JSONArray();
        List<BoardEntity> boardEntityList = boardRepository.findAll();

        for (BoardEntity boardEntity : boardEntityList) {
            JSONObject object = new JSONObject();
            object.put("bno", boardEntity.getBno());
            object.put("btitle", boardEntity.getBtitle());
            object.put("bcontent", boardEntity.getBcontent());
            object.put("bid", boardEntity.getBid());
            object.put("bpassword", boardEntity.getBpassword());
            jsonArray.put(object);
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
}