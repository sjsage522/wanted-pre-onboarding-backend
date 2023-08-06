package com.junseok.wantedpreonboardingbackend.module.post.controller;

import com.junseok.wantedpreonboardingbackend.global.dto.ApiResult;
import com.junseok.wantedpreonboardingbackend.global.util.HttpServletUtils;
import com.junseok.wantedpreonboardingbackend.module.post.dto.PostCreateDto;
import com.junseok.wantedpreonboardingbackend.module.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class PostController {

    private final PostService postService;

    private final HttpServletUtils httpServletUtils;

    /**
     * 게시글 생성
     */
    @PostMapping("/posts")
    public ResponseEntity<ApiResult<Long>> createPost(@RequestBody PostCreateDto postCreateDto) {
        Long userId = httpServletUtils.getUserIdFromServletRequest();
        final Long postId = postService.createPost(postCreateDto.getTitle(), postCreateDto.getContent(), userId);

        return new ResponseEntity<>(
            ApiResult.succeed(postId),
            HttpStatus.CREATED
        );
    }
}
