package com.oopinspring.mvc.controller;

import com.oopinspring.mvc.domain.BoardEdit;
import com.oopinspring.mvc.domain.BoardVO;
import com.oopinspring.mvc.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/board")
@AllArgsConstructor
@SessionAttributes("boardEdit")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("boardList", boardService.list());
        return "/board/list";
    }

    @GetMapping("/read/{seq}")
    public String read(Model model, @PathVariable int seq) {
        model.addAttribute("boardVO", boardService.read(seq));
        return "/board/read";
    }

    @GetMapping("/write")
    public String write(Model model) {
        model.addAttribute("boardVO", BoardVO.newInstance());
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
        model.addAttribute("boardEdit", new BoardEdit(boardVO));
        return "/board/edit";
    }

    @PostMapping("/edit/{seq}")
    public String edit(@ModelAttribute @Valid BoardEdit boardEdit, BindingResult bindingResult,
                       SessionStatus sessionStatus) {
        BoardVO boardVO = boardService.read(boardEdit.getSeq());
        if (bindingResult.hasErrors()) {
            return "/board/edit";
        }

        if (boardVO.getPassword() != boardEdit.getPassword()) {
            bindingResult.addError(new FieldError("boardEdit", "password", "비밀번호가 일치하지 않습니다."));
            return "/board/edit";
        }

        boardVO.updateBoardEdit(boardEdit);

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
        BoardVO boardVO = BoardVO.newInstance();
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
