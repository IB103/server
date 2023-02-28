package com.hansung.capstone.community;

import com.hansung.capstone.user.User;
import com.hansung.capstone.user.UserDetailsImpl;
import com.hansung.capstone.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReCommentServiceImpl implements ReCommentService{

    private final CommentRepository commentRepository;

    private final ReCommentRepository reCommentRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final PostServiceImpl postService;
    @Override
    public PostDTO.PostResponseDTO createReComment(ReCommentDTO.CreateRequestDTO req) {
        ReComment reComment = ReComment.builder()
                .content(req.getContent())
                .createdDate(LocalDateTime.now())
                .comment(this.commentRepository.findById(req.getCommentId()).orElseThrow( () ->
                        new IllegalArgumentException("댓글이 존재하지 않습니다.")
                ))
                .author(this.userRepository.findById(req.getUserId()).orElseThrow( () ->
                        new IllegalArgumentException("유저가 존재하지 않습니다.")
                )).build();
        this.reCommentRepository.save(reComment);
        Post post = this.postRepository.findById(req.getPostId()).orElseThrow( () ->
                new IllegalArgumentException("게시글이 존재하지 않습니다.")
                );
        return this.postService.createResponse(post);
    }

    @Override
    @Transactional
    public PostDTO.PostResponseDTO setFavorite(Long userId, Long postId,Long reCommentId) {
        Post post = this.postRepository.findById(postId).orElseThrow( () ->
                new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        User user = this.userRepository.findById(userId).orElseThrow( () ->
                new IllegalArgumentException("유저가 존재하지 않습니다.")
        );
        ReComment reComment = this.reCommentRepository.findById(reCommentId).orElseThrow( () ->
                new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if(reComment.getVoter().contains(user)){
            reComment.getVoter().remove(user);
        }else{
            reComment.getVoter().add(user);
        }
        return this.postService.createResponse(post);
    }

    @Override
    public void deleteReComment(Long userId, Long reCommentId) throws Exception {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long reCommentAuthorId = this.reCommentRepository.findById(reCommentId).orElseThrow( () ->
                new IllegalArgumentException("댓글이 존재하지 않습니다.")
        ).getAuthor().getId();
        if(userDetails.getUserId().equals(userId) && reCommentId.equals(reCommentAuthorId)){
            this.commentRepository.deleteById(reCommentId);
        }else{
            throw new AuthenticationException();
        }
    }
}
