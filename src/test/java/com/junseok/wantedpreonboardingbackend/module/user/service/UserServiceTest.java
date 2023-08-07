package com.junseok.wantedpreonboardingbackend.module.user.service;

import com.junseok.wantedpreonboardingbackend.global.exception.CustomException;
import com.junseok.wantedpreonboardingbackend.global.util.JwtProvider;
import com.junseok.wantedpreonboardingbackend.module.user.dao.UserRepository;
import com.junseok.wantedpreonboardingbackend.module.user.domain.User;
import com.junseok.wantedpreonboardingbackend.module.user.dto.UserTokenResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("사용자 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtProvider jwtProvider;

    @DisplayName("사용자 생성 테스트")
    @Test
    void createUser() {
        // given
        User user = mock(User.class);
        given(userRepository.save(any()))
                .willReturn(user);

        // when
        Long userId = userService.createUser("test@gmail.com", "test-test-test-test");

        // then
        assertThat(userId).isEqualTo(0L);
    }

    @DisplayName("사용자 생성 실패 테스트 - 이미 존재하는 이메일")
    @Test
    void createUserFail1() {
        // given
        given(userRepository.existsByEmail(any()))
                .willReturn(true);

        // when

        // then
        Assertions.assertThrows(CustomException.class, () -> {
            userService.createUser("test@gmail.com", "test-test-test-test");
        });
    }

    @DisplayName("사용자 로그인 테스트")
    @Test
    void authUser() {
        // given
        User user = mock(User.class);
        given(userRepository.findByEmail(any()))
                .willReturn(Optional.of(user));
        given(jwtProvider.createJwt(anyString(), any()))
                .willReturn(anyString());

        // when
        UserTokenResponseDto userTokenResponseDto = userService.auth("test@gmail.com", "test-test-test-test");

        // then
        assertThat(userTokenResponseDto.getJwt()).isNotNull();
    }
}