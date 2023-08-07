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
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.Ordered;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    @DisplayName("게시글 조회 테스트 - 리스트 조회")
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.count", is(10)))
                .andExpect(jsonPath("$.data.page", is(page)))
                .andExpect(jsonPath("$.data.size", is(10)))
                .andExpect(jsonPath("$.data.total", is(totalSize)));
    }

    @DisplayName("게시글 조회 테스트 - 단건 조회")
    @Transactional
    @Test
    void getPost() throws Exception {
        // given
        int totalSize = 1;
        for (int i = 0; i < totalSize; i++) {
            Post post = makePost("title " + i, "content " + i, this.user);
            postRepository.save(post);
        }

        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
        );

        // then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title", is("title 0")))
                .andExpect(jsonPath("$.data.content", is("content 0")))
                .andExpect(jsonPath("$.data.post_id", is(1)));
    }

    @DisplayName("게시글 조회 실패 테스트 - 단건 조회(게시글이 존재하지 않음)")
    @Transactional
    @Test
    void getPostFail1() throws Exception {
        // given

        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
        );

        // then
        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.error.code", is(30001)))
                .andExpect(jsonPath("$.error.message", is("Not found post.")));
    }

    @Order(Ordered.LOWEST_PRECEDENCE)
    @DisplayName("게시글 수정 테스트")
    @Transactional
    @Test
    void updatePost() throws Exception {
        // given
        int totalSize = 1;
        long savedId = 1L;
        for (int i = 0; i < totalSize; i++) {
            Post post = makePost("title " + i, "content " + i, this.user);
            Post savedPost = postRepository.save(post);
            savedId = savedPost.getId();
        }

        // when
        ResultActions result = mockMvc.perform(
                patch("/api/v1/posts/" + savedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt)
                        .content(
                                toJson(
                                        new HashMap<>() {
                                            {
                                                put("title", "title update");
                                                put("content", "content update");
                                            }
                                        }
                                )
                        )
        );

        // then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is(true)));
    }
}