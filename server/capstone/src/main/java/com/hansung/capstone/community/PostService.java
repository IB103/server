package com.hansung.capstone.community;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PostService {
    PostDTO.PostResponseDTO createPost(PostDTO.CreateRequestDTO req, List<MultipartFile> files) throws Exception;

    PostDTO.PostResponseDTO modifyPost(PostDTO.ModifyRequestDTO req, List<MultipartFile> files) throws Exception;

    Page<Post> getAllPost(int page);

    Page<Post> getUserNickNamePost(String nickname, int page);

    PostDTO.PostResponseDTO getDetailPost(Long id);
}
