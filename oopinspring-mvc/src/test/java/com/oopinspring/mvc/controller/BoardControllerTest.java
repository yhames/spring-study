package com.oopinspring.mvc.controller;

import com.oopinspring.mvc.domain.BoardVO;
import com.oopinspring.mvc.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest(controllers = BoardController.class)
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

    @Test
    @DisplayName("저장 기능 - SessionAttributes와 ModelAttribute 디버깅")
    void writeDebug() throws Exception {
        // given
        BoardVO boardVO = BoardVO.newInstance();
        MockHttpSession session = new MockHttpSession();

        // when
        mockMvc.perform(post("/board/write")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .session(session)
                .sessionAttr("boardVO", boardVO)
                .characterEncoding("UTF-8")
                .param("title", "t1")
                .param("content", "c1")
                .param("writer", "w1")
                .param("password", "1234")
        ).andExpect(status().is3xxRedirection());

        // then
        assertThat(boardVO.getTitle()).isEqualTo("t1");
        assertThat(boardVO.getContent()).isEqualTo("c1");
        assertThat(boardVO.getWriter()).isEqualTo("w1");
        assertThat(boardVO.getPassword()).isEqualTo(1234);
    }

}