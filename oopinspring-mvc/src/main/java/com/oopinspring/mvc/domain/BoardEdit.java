package com.oopinspring.mvc.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class BoardEdit {

    private int seq;

    @NotEmpty(message = "제목을 입력하세요.")
    @Length(min = 2, max = 5, message = "제목은 2자 이상, 5자 미만으로 입력하세요.")
    private String title;

    @NotEmpty(message = "내용을 입력하세요.")
    private String content;

    @NotEmpty(message = "작성자를 입력하세요.")
    private String writer;

    private int password;

    public BoardEdit(BoardVO boardVO) {
        this.seq = boardVO.getSeq();
        this.title = boardVO.getTitle();
        this.content = boardVO.getContent();
        this.writer = boardVO.getWriter();
    }
}
