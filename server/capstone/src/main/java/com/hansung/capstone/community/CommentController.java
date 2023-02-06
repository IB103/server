package com.hansung.capstone.community;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community/comment")
public class CommentController {

    private final CommentService commentService;

    private final PostService postService;

    @PostMapping("/create")
    private ResponseEntity<Optional<Post>> createComment(@RequestBody CommentDTO.CreateRequestDTO req){
        Post post = this.postService.getDetailPost(req.getPostId()).get();
        this.commentService.createComment(post, req.getContent());
        return new ResponseEntity<>(this.postService.getDetailPost(req.getPostId()), HttpStatus.OK);
    }
}
