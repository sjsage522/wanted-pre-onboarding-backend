package com.junseok.wantedpreonboardingbackend.global.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("jwt.config")
public class JwtProperty {

    private final Long expiredTime;
}
