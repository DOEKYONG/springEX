package Exboard.controller;

import Exboard.dto.BoardDto;
import Exboard.service.BoardService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardservice;

    // 1. 글쓰기 페이지 이동매핑
    @GetMapping("/boardwrite")
    public String write(){
        return "/board/boardwrite";
    }
    // 1-2 글쓰기
    @PostMapping("/boardwrite")
    @ResponseBody
    public boolean save(BoardDto boardDto) {
        return boardservice.boardwrite(boardDto);
    }

    // 2-1 글목록이동
    @GetMapping("/boardlist")
    public String list(){
        return "/board/boardlist";
    }
    @GetMapping("/boardlist")
    public void boardlist(  HttpServletResponse response){
        JSONObject object = boardservice.board_list();
        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardservice);

        }catch(Exception e) {System.out.println(e);}

    }


}
