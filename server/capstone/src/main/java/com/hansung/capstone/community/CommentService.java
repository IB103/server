package com.hansung.capstone.community;

import javax.naming.AuthenticationException;
import java.util.Optional;

public interface CommentService {

    PostDTO.PostResponseDTO createComment(CommentDTO.CreateRequestDTO req);

    PostDTO.PostResponseDTO setFavorite(Long userId, Long postId, Long commentId);

    PostDTO.PostResponseDTO modifyComment(Long id, String content);

    void deleteComment(Long userId, Long commentId) throws Exception;
}
