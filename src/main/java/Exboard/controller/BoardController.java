package Exboard.controller;

import Exboard.domain.board.BoardEntity;
import Exboard.domain.board.BoardRepository;
import Exboard.dto.BoardDto;
import Exboard.service.BoardService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/board")
public class BoardController {
@Autowired
private HttpServletRequest request;
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
    public void getlist(  HttpServletResponse response ,@RequestParam("cno") int cno ){

        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
          //  System.out.println("cno:" +cno);
            response.getWriter().print(boardservice.board_list(cno));

        }catch(Exception e) {System.out.println(e);}

    }

    // 3-1 글 보기 이동
    @GetMapping("/boardview/{bno}")
    public String view(@PathVariable("bno") int bno){
        request.getSession().setAttribute("bno",bno);
        return "/board/boardview";
    }
    // 3-2 글보기 출력
    @GetMapping("/getboardview")
    @ResponseBody
    public void getboardview(HttpServletResponse response){
        int bno = (Integer)request.getSession().getAttribute("bno");
        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardservice.board_view(bno));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // 삭제 메소드
    @DeleteMapping("/delete")
    @ResponseBody
    public boolean delete( @RequestParam("bno") int bno ,@RequestParam("mpassword") String mpassword){
        return boardservice.delete( bno, mpassword );
    }

    // 수정 메소드
    // 3. U  수정페이지 이동
    // 3. 게시물 수정 페이지
    @GetMapping("/update")
    public String update(){

        return "board/boardupdate";
    }

// 수정메소드
    @PutMapping("/update")
    @ResponseBody
    public boolean update( BoardDto boardDto ,@RequestParam("mpassword") String mpassword){
        int bno = (Integer)request.getSession().getAttribute("bno");
        boardDto.setBno(bno);
        return  boardservice.update( boardDto,mpassword );
    }

    // 카테고리 출력 메소드
    @GetMapping("/getcategorylist")
    @ResponseBody
    public  void getCategoryList(  HttpServletResponse response){

        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardservice.getcategoryList());

        } catch (Exception e) {System.out.println(e);}
    }






}
