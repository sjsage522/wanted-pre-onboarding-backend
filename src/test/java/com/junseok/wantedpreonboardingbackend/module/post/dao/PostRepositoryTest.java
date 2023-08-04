package com.junseok.wantedpreonboardingbackend.module.post.dao;

import com.junseok.wantedpreonboardingbackend.module.post.domain.Post;
import com.junseok.wantedpreonboardingbackend.module.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private PostRepository postRepository;

    @Test
    public void 게시글_생성_성공() throws Exception {
        // given
        User user = this.makeUser("test@gmail.com", "test-password!!@@");
        this.entityManager.persist(user);

        Post post = this.makePost("post 1", "content lorem", user);
        this.entityManager.persist(post);

        // when
        Post findPost = this.postRepository.findById(1L).orElseThrow();

        // then
        assertThat(findPost.getTitle()).isEqualTo("post 1");
        assertThat(findPost.getContent()).isEqualTo("content lorem");
        assertThat(findPost.getUser().getEmail()).isEqualTo("test@gmail.com");
        assertThat(findPost.getUser().getPassword()).isNotBlank();
    }

    private User makeUser(String email, String password) {
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }

    private Post makePost(String title, String content, User user) {
        return Post.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}