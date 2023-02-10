package com.hansung.capstone.community;

import com.hansung.capstone.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;


    @Override
    public PostDTO.PostResponseDTO createPost(PostDTO.CreateRequestDTO req) {
        Post newPost = Post.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .createdDate(LocalDateTime.now())
                .author(this.userRepository.findById(req.getUserId()).get()).build();
        return createResponse(this.postRepository.save(newPost));
    }

    @Transactional
    @Override
    public PostDTO.PostResponseDTO modifyPost(PostDTO.ModifyRequestDTO req) {
        Optional<Post> modifyPost = this.postRepository.findById(req.getId());
        modifyPost.ifPresent( s -> {
            modifyPost.get().modify(req.getTitle(), req.getContent(), LocalDateTime.now());
                }
        );
        return createResponse(this.postRepository.findById(req.getId()).get());
    }

    @Override
    public Page<Post> getAllPost(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));
        return this.postRepository.findAll(pageable);
    }

    @Override
    public PostDTO.PostResponseDTO getDetailPost(Long id) {
        return createResponse(this.postRepository.findById(id).get());
    }

    public PostDTO.PostResponseDTO createResponse(Post req){
        List<CommentDTO.ResponseDTO> comments = new ArrayList<>();
        if (req.getCommentList() != null) {
            for (int i = 0; i < req.getCommentList().size(); i++) {
                Comment comment = req.getCommentList().get(i);
                CommentDTO.ResponseDTO commentRes = CommentDTO.ResponseDTO.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .createdDate(comment.getCreateDate())
                        .modifiedDate(comment.getModifiedDate())
                        .userId(comment.getAuthor().getId())
                        .userNickname(comment.getAuthor().getNickname()).build();
                comments.add(commentRes);
            }
        }


        PostDTO.PostResponseDTO res = PostDTO.PostResponseDTO.builder()
                .id(req.getId())
                .title(req.getTitle())
                .content(req.getContent())
                .createdDate(req.getCreatedDate())
                .modifiedDate(req.getModifiedDate())
                .authorId(req.getAuthor().getId())
                .nickname(req.getAuthor().getNickname())
                .commentList(comments).build();
        return res;
    }
}
