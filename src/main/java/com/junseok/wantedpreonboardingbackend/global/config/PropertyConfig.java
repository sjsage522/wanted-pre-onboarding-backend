package com.junseok.wantedpreonboardingbackend.global.config;

import com.junseok.wantedpreonboardingbackend.global.property.JwtProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {
        JwtProperty.class
})
public class PropertyConfig {
}
