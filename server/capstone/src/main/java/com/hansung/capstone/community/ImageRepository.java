package com.hansung.capstone.community;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<ImageIdInterface> findByPostId(Long postId);
}
