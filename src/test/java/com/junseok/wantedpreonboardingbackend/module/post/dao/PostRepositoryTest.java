package com.junseok.wantedpreonboardingbackend.module.post.dao;

import com.junseok.wantedpreonboardingbackend.module.post.domain.Post;
import com.junseok.wantedpreonboardingbackend.module.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.junseok.wantedpreonboardingbackend.TestUtils.makePost;
import static com.junseok.wantedpreonboardingbackend.TestUtils.makeUser;
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

    @DisplayName("게시글 조회 테스트")
    @Test
    public void getPosts() {
        int minSize = 2;

        // given
        int totalSize = 10;
        int size = 5;
        assert size > minSize;
        int pageNumber = 0;

        User user = makeUser("test@gmail.com", "test-password!!@@");
        this.entityManager.persist(user);

        for (int i = 0; i < totalSize; i++) {
            Post post = makePost("title " + i, "content " + i, user);
            entityManager.persist(post);
        }

        Pageable pageable = PageRequest.of(pageNumber, size, Sort.Direction.DESC, "id");

        // when
        Page<Post> page = this.postRepository.findAll(pageable);

        // then
        List<Post> posts = page.getContent();
        assertThat(posts.size()).isEqualTo(size);
        assertThat(posts.get(0).getTitle()).isEqualTo("title " + (totalSize - 1));
        assertThat(posts.get(0).getContent()).isEqualTo("content " + (totalSize - 1));
        assertThat(posts.get(1).getTitle()).isEqualTo("title " + (totalSize - 2));
        assertThat(posts.get(1).getContent()).isEqualTo("content " + (totalSize - 2));
    }
}