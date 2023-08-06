package com.junseok.wantedpreonboardingbackend.module.user.service;

import com.junseok.wantedpreonboardingbackend.module.user.dao.UserRepository;
import com.junseok.wantedpreonboardingbackend.module.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Long createUser(String email, String password) {
        final User createUser = User
                .builder()
                .email(email)
                .password(password)
                .build();

        final User savedUser = userRepository.save(createUser);

        return savedUser.getId();
    }
}
