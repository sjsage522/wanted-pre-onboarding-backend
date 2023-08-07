package com.junseok.wantedpreonboardingbackend.module.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.junseok.wantedpreonboardingbackend.module.post.domain.Post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponseDto {
    
    @JsonProperty(value = "post_id")
    private Long id;

    private String title;

    private String content;

    @JsonProperty(value = "writer")
    private String email;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.email = post.getUser().getEmail();
    }
}
