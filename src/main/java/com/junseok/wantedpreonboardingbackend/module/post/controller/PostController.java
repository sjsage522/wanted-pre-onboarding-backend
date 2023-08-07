package com.junseok.wantedpreonboardingbackend.module.post.controller;

import com.junseok.wantedpreonboardingbackend.global.dto.ApiResult;
import com.junseok.wantedpreonboardingbackend.global.dto.PageResponseDto;
import com.junseok.wantedpreonboardingbackend.global.util.HttpServletUtils;
import com.junseok.wantedpreonboardingbackend.module.post.dto.PostCreateDto;
import com.junseok.wantedpreonboardingbackend.module.post.dto.PostResponseDto;
import com.junseok.wantedpreonboardingbackend.module.post.dto.PostUpdateDto;
import com.junseok.wantedpreonboardingbackend.module.post.service.PostService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 게시글 리스트 조회
     */
    @GetMapping("/posts")
    public ResponseEntity<ApiResult<PageResponseDto<PostResponseDto>>> getPosts(
        @RequestParam(required = false, defaultValue = "0", value = "page") int page
    ) {
        Pageable pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "id");
        PageResponseDto<PostResponseDto> postResponseDtos = postService.getPosts(pageable);

        return new ResponseEntity<>(
            ApiResult.succeed(postResponseDtos),
            HttpStatus.OK
        );
    }

    /**
     * 게시글 단건 조회
     */
    @GetMapping("/posts/{postId}")
    public ResponseEntity<ApiResult<PostResponseDto>> getPost(@PathVariable Long postId) {
        PostResponseDto postResponseDto = postService.getPost(postId);

        return new ResponseEntity<>(
            ApiResult.succeed(postResponseDto),
            HttpStatus.OK
        );
    }

    /**
     * 게시글 수정
     */
    @PatchMapping("/posts/{postId}")
    public ResponseEntity<ApiResult<Boolean>> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateDto postUpdateDto) {
        Long userId = httpServletUtils.getUserIdFromServletRequest();
        Boolean isUpdated = postService.updatePost(postUpdateDto.getTitle(), postUpdateDto.getContent(), postId, userId);

        return new ResponseEntity<>(
            ApiResult.succeed(isUpdated),
            HttpStatus.OK
        );
    }
}
