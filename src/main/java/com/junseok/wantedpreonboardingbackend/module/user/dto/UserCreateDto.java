package com.junseok.wantedpreonboardingbackend.module.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCreateDto {

    private String email;

    private String password;

    @Builder
    public UserCreateDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
