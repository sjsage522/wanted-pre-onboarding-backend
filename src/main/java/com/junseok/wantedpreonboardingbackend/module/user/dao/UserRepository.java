package com.junseok.wantedpreonboardingbackend.module.user.dao;

import com.junseok.wantedpreonboardingbackend.module.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
