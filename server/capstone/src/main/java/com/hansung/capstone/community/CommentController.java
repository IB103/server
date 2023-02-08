package com.hansung.capstone.community;

import com.hansung.capstone.response.ResponseService;
import com.hansung.capstone.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community/comment")
public class CommentController {

    private final CommentService commentService;

    private final PostService postService;

    private final ResponseService responseService;

    @PostMapping("/create")
    private ResponseEntity<SingleResponse<Optional<Post>>> createComment(@RequestBody CommentDTO.CreateRequestDTO req){
        Post post = this.postService.getDetailPost(req.getPostId()).get();
        this.commentService.createComment(post, req.getContent());
        return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(this.postService.getDetailPost(req.getPostId())), HttpStatus.CREATED);
    }

    @PutMapping("/modify")
    public ResponseEntity<SingleResponse<Optional<Post>>> modifyComment(@RequestBody CommentDTO.ModifyRequestDTO req){
        this.commentService.modifyComment(req.getId(), req.getContent());
        return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(this.postService.getDetailPost(req.getPostId())), HttpStatus.OK);
    }
}
