package com.hansung.capstone.community;

import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PostService {
    Post createPost(PostDTO.CreateRequestDTO req);

    Optional<Post> modifyPost(PostDTO.ModifyRequestDTO req);

    Page<Post> getAllPost(int page);
}
