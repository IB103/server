package com.hansung.capstone.community;

import com.hansung.capstone.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;


    @Override
    public Post createPost(PostDTO.CreateRequestDTO req) {
        Post newPost = Post.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .createdDate(LocalDateTime.now()).build();
        return this.postRepository.save(newPost);
    }

    @Override
    public Optional<Post> modifyPost(PostDTO.ModifyRequestDTO req) {
        Optional<Post> modifyPost = this.postRepository.findById(req.getId());
        modifyPost.ifPresent(selectPost -> {
                    selectPost.setTitle(req.getTitle());
                    selectPost.setContent(req.getContent());
                    selectPost.setModifiedDate(LocalDateTime.now());
                    this.postRepository.save(selectPost);
                }
        );
        Optional<Post> modifiedPost = this.postRepository.findById(req.getId());
        return modifiedPost;
    }
}