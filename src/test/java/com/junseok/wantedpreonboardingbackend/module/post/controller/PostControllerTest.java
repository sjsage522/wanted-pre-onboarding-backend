package com.junseok.wantedpreonboardingbackend.module.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junseok.wantedpreonboardingbackend.global.exception.handler.ExceptionHandlerFilter;
import com.junseok.wantedpreonboardingbackend.global.exception.handler.GlobalExceptionHandler;
import com.junseok.wantedpreonboardingbackend.global.filter.AuthFilter;
import com.junseok.wantedpreonboardingbackend.global.util.JwtProvider;
import com.junseok.wantedpreonboardingbackend.module.post.dao.PostRepository;
import com.junseok.wantedpreonboardingbackend.module.post.domain.Post;
import com.junseok.wantedpreonboardingbackend.module.user.dao.UserRepository;
import com.junseok.wantedpreonboardingbackend.module.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static com.junseok.wantedpreonboardingbackend.TestUtils.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("게시글 컨트롤러 테스트")
@SpringBootTest
class PostControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private PostController postController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private JwtProvider jwtProvider;

    private User user;

    private String jwt;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilters(new ExceptionHandlerFilter(new ObjectMapper()), new AuthFilter(jwtProvider))
                .build();
    }

    @BeforeTransaction
    void singUp() {
        user = makeUser("test1@gmail.com", "test-test-test-test");
        User savedUser = userRepository.save(user);
        jwt = jwtProvider.createJwt(String.valueOf(savedUser.getId()), savedUser.getEmail());
    }

    @AfterTransaction
    void cleanUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @DisplayName("게시글 생성 테스트")
    @Transactional
    @Test
    void createPost() throws Exception {
        // given

        // when
        ResultActions result = mockMvc.perform(
                post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
                        .content(
                                toJson(
                                        new HashMap<>() {
                                            {
                                                put("title", "title 1");
                                                put("content", "content 1");
                                            }
                                        }
                                )
                        )
        );

        // then
        result.andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("게시글 조회 테스트")
    @Transactional
    @Test
    void getPosts() throws Exception {
        // given
        int totalSize = 15;
        int page = 0;
        for (int i = 0; i < totalSize; i++) {
            Post post = makePost("title " + i, "content " + i, this.user);
            postRepository.save(post);
        }

        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
                        .param("page", String.valueOf(page))
        );

        // then
        result.andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.data.count", is(10)))
                .andExpect(jsonPath("$.data.page", is(page)))
                .andExpect(jsonPath("$.data.size", is(10)))
                .andExpect(jsonPath("$.data.total", is(totalSize)));
    }
}