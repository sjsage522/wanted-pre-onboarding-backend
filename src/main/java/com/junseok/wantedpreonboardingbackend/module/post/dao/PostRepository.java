package com.junseok.wantedpreonboardingbackend.module.post.dao;

import com.junseok.wantedpreonboardingbackend.module.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = {"user"})
    @Override
    Page<Post> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    @Override
    Optional<Post> findById(Long id);
}
