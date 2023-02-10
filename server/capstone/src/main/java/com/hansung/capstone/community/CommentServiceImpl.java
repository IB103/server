package com.hansung.capstone.community;

import com.hansung.capstone.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final PostServiceImpl postService;

    @Override
    public PostDTO.PostResponseDTO createComment(CommentDTO.CreateRequestDTO req) {
        Comment comment = Comment.builder()
                .content(req.getContent())
                .createDate(LocalDateTime.now())
                .post(this.postRepository.findById(req.getPostId()).get())
                .author(this.userRepository.findById(req.getUserId()).get()).build();
        this.commentRepository.save(comment);
        Post post = this.postRepository.findById(req.getPostId()).get();
        return this.postService.createResponse(post);
    }

    @Override
    public void modifyComment(Long id, String content) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        comment.ifPresent(s->{
            comment.get().modify(content, LocalDateTime.now());
                }
        );
    }

}
