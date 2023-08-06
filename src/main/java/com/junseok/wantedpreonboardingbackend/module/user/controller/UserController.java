package com.junseok.wantedpreonboardingbackend.module.user.controller;

import com.junseok.wantedpreonboardingbackend.global.dto.ApiResult;
import com.junseok.wantedpreonboardingbackend.module.user.dto.UserCreateDto;
import com.junseok.wantedpreonboardingbackend.module.user.dto.UserLocalAuthDto;
import com.junseok.wantedpreonboardingbackend.module.user.dto.UserTokenResponseDto;
import com.junseok.wantedpreonboardingbackend.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class UserController {

    private final UserService userService;

    /**
     * 사용자 회원가입
     */
    @PostMapping("/users")
    public ResponseEntity<ApiResult<Long>> createUser(@RequestBody UserCreateDto userCreateDto) {
        final Long userId = userService.createUser(userCreateDto.getEmail(), userCreateDto.getPassword());

        return new ResponseEntity<>(
            ApiResult.succeed(userId),
            HttpStatus.CREATED
        );
    }

    /**
     * 사용자 로그인
     */
    @PostMapping("/users/auth")
    public ResponseEntity<ApiResult<UserTokenResponseDto>> auth(@RequestBody UserLocalAuthDto userLocalAuthDto) {
        final UserTokenResponseDto userTokenResponseDto = userService.auth(userLocalAuthDto.getEmail(), userLocalAuthDto.getPassword());

        return new ResponseEntity<>(
            ApiResult.succeed(userTokenResponseDto),
            HttpStatus.CREATED
        );
    }
 }
