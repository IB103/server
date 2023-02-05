package com.hansung.capstone.community;

import com.hansung.capstone.user.User;
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

    @Override
    public Page<Post> getAllPost(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));
        return this.postRepository.findAll(pageable);
    }
}
