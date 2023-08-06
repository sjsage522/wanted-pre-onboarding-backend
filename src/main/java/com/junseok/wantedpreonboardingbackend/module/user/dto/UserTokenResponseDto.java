package com.junseok.wantedpreonboardingbackend.module.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTokenResponseDto {

    @JsonProperty(value = "access_token")
    private String jwt;

    @Builder
    public UserTokenResponseDto(String jwt) {
        this.jwt = jwt;
    }
}
