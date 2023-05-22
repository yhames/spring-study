package com.oopinspring.mvc.domain;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Alias("boardVO")   // Mybatis 에서 해당 이름("boardVO")으로 클래스 매핑
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardVO {
    private int seq;
    private String title;
    private String content;
    private String writer;
    private int password;
    private Timestamp regDate;
    private int cnt;

    @Builder
    public BoardVO(int seq, String title, String content, String writer, int password) {
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
        this.cnt = 0;
    }
}
