package com.hansung.capstone.community;

import com.hansung.capstone.response.ListResponse;
import com.hansung.capstone.response.ResponseService;
import com.hansung.capstone.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/community/post")
@RequiredArgsConstructor
public class PostController {

    private final PostServiceImpl postService;

    private final ResponseService responseService;



    @PostMapping("/create")
    public ResponseEntity<SingleResponse<PostDTO.PostResponseDTO>> createPost(
            @RequestPart(value = "requestDTO") PostDTO.CreateRequestDTO req,
            @RequestPart(value = "image", required = false) List<MultipartFile> files)  {
        try {
            return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(this.postService.createPost(req, files)), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/modify")
    public ResponseEntity<SingleResponse<PostDTO.PostResponseDTO>> modifyPost(
            @RequestPart(value = "requestDTO") PostDTO.ModifyRequestDTO req,
            @RequestPart(value = "image", required = false) List<MultipartFile> files ) throws Exception {
        return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(this.postService.modifyPost(req, files)), HttpStatus.OK);
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


    @GetMapping("/test")
    public String test(){
        return "hi";
    }
}
