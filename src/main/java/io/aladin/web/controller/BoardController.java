package io.aladin.web.controller;

import io.aladin.web.entity.Board;
import io.aladin.web.service.BoardSercie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BoardController {
    @Autowired
    private BoardSercie boardService;

    @GetMapping("")
    public String index(){
        System.out.println("index");
        return "redirect:/board/list";
    }

    @GetMapping("/board/write") //localhost:8090/board/write
    public String boardwriteForm(){

        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board , Model model){
        model.addAttribute("message" , "글 작성이 완료 되었습니다.");
        model.addAttribute("SearchUrl" , "/board/list");
        boardService.write(board);

        return "Message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword) {

        Page<Board> list = null;

        if(searchKeyword == null) {
            list = boardService.boardList(pageable);
        }else {
            list = boardService.boardSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardList";

    }

    @GetMapping("/board/view")
    public String boardView(Model model , Integer id){

        model.addAttribute("board",boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id ,Model model){
        boardService.boardDelete(id);

        model.addAttribute("message" , "글 삭제 완료.");
        model.addAttribute("SearchUrl" , "/board/list");

        return "Message";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id , Model model){
        System.out.println("제발류");
        model.addAttribute("board",boardService.boardView(id));

        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id , Board board , Model model){

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        model.addAttribute("message" , "글 수정 완료.");
        model.addAttribute("SearchUrl" , "/board/list");

        // 절대 이렇게 하면 안되고 Jpa에서 제공하는 변경감지나 Merge 기능을 따로 공부하자.
        boardService.write(boardTemp);

        return "Message";
    }
}
