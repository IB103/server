package com.hansung.capstone.community;

import com.hansung.capstone.response.CommonResponse;
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
    private ResponseEntity<SingleResponse<PostDTO.PostResponseDTO>> createComment(@RequestBody CommentDTO.CreateRequestDTO req){
        return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(this.commentService.createComment(req)), HttpStatus.CREATED);
    }

    @PutMapping("/modify")
    public ResponseEntity<SingleResponse<PostDTO.PostResponseDTO>> modifyComment(@RequestBody CommentDTO.ModifyRequestDTO req){
        this.commentService.modifyComment(req.getId(), req.getContent());
        return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(this.postService.getDetailPost(req.getPostId())), HttpStatus.OK);
    }

    @GetMapping("/favorite")
    public ResponseEntity<CommonResponse> commentFavorite(
            @RequestParam Long userId,
            @RequestParam Long postId,
            @RequestParam Long commentId){
        return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(this.commentService.setFavorite(userId,postId,commentId)), HttpStatus.OK);
    }
}
