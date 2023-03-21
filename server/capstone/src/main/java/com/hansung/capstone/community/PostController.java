package com.hansung.capstone.community;

import com.hansung.capstone.response.*;
import com.hansung.capstone.user.AuthService;
import com.hansung.capstone.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/community/post")
@RequiredArgsConstructor
public class PostController {

    private final PostServiceImpl postService;

    private final ResponseService responseService;

    private final AuthService authService;



    @PostMapping("/create")
    public ResponseEntity<SingleResponse<PostDTO.PostResponseDTO>> createPost(
            @RequestPart(value = "requestDTO") PostDTO.CreateRequestDTO req,
            @RequestPart(value = "imageList", required = false) List<MultipartFile> files)  {
        try {
            return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(this.postService.createPost(req, files)), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/modify")
    public ResponseEntity<SingleResponse<PostDTO.PostResponseDTO>> modifyPost(
            @RequestPart(value = "requestDTO") PostDTO.ModifyRequestDTO req,
            @RequestPart(value = "imageList", required = false) List<MultipartFile> files ) throws Exception {
        return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(this.postService.modifyPost(req, files)), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<PageResponse<PostDTO.PostResponseDTO>> getAllPost(@RequestParam(defaultValue = "0") int page) {
        Page<Post> paging = this.postService.getAllPost(page);
        List<PostDTO.PostResponseDTO> res = new ArrayList<>();
        for(Post post : paging){
            res.add(this.postService.createResponse(post));
        }

        return new ResponseEntity<>(this.responseService.getPageResponse(paging.getTotalPages(),res), HttpStatus.OK);
    }


    @GetMapping("/detail")
    public ResponseEntity<SingleResponse<PostDTO.PostResponseDTO>> getDetailPost(@RequestParam Long id){
        return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(this.postService.getDetailPost(id)),HttpStatus.OK);
    }


    @PostMapping("/test")
    public ResponseEntity<SingleResponse<PostDTO.PostResponseDTO>> coursePost(
            @RequestPart(value = "requestDTO") PostDTO.CreateRequestDTO req,
            @RequestPart(value = "imageList", required = false) List<MultipartFile> files)  {
        try {
            return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(this.postService.createPost(req, files)), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/list/nickname")
    public ResponseEntity<ListResponse<PostDTO.PostResponseDTO>> getUserNickNamePost(
            @RequestParam String nickname,
            @RequestParam(defaultValue = "0") int page) {
        Page<Post> paging = this.postService.getUserNickNamePost(nickname, page);
        List<PostDTO.PostResponseDTO> res = new ArrayList<>();
        for(Post post : paging){
            res.add(this.postService.createResponse(post));
        }
        return new ResponseEntity<>(this.responseService.getListResponse(res), HttpStatus.OK);
    }

    @GetMapping("/list/title-or-content")
    public ResponseEntity<CommonResponse> getTitleOrContentPost(
            @RequestParam String titleOrContent,
            @RequestParam(defaultValue = "0") int page) {
        try{
            Page<Post> paging = this.postService.getTitleOrContentPost(titleOrContent, page);
            List<PostDTO.PostResponseDTO> res = new ArrayList<>();
            for(Post post : paging){
                res.add(this.postService.createResponse(post));
            }
            return new ResponseEntity<>(this.responseService.getListResponse(res), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(this.responseService.getFailureSingleResponse(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/list/scrap")
    public ResponseEntity<CommonResponse> getScrapPost(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page){
        try{
            Page<Post> paging = this.postService.getScrapPost(userId,page);
            List<PostDTO.PostResponseDTO> res = new ArrayList<>();
            for(Post post : paging){
                res.add(this.postService.createResponse(post));
            }
            return new ResponseEntity<>(this.responseService.getListResponse(res), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(this.responseService.getFailureSingleResponse(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/favorite")
    public ResponseEntity<CommonResponse> postFavorite(
            @RequestParam Long userId,
            @RequestParam Long postId){
        return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(this.postService.setFavorite(userId,postId)), HttpStatus.OK);
    }

    @GetMapping("/scrap")
    public ResponseEntity<CommonResponse> postScrap(
            @RequestParam Long userId,
            @RequestParam Long postId){
        return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(this.postService.setScrap(userId, postId)), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CommonResponse> deletePost(
            @RequestParam Long userId,
            @RequestParam Long postId
    ){
        try{
            this.postService.deletePost(userId,postId);
            return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.responseService.getFailureSingleResponse(null), HttpStatus.BAD_REQUEST);
        }
    }

}
