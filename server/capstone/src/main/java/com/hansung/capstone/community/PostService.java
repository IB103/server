package com.hansung.capstone.community;

import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PostService {
    PostDTO.PostResponseDTO createPost(PostDTO.CreateRequestDTO req);

    PostDTO.PostResponseDTO modifyPost(PostDTO.ModifyRequestDTO req);

    Page<Post> getAllPost(int page);

    PostDTO.PostResponseDTO getDetailPost(Long id);
}
