package com.junseok.wantedpreonboardingbackend.module.post.dao;

import com.junseok.wantedpreonboardingbackend.module.post.domain.Post;
import com.junseok.wantedpreonboardingbackend.module.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static com.junseok.wantedpreonboardingbackend.module.post.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("게시글 레포지토리 테스트")
@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private PostRepository postRepository;

    @DisplayName("게시글 생성 테스트")
    @Test
    public void createPostTest() throws Exception {
        // given
        User user = makeUser("test@gmail.com", "test-password!!@@");
        this.entityManager.persist(user);

        Post post = makePost("post 1", "content lorem", user);
        this.entityManager.persist(post);

        // when
        Post findPost = this.postRepository.findById(1L).orElseThrow();

        // then
        assertThat(findPost.getTitle()).isEqualTo("post 1");
        assertThat(findPost.getContent()).isEqualTo("content lorem");
        assertThat(findPost.getUser().getEmail()).isEqualTo("test@gmail.com");
        assertThat(findPost.getUser().getPassword()).isNotBlank();
    }
}