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
    void write() throws Exception {
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

        /**
         * @SessionAttributes 사용 전
         * - PartialArgsConstructor을 사용하여 객체 생성 및 초기화
         * - @ModelAttribute는 ModelAttributeMethodProcessor를 구현체로 사용함
         *
         * -> 비밀번호를 문자로 하는 경우 :
         * constructAttribute()의 TypeMismatchException을 catch하는 try...catch...문에서
         * bindingFailure flag true되고 바로 BindException이 터짐
         * 이후에 resolveArgument()에서 해당 예외에 대해 getBindingResult를 통해 bindingResult 가져옴
         * Map<String, Object> bindingResultModel = bindingResult.getModel(); 코드 실행해서
         * 모델에 bindingResult 넣어줌
         *
         * -> 비밀번호를 숫자로 하는 경우 :
         * constructAttribute()에서 bindingFailure flag false로 넘어감
         * 그리고 resolveArgument()의 if (bindingResult == null) 구문에서 getBindingResult를 통해
         * validation 해서 bindingResult 가져옴
         * Map<String, Object> bindingResultModel = bindingResult.getModel(); 코드 실행해서
         * 모델에 bindingResult 넣어줌
         */

        /**
         * @SessionAttributes 사용 후
         * - 객체를 미리 생성해서 모델에 전달하고 그 객체를 계속 사용함
         * - @ModelAttribute는 ModelAttributeMethodProcessor를 구현체로 사용함
         * -> 객체가 이미 생성되어있고 ModelAttributeMethodProcessor에서는 setter로 데이터 바인딩만 하기때문에
         * constructAttribute 사용 하지 않아서 resolveArgument에서 validation 한번에 다 실행함
         *
         */
    }

}