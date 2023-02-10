package com.hansung.capstone.community;

import java.util.Optional;

public interface CommentService {

    PostDTO.PostResponseDTO createComment(CommentDTO.CreateRequestDTO req);

    void modifyComment(Long id, String content);
}
