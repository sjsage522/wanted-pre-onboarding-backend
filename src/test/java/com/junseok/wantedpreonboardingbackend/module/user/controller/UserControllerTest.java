package com.junseok.wantedpreonboardingbackend.module.user.controller;

import com.junseok.wantedpreonboardingbackend.global.exception.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static com.junseok.wantedpreonboardingbackend.TestUtils.toJson;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("사용자 컨트롤러 테스트")
@SpringBootTest
class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @DisplayName("사용자 생성 성공 테스트")
    @Transactional
    @Test
    void createUser() throws Exception {
        // given

        // when
        ResultActions result = mockMvc.perform(
                post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                toJson(
                                        new HashMap<>() {
                                            {
                                                put("email", "test@gmail.com");
                                                put("password", "test-test-test-test");
                                            }
                                        }
                                )
                        )
        );

        result.andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("사용자 생성 실패 테스트 - 이메일에 @ 누락")
    @Transactional
    @Test
    void createUserFail1() throws Exception {
        // given

        // when
        ResultActions result = mockMvc.perform(
                post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                toJson(
                                        new HashMap<>() {
                                            {
                                                put("email", "test!gmail.com");
                                                put("password", "test-test-test-test");
                                            }
                                        }
                                )
                        )
        );

        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.error.code", is(20000)))
                .andExpect(jsonPath("$.error.message", is("Invalid sign up format, check email, password format.")));
    }

    @DisplayName("사용자 생성 실패 테스트 - 패스워드 8자 미만")
    @Transactional
    @Test
    void createUserFail2() throws Exception {
        // given

        // when
        ResultActions result = mockMvc.perform(
                post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                toJson(
                                        new HashMap<>() {
                                            {
                                                put("email", "test!gmail.com");
                                                put("password", "1234567");
                                            }
                                        }
                                )
                        )
        );

        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.error.code", is(20000)))
                .andExpect(jsonPath("$.error.message", is("Invalid sign up format, check email, password format.")));
    }
}