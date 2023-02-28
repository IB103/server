package com.hansung.capstone.community;

import com.hansung.capstone.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(Long id);

    Optional<Post> findByTitle(String title);

    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByAuthor(User user, Pageable pageable);

    @Query(
            value = "SELECT p FROM Post p Where p.title LIKE %:titleOrContent% OR p.content LIKE %:titleOrContent%"
    )
    Page<Post> findAllSearch(@Param("titleOrContent")String titleOrContent, Pageable pageable);

    void deleteById(Long id);
}
