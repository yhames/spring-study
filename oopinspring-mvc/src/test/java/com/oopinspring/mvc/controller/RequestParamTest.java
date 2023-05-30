package com.oopinspring.mvc.controller;

import com.oopinspring.mvc.domain.BoardVO;
import com.oopinspring.mvc.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class RequestParamTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("edit - @RequestParam")
    void editRequestParam() throws Exception {
        int seq = 1;
        BoardVO boardVO = boardService.read(seq);
        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(post("/board/edit/" + seq)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .characterEncoding("UTF-8")
                        .session(session)
                        .sessionAttr("boardVO", boardVO)
                        .param("title", "t2")
                        .param("content", "c2")
                        .param("writer", "w2")
                        .param("pwd", "asdf"))  // MethodArgumentTypeMismatchException
                .andExpect(status().isBadRequest()) // 400
                .andExpect(result -> Assertions.assertThat(result.getResolvedException())
                        .isInstanceOf(MethodArgumentTypeMismatchException.class))
                .andDo(print());

        /**
         * InvocableHandlerMethod.getMethodArgumentValues()에서 파라미터 순회하면서 resolveArgument 호출
         * AbstractNamedValueMethodArgumentResolver.resolveArgument()에서 resolveName 호출
         * RequestParamMethodArgumentResolver.resolveName()에서 pwd에 대한 arg("asdf") 반환
         * AbstractNamedValueMethodArgumentResolver.resolveArgument()에서 DataBinder.convertIfNecessary() 호출하고
         * DataBinder.convertIfNecessary()는 TypeConverter.convertIfNecessary()를 호출하여 문자열을 숫자로 변환
         * type conversion 실패시 TypeMismatchException() 발생하고
         * AbstractNamedValueMethodArgumentResolver.resolveArgument() MethodArgumentTypeMismatchException() 던짐
         */
    }

}