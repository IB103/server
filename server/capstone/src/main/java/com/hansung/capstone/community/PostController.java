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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/community/post")
@RequiredArgsConstructor
public class PostController {

    private final PostServiceImpl postService;

    private final ResponseService responseService;


    @PostMapping("/create")
    public ResponseEntity<SingleResponse<PostDTO.PostResponseDTO>> createPost(@RequestBody PostDTO.CreateRequestDTO req){
        return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(this.postService.createPost(req)), HttpStatus.CREATED);
    }

    @PutMapping("/modify")
    public ResponseEntity<SingleResponse<PostDTO.PostResponseDTO>> modifyPost(@RequestBody PostDTO.ModifyRequestDTO req){
        return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(this.postService.modifyPost(req)), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<ListResponse<PostDTO.PostResponseDTO>> getAllPost(@RequestParam(defaultValue = "0") int page) {
        Page<Post> paging = this.postService.getAllPost(page);
        List<PostDTO.PostResponseDTO> res = new ArrayList<>();
        for(int i = 0; i < paging.getSize(); i++){
            Post post = paging.getContent().get(i);
            res.add(this.postService.createResponse(post));
        }

        return new ResponseEntity<>(this.responseService.getListResponse(res), HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<SingleResponse<PostDTO.PostResponseDTO>> getDetailPost(@RequestParam Long id){
        return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(this.postService.getDetailPost(id)),HttpStatus.OK);
    }
}
