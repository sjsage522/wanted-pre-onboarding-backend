package com.junseok.wantedpreonboardingbackend.module.post.dao;

import com.junseok.wantedpreonboardingbackend.module.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
