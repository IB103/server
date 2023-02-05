package com.hansung.capstone.community;

import java.util.Optional;

public interface PostService {
    Post createPost(PostDTO.CreateRequestDTO req);

    Optional<Post> modifyPost(PostDTO.ModifyRequestDTO req);
}
