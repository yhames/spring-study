package com.oopinspring.mvc.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Slf4j
@Alias("boardVO")   // Mybatis 에서 해당 이름("boardVO")으로 클래스 매핑
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardVO {
    private int seq;
    private String title;
    private String content;
    private String writer;
    private int password;
    private Timestamp regDate;
    private int cnt;

    public static BoardVO newInstance() {
        return new BoardVO();
    }

    @Builder
    public BoardVO(String title, String content, String writer, int password) {
        log.info("PartialArgsConstructor execute");
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
        this.cnt = 0;
    }
}
