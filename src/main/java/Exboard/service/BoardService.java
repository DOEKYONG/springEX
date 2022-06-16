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

    // json
    public JSONArray board_list(){
        JSONArray jsonArray = new JSONArray();
        List<BoardEntity> boardEntityList = boardRepository.findAll();

        for(BoardEntity boardEntity : boardEntityList) {
            JSONObject object = new JSONObject();
            object.put("btitle",boardEntity.getBtitle());
            object.put("bcontent",boardEntity.getBcontent());
            object.put("bid",boardEntity.getBid());
            object.put("bpassword",boardEntity.getBpassword());
            jsonArray.put(object);
        }

        return jsonArray;
    }


}
