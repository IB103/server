package com.hansung.capstone.community;

import com.hansung.capstone.response.CommonResponse;
import com.hansung.capstone.response.ResponseService;
import com.hansung.capstone.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community/recomment")
public class ReCommentController {

    private final ReCommentService reCommentService;

    private final PostService postService;

    private final ResponseService responseService;

    @PostMapping("/create")
    private ResponseEntity<SingleResponse<PostDTO.PostResponseDTO>> createComment(@RequestBody ReCommentDTO.CreateRequestDTO req){
        return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(
                this.reCommentService.createReComment(req)), HttpStatus.CREATED);
    }

    @GetMapping("/favorite")
    public ResponseEntity<CommonResponse> commentFavorite(
            @RequestParam Long userId,
            @RequestParam Long postId,
            @RequestParam Long reCommentId){
        return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(
                this.reCommentService.setFavorite(userId,postId,reCommentId)), HttpStatus.OK);
    }
}
