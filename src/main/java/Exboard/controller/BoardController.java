package Exboard.controller;

import Exboard.dto.BoardDto;
import Exboard.service.BoardService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    // 2-2 글 목록 출력
    @GetMapping("/getlist")
    public void getlist(  HttpServletResponse response){

        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardservice.board_list());

        }catch(Exception e) {System.out.println(e);}

    }

    // 3-1 글 보기 이동
    @GetMapping("/boardview")
    public String view(){
        return "/board/boardview";
    }
    // 3-2 글보기 출력
    @GetMapping("/getboardview")
    @ResponseBody
    public void getboardview(@RequestParam("bno")int bno , HttpServletResponse response){
        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardservice.board_view(bno));
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
