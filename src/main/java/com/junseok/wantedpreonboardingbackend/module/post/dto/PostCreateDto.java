package com.junseok.wantedpreonboardingbackend.module.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateDto {

    private String title;

    private String content;

    @Builder
    public PostCreateDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
