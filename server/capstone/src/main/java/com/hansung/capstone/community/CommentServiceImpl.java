package com.hansung.capstone.community;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;
    @Override
    public void createComment(Post post, String content) {
        Comment comment = Comment.builder()
                .content(content)
                .createDate(LocalDateTime.now())
                .post(post).build();
        this.commentRepository.save(comment);
    }
}
