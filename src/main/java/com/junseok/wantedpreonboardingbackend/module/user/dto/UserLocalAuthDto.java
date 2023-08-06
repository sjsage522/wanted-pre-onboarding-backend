package com.junseok.wantedpreonboardingbackend.module.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLocalAuthDto {

    private String email;

    private String password;

    @Builder
    public UserLocalAuthDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
