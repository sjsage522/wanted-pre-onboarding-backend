package com.junseok.wantedpreonboardingbackend.module.user.service;

import com.junseok.wantedpreonboardingbackend.module.user.dao.UserRepository;
import com.junseok.wantedpreonboardingbackend.module.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("사용자 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

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
}