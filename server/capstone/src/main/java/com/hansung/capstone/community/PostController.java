package com.hansung.capstone.community;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/list")
    public List<Post> getAllPost(@RequestParam(defaultValue = "0") int page) {
        Page<Post> paging = this.postService.getAllPost(page);
        return paging.getContent();
    }

    @GetMapping("/detail")
    public Optional<Post> getDetailPost(@RequestParam Long id){
        return this.postService.getDetailPost(id);
    }
}
