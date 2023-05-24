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
//        model.addAttribute("boardVO",  BoardVO.newInstance());
        return "/board/write";
    }

    @PostMapping("/write")
    public String write(HttpServletRequest request, @ModelAttribute @Valid BoardVO boardVO, BindingResult bindingResult) {
        log.info("HttpServletRequest.getParameter.title={}", request.getParameter("title"));
        log.info("HttpServletRequest.getParameter.content={}", request.getParameter("content"));
        log.info("HttpServletRequest.getParameter.writer={}", request.getParameter("writer"));
        log.info("HttpServletRequest.getParameter.password={}", request.getParameter("password"));
        log.info("write().boardVO.getTitle()={}", boardVO.getTitle());
        log.info("write().boardVO.getContent()={}", boardVO.getContent());
        log.info("write().boardVO.getWriter()={}", boardVO.getWriter());
        log.info("write().boardVO.getPassword()={}", boardVO.getPassword());
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
    public String edit(HttpServletRequest request, @Valid @ModelAttribute BoardVO boardVO, BindingResult bindingResult,
                       int pwd, SessionStatus sessionStatus, Model model) {
        log.info("HttpServletRequest.getParameter.title={}", request.getParameter("title"));
        log.info("HttpServletRequest.getParameter.content={}", request.getParameter("content"));
        log.info("HttpServletRequest.getParameter.writer={}", request.getParameter("writer"));
        log.info("HttpServletRequest.getParameter.password={}", request.getParameter("password"));
        log.info("pwd={}", pwd);
        log.info("edit().boardVO.getTitle()={}", boardVO.getTitle());
        log.info("edit().boardVO.getContent()={}", boardVO.getContent());
        log.info("edit().boardVO.getWriter()={}", boardVO.getWriter());
        log.info("edit().boardVO.getPassword()={}", boardVO.getPassword());
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
}
