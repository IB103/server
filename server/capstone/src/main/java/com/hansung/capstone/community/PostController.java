package com.hansung.capstone.community;

import com.hansung.capstone.response.ListResponse;
import com.hansung.capstone.response.ResponseService;
import com.hansung.capstone.response.SingleResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public SingleResponse<Post> createPost(@RequestBody PostDTO.CreateRequestDTO req){
        return this.responseService.getSuccessSingleResponse(this.postService.createPost(req));
    }

    @PutMapping("/modify")
    public SingleResponse<Optional<Post>> modifyPost(@RequestBody PostDTO.ModifyRequestDTO req){
        return this.responseService.getSuccessSingleResponse(this.postService.modifyPost(req));
    }

    @GetMapping("/list")
    public ListResponse<Post> getAllPost(@RequestParam(defaultValue = "0") int page) {
        Page<Post> paging = this.postService.getAllPost(page);
        return this.responseService.getListResponse(paging.getContent());
    }

    @GetMapping("/detail")
    public SingleResponse<Optional<Post>> getDetailPost(@RequestParam Long id){
        return this.responseService.getSuccessSingleResponse(this.postService.getDetailPost(id));
    }
}
