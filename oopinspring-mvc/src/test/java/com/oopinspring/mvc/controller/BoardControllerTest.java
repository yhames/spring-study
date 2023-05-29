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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest(controllers = BoardController.class)
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

    @Test
    @DisplayName("저장 기능 - SessionAttributes와 ModelAttribute")
    void writeModelAttributeDebug() throws Exception {
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
                        .param("password", "1234"))
                .andExpect(status().is3xxRedirection())
                .andDo(print());

        // then
        assertThat(boardVO.getTitle()).isEqualTo("t1");
        assertThat(boardVO.getContent()).isEqualTo("c1");
        assertThat(boardVO.getWriter()).isEqualTo("w1");
        assertThat(boardVO.getPassword()).isEqualTo(1234);
    }

    @Test
    @DisplayName("저장 기능 - BindingResult와 @Valid")
    void writeBindExceptionDebug() throws Exception {
        BoardVO boardVO = BoardVO.newInstance();
        MockHttpSession session = new MockHttpSession();

        // given
        mockMvc.perform(post("/board/write")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .characterEncoding("UTF-8")
                        .session(session)
                        .sessionAttr("boardVO", boardVO)
                        .param("title", "")
                        .param("content", "")
                        .param("writer", "")
                        .param("password", "asdf"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("boardVO"))
                .andDo(print());
    }

}