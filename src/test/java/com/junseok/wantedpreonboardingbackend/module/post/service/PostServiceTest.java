package com.junseok.wantedpreonboardingbackend.module.post.service;

import com.junseok.wantedpreonboardingbackend.module.post.domain.PostRepository;
import com.junseok.wantedpreonboardingbackend.module.post.domain.Post;
import com.junseok.wantedpreonboardingbackend.module.user.domain.UserRepository;
import com.junseok.wantedpreonboardingbackend.module.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("게시글 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @DisplayName("게시글 생성 테스트")
    @Test
    public void createPost() {
        // given
        User user = mock(User.class);
        given(userRepository.findById(anyLong()))
                .willReturn(Optional.of(
                    user
                ));

        Post post = mock(Post.class);
        given(postRepository.save(any()))
                .willReturn(post);

        // when
        Long postId = postService.createPost(post.getTitle(), post.getContent(), user.getId());

        // then
        assertThat(postId).isEqualTo(0L);
    }
}