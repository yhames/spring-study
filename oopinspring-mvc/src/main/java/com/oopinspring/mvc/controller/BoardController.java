package com.oopinspring.mvc.controller;

import com.oopinspring.mvc.domain.BoardVO;
import com.oopinspring.mvc.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 모델을 생성하는 것은 `DispatcherServlet`이다.
     * `DispatcherServlet`이 생성한 참조변수는 @RequestMapping 어노테이션이 붙은 메서드에서
     * 인자를 선언하기만 하면 자동으로 받을 수 있다.
     */
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("boardList", boardService.list());
        /**
         * URL 뒷부분 반환하면 ViewResolver 에서 전체 URL 생성
         * Resolve 설정은 `application.properties`에서 prefix, suffix 설정
         */
        return "/board/list";
    }

    @GetMapping("/read/{seq}")
    public String read(Model model, @PathVariable int seq) {
        model.addAttribute("boardVO", boardService.read(seq));
        return "/board/read";
    }

    @GetMapping("/write")
    public String write() {
        return "/board/write";
    }

    @PostMapping("/write")
    public String write(BoardVO boardVO) {
        boardService.write(boardVO);
        return "redirect:/board/list";
    }
}
