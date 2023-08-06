package com.junseok.wantedpreonboardingbackend.module.user.service;

import com.junseok.wantedpreonboardingbackend.global.exception.EntityNotFoundException;
import com.junseok.wantedpreonboardingbackend.global.exception.dto.ErrorCode;
import com.junseok.wantedpreonboardingbackend.global.util.JwtProvider;
import com.junseok.wantedpreonboardingbackend.module.user.dao.UserRepository;
import com.junseok.wantedpreonboardingbackend.module.user.domain.User;
import com.junseok.wantedpreonboardingbackend.module.user.dto.UserTokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    @Transactional
    public Long createUser(String email, String password) {
        final User createUser = User
                .builder()
                .email(email)
                .password(password)
                .build();

        final User savedUser = userRepository.save(createUser);

        return savedUser.getId();
    }

    public UserTokenResponseDto auth(String email, String password) {
        User.validationSignUpFormat(email, password);

        final User findUser = findUser(email);
        findUser.validationMatchPassword(findUser.hashPassword(password, findUser.getSalt()));

        final String jwt = jwtProvider.createJwt(String.valueOf(findUser.getId()), findUser.getEmail());

        return UserTokenResponseDto.builder()
                .jwt(jwt)
                .build();
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.NOT_FOUND_USER)
        );
    }
}
