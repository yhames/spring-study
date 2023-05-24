package com.oopinspring.mvc.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

@Slf4j
@Alias("boardVO")   // Mybatis 에서 해당 이름("boardVO")으로 클래스 매핑
@Getter
@Setter
@NoArgsConstructor
public class BoardVO {

    private int seq;

    @Length(min = 2, max = 5, message = "제목은 2자 이상, 5자 미만으로 입력하세요.")
    private String title;

    @NotEmpty(message = "내용을 입력하세요.")
    private String content;

    @NotEmpty(message = "작성자를 입력하세요.")
    private String writer;

    private int password;

    private Timestamp regDate;

    private int cnt;

//    public static BoardVO newInstance() {
//        return new BoardVO();
//    }

    @Builder
    public BoardVO(String title, String content, String writer, int password) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
        this.cnt = 0;
    }
}
