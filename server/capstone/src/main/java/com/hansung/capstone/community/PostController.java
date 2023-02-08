package com.hansung.capstone.community;

import com.hansung.capstone.response.ListResponse;
import com.hansung.capstone.response.ResponseService;
import com.hansung.capstone.response.SingleResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/community/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final ResponseService responseService;


    @PostMapping("/create")
    public ResponseEntity<SingleResponse<Post>> createPost(@RequestBody PostDTO.CreateRequestDTO req){
        return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(this.postService.createPost(req)), HttpStatus.CREATED);
    }

    @PutMapping("/modify")
    public ResponseEntity<SingleResponse<Optional<Post>>> modifyPost(@RequestBody PostDTO.ModifyRequestDTO req){
        return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(this.postService.modifyPost(req)), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<ListResponse<Post>> getAllPost(@RequestParam(defaultValue = "0") int page) {
        Page<Post> paging = this.postService.getAllPost(page);
        return new ResponseEntity<>(this.responseService.getListResponse(paging.getContent()), HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<SingleResponse<Optional<Post>>> getDetailPost(@RequestParam Long id){
        return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(this.postService.getDetailPost(id)),HttpStatus.OK);
    }
}
