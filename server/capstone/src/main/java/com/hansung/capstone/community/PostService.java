package com.hansung.capstone.community;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    PostDTO.PostResponseDTO createFreeBoardPost(PostDTO.CreateRequestDTO req, List<MultipartFile> files) throws Exception;

    Post createCourseBoardPost(CourseDTO.CreateRequestDTO req, List<MultipartFile> files) throws Exception;

    PostDTO.PostResponseDTO modifyPost(PostDTO.ModifyRequestDTO req, List<MultipartFile> files) throws Exception;

    Page<Post> getAllPost(int page);

    Page<Post> getBoardPost(int page, String board);


    Page<Post> getUserNickNamePost(String nickname, int page);

    Page<Post> getTitleOrContentPost(String titleOrContent, int page);

    Page<Post> getScrapPost(Long userId, int page);

    PostDTO.PostResponseDTO getDetailPost(Long id);

    PostDTO.PostResponseDTO setFavorite(Long userId, Long postId);

    PostDTO.PostResponseDTO setScrap(Long userId, Long postId);

    void deletePost(Long userId, Long postId) throws Exception;
}
