package com.junseok.wantedpreonboardingbackend.module.user.dao;

import com.junseok.wantedpreonboardingbackend.module.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
