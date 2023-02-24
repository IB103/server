package com.hansung.capstone.community;

import java.util.Optional;

public interface CommentService {

    PostDTO.PostResponseDTO createComment(CommentDTO.CreateRequestDTO req);

    PostDTO.PostResponseDTO setFavorite(Long userId, Long postId, Long commentId);

    void modifyComment(Long id, String content);
}
