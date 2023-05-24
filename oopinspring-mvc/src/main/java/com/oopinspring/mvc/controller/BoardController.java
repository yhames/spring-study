package com.oopinspring.mvc.controller;

import com.oopinspring.mvc.domain.BoardVO;
import com.oopinspring.mvc.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/board")
@AllArgsConstructor
@Slf4j
@SessionAttributes("boardVO")
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
    public String write(Model model) {
        model.addAttribute("boardVO", new BoardVO());
        return "/board/write";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute @Valid BoardVO boardVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/board/write";
        }
        boardService.write(boardVO);
        return "redirect:/board/list";
    }

    @GetMapping("/edit/{seq}")
    public String edit(@PathVariable int seq, Model model) {
        BoardVO boardVO = boardService.read(seq);
        model.addAttribute("boardVO", boardVO);
        return "/board/edit";
    }

    @PostMapping("/edit/{seq}")
    public String edit(@ModelAttribute @Valid BoardVO boardVO, BindingResult bindingResult,
                       int pwd, SessionStatus sessionStatus, Model model) {
        if (bindingResult.hasErrors()) {
            return "/board/edit";
        }
        if (boardVO.getPassword() != pwd) {
            model.addAttribute("msg", "비밀번호가 일치하지 않습니다.");
            return "/board/edit";
        }

        boardService.edit(boardVO);
        sessionStatus.setComplete();
        return "redirect:/board/list";
    }

    @GetMapping("/delete/{seq}")
    public String delete(@PathVariable int seq, Model model) {
        model.addAttribute("seq", seq);
        return "/board/delete";
    }

    @PostMapping("/delete")
    public String delete(int seq, int pwd, Model model) {
        int rowCount;
        BoardVO boardVO = new BoardVO();
        boardVO.setSeq(seq);
        boardVO.setPassword(pwd);

        rowCount = boardService.delete(boardVO);

        if (rowCount == 0) {
            model.addAttribute("seq", seq);
            model.addAttribute("msg", "비밀번호가 일치하지 않습니다.");
            return "/board/delete";
        }

        return "redirect:/board/list";
    }
}
