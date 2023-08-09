package com.junseok.wantedpreonboardingbackend.global.aop;

import com.junseok.wantedpreonboardingbackend.global.exception.CustomException;
import com.junseok.wantedpreonboardingbackend.global.exception.EntityNotFoundException;
import com.junseok.wantedpreonboardingbackend.global.exception.dto.ErrorCode;
import com.junseok.wantedpreonboardingbackend.global.util.HttpServletUtils;
import com.junseok.wantedpreonboardingbackend.module.post.dao.PostRepository;
import com.junseok.wantedpreonboardingbackend.module.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Aspect
@Component
public class ResourceAuthCheckAspect {

    private final PostRepository postRepository;

    private final HttpServletUtils httpServletUtils;

    @Before(value = "@annotation(com.junseok.wantedpreonboardingbackend.global.aop.annotation.PostAuthCheck) && args(.., postId)", argNames = "joinPoint, postId")
    public Object resourceAuthCheck(JoinPoint joinPoint, Long postId) {
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_POST));
        Long userId = httpServletUtils.getUserIdFromServletRequest();

        if (!findPost.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.DENIED_ACCESS_POST);
        }

        return joinPoint;
    }
}
