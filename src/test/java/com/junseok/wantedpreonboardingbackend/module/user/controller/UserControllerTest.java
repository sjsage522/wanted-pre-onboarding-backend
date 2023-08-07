package com.junseok.wantedpreonboardingbackend.module.user.controller;

import com.junseok.wantedpreonboardingbackend.global.exception.handler.GlobalExceptionHandler;
import com.junseok.wantedpreonboardingbackend.module.user.dao.UserRepository;
import com.junseok.wantedpreonboardingbackend.module.user.domain.User;
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

import static com.junseok.wantedpreonboardingbackend.TestUtils.makeUser;
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

    @Autowired
    private UserRepository userRepository;

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

        // then
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

        // then
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

        // then
        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.error.code", is(20000)))
                .andExpect(jsonPath("$.error.message", is("Invalid sign up format, check email, password format.")));
    }

    @DisplayName("사용자 로그인 성공 테스트")
    @Test
    void authUser() throws Exception {
        // given
        String email = "test@gmail.com";
        String password = "12345678";
        User user = makeUser(email, password);
        userRepository.save(user);

        // when
        ResultActions result = mockMvc.perform(
                post("/api/v1/users/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                toJson(
                                        new HashMap<>() {
                                            {
                                                put("email", email);
                                                put("password",password);
                                            }
                                        }
                                )
                        )
        );
        userRepository.deleteAll();

        // then
        result.andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("사용자 로그인 실패 테스트 - 이메일에 @ 누락")
    @Test
    void authUserFail1() throws Exception {
        // given
        String email = "test!gmail.com";
        String password = "12345678";

        // when
        ResultActions result = mockMvc.perform(
                post("/api/v1/users/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                toJson(
                                        new HashMap<>() {
                                            {
                                                put("email", email);
                                                put("password",password);
                                            }
                                        }
                                )
                        )
        );

        // then
        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.error.code", is(20000)))
                .andExpect(jsonPath("$.error.message", is("Invalid sign up format, check email, password format.")));
    }

    @DisplayName("사용자 로그인 실패 테스트 - 패스워드 8자 미만")
    @Test
    void authUserFail2() throws Exception {
        // given
        String email = "test@gmail.com";
        String password = "1234567";

        // when
        ResultActions result = mockMvc.perform(
                post("/api/v1/users/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                toJson(
                                        new HashMap<>() {
                                            {
                                                put("email", email);
                                                put("password",password);
                                            }
                                        }
                                )
                        )
        );

        // then
        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.error.code", is(20000)))
                .andExpect(jsonPath("$.error.message", is("Invalid sign up format, check email, password format.")));
    }

    @DisplayName("사용자 로그인 실패 테스트 - 비밀번호 불일치")
    @Test
    void authUserFail3() throws Exception {
        // given
        String email = "test@gmail.com";
        String password = "12345678";
        User user = makeUser(email, password);
        userRepository.save(user);

        // when
        ResultActions result = mockMvc.perform(
                post("/api/v1/users/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                toJson(
                                        new HashMap<>() {
                                            {
                                                put("email", email);
                                                put("password","12345678!!");
                                            }
                                        }
                                )
                        )
        );
        userRepository.deleteAll();

        // then
        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.error.code", is(40000)))
                .andExpect(jsonPath("$.error.message", is("Mismatch user password.")));
    }

    @DisplayName("사용자 로그인 실패 테스트 - 존재하지 않는 이메일")
    @Test
    void authUserFail4() throws Exception {
        // given
        String email = "test@gmail.com";
        String password = "12345678";
        User user = makeUser(email, password);
        userRepository.save(user);

        // when
        ResultActions result = mockMvc.perform(
                post("/api/v1/users/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                toJson(
                                        new HashMap<>() {
                                            {
                                                put("email", "not@gmail.com");
                                                put("password",password);
                                            }
                                        }
                                )
                        )
        );
        userRepository.deleteAll();

        // then
        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.error.code", is(30000)))
                .andExpect(jsonPath("$.error.message", is("Not found user.")));
    }
}