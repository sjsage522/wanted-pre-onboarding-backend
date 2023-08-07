package com.junseok.wantedpreonboardingbackend.module.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateDto {
    private String title;

    private String content;

    @Builder
    public PostUpdateDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
