package com.hansung.capstone.community;

public interface ReCommentService {

    PostDTO.PostResponseDTO createReComment(ReCommentDTO.CreateRequestDTO req);

    PostDTO.PostResponseDTO setFavorite(Long userId, Long postId,Long reCommentId);

    void deleteReComment(Long userId, Long reCommentId) throws Exception;
}
