package com.hansung.capstone.community;

import java.util.Optional;

public interface CommentService {

    void createComment(Post post, String content);
}
