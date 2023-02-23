package com.hansung.capstone.community;

import com.hansung.capstone.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(Long id);

    Optional<Post> findByTitle(String title);

    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByAuthor(User user, Pageable pageable);

    Page<Post> findAllByContentOrTitle(String req, Pageable pageable);
}
