package com.hansung.capstone.community;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/community/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public Post createPost(@RequestBody PostDTO.CreateRequestDTO req){
        return this.postService.createPost(req);
    }

    @PutMapping("/modify")
    public Optional<Post> modifyPost(@RequestBody PostDTO.ModifyRequestDTO req){
        return this.postService.modifyPost(req);
    }
}
