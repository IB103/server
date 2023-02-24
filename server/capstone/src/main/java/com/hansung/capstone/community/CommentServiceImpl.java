package com.hansung.capstone.community;

import com.hansung.capstone.user.User;
import com.hansung.capstone.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .createdDate(LocalDateTime.now())
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

    @Override
    @Transactional
    public PostDTO.PostResponseDTO setFavorite(Long userId, Long postId, Long commentId) {
        Post post = this.postRepository.findById(postId).orElseThrow( () ->
                new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        User user = this.userRepository.findById(userId).orElseThrow( () ->
                new IllegalArgumentException("유저가 존재하지 않습니다.")
        );
        Comment comment = this.commentRepository.findById(commentId).orElseThrow( () ->
                new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if(comment.getVoter().contains(user)){
            comment.getVoter().remove(user);
        }else{
            comment.getVoter().add(user);
        }
        return this.postService.createResponse(post);
    }
}
