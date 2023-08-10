package com.junseok.wantedpreonboardingbackend.module.post.service;

import com.junseok.wantedpreonboardingbackend.global.aop.annotation.PostAuthCheck;
import com.junseok.wantedpreonboardingbackend.global.dto.PageResponseDto;
import com.junseok.wantedpreonboardingbackend.global.exception.EntityNotFoundException;
import com.junseok.wantedpreonboardingbackend.global.exception.dto.ErrorCode;
import com.junseok.wantedpreonboardingbackend.module.post.dao.PostRepository;
import com.junseok.wantedpreonboardingbackend.module.post.domain.Post;
import com.junseok.wantedpreonboardingbackend.module.post.dto.PostResponseDto;
import com.junseok.wantedpreonboardingbackend.module.user.dao.UserRepository;
import com.junseok.wantedpreonboardingbackend.module.user.domain.User;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createPost(String title, String content, Long userId) {
        final User findUser = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_USER));

        final Post createdPost = Post.builder()
                .title(title)
                .content(content)
                .user(findUser)
                .build();

        final Post savedPost = postRepository.save(createdPost);

        return savedPost.getId();
    }

    @Transactional(readOnly = true)
    public PageResponseDto<PostResponseDto> getPosts(Pageable pageable) {
        final Page<PostResponseDto> posts = postRepository.findAll(pageable).map(PostResponseDto::new);

        return new PageResponseDto<>(posts);
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long postId) {
        Post findPost = findPost(postId);

        return new PostResponseDto(findPost);
    }

    @PostAuthCheck
    @Transactional
    public Boolean updatePost(String title, String content, Long postId) {
        Post findPost = findPost(postId);
        findPost.updatePost(title, content);

        return Boolean.TRUE;
    }

    @PostAuthCheck
    @Transactional
    public Boolean deletePost(Long postId) {
        Post findPost = findPost(postId);
        postRepository.delete(findPost);

        return Boolean.TRUE;
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_POST));
    }
}
