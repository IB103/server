package com.hansung.capstone.community;

import com.hansung.capstone.user.User;
import com.hansung.capstone.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final PostImageRepository postImageRepository;

    private final ImageHandler imageHandler;

    private final ImageService imageService;


    @Transactional
    @Override
    public PostDTO.PostResponseDTO createPost(PostDTO.CreateRequestDTO req, List<MultipartFile> files) throws Exception {
        Post newPost = Post.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .createdDate(LocalDateTime.now())
                .author(this.userRepository.findById(req.getUserId()).get()).build();
        List<PostImage> postImageList = imageHandler.parsePostImageInfo(files);

        if(!postImageList.isEmpty()){
            for(PostImage postImage : postImageList){
                newPost.addImage(postImageRepository.save(postImage));
            }
        }
        return createResponse(this.postRepository.save(newPost));
    }

    @Transactional
    @Override
    public PostDTO.PostResponseDTO modifyPost(PostDTO.ModifyRequestDTO req, List<MultipartFile> files) throws Exception {
        Optional<Post> modifyPost = this.postRepository.findById(req.getId());
        List<PostImage> dbPostImageList = this.postImageRepository.findAllByPostId(req.getId());
        List<MultipartFile> addFileList = new ArrayList<>();
        if(CollectionUtils.isEmpty(dbPostImageList)){ // db에 존재 x
            if(!CollectionUtils.isEmpty(files)){ // 전달 file 존재
                for (MultipartFile multipartFile : files){
                    addFileList.add(multipartFile);
                }
            }
        }
        else{ // DB에 한장이상 존재
            if(CollectionUtils.isEmpty(files)){ // 전달 file x
                for(PostImage dbPostImage : dbPostImageList){
                    this.postImageRepository.deleteById(dbPostImage.getId());
                }
            }
            else{
                List<String> dbOriginNameList = new ArrayList<>();
                for(PostImage dbPostImage : dbPostImageList){
                    PostImageDTO dbPostImageDTO = this.imageService.findByFileId(dbPostImage.getId());
                    String dbOriginName = dbPostImageDTO.getOriginFileName();
                    if(!files.contains(dbOriginName)){
                        this.postImageRepository.deleteById(dbPostImage.getId());
                    } else{
                        dbOriginNameList.add(dbOriginName);
                    }
                }
                for (MultipartFile multipartFile : files){
                    String multipartOriginName = multipartFile.getOriginalFilename();
                    if(!dbOriginNameList.contains(multipartOriginName)){
                        addFileList.add(multipartFile);
                    }
                }
            }
        }
        List<PostImage> postImageList = imageHandler.parsePostImageInfo(addFileList);

        if(!postImageList.isEmpty()){
            for(PostImage postImage : postImageList){
                modifyPost.get().addImage(postImageRepository.save(postImage));
            }
        }

        modifyPost.ifPresent( s -> {
            modifyPost.get().modify(req.getTitle(), req.getContent(), LocalDateTime.now());
                }
        );
        return createResponse(this.postRepository.findById(req.getId()).get());
    }

    @Override
    public Page<Post> getAllPost(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));
        return this.postRepository.findAll(pageable);
    }

    @Override
    public Page<Post> getUserNickNamePost(String nickname, int page) {
        User user = this.userRepository.findByNickname(nickname).orElseThrow( () ->
                new IllegalArgumentException("게시글이 존재하지 않습니다."));
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));
        return this.postRepository.findAllByAuthor(user, pageable);
    }

    @Override
    public Page<Post> getTitleOrContentPost(String titleOrContent, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));
        return this.postRepository.findAllSearch(titleOrContent, pageable);
    }

    @Override
    public PostDTO.PostResponseDTO getDetailPost(Long id) {
        return createResponse(this.postRepository.findById(id).get());
    }

    @Transactional
    @Override
    public PostDTO.PostResponseDTO setFavorite(Long userId, Long postId) {
        Post post = this.postRepository.findById(postId).orElseThrow( () ->
                new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        User user = this.userRepository.findById(userId).orElseThrow( () ->
                new IllegalArgumentException("유저가 존재하지 않습니다.")
        );
        if(post.getVoter().contains(user)){
            post.getVoter().remove(user);
        }else {
            post.getVoter().add(user);
        }
        return createResponse(this.postRepository.findById(postId).get());
    }

    public PostDTO.PostResponseDTO createResponse(Post req){
        List<CommentDTO.ResponseDTO> comments = new ArrayList<>();
        Long profileImageId;
        if(req.getAuthor().getProfileImage() != null){
            profileImageId = req.getAuthor().getProfileImage().getId();
        }else{
            profileImageId = -1L;
        }
        if (req.getCommentList() != null) {
            for (int i = 0; i < req.getCommentList().size(); i++) {
                Comment comment = req.getCommentList().get(i);
                List<ReCommentDTO.ResponseDTO> reCommentList = new ArrayList<>();
                Set<User> commentVoter = comment.getVoter();
                Set<Long> commentVoterId = new HashSet<>();
                if(!commentVoter.isEmpty()){
                    for(User user : commentVoter) {
                        commentVoterId.add(user.getId());
                    }
                }
                if(comment.getReCommentList() != null) {
                    for (int j = 0; j < comment.getReCommentList().size(); j++) {
                        ReComment reComment = comment.getReCommentList().get(j);
                        Set<User> reCommentVoter = reComment.getVoter();
                        Set<Long> reCommentVoterId = new HashSet<>();
                        if(!reCommentVoter.isEmpty()){
                            for(User user : reCommentVoter) {
                                reCommentVoterId.add(user.getId());
                            }
                        }
                        ReCommentDTO.ResponseDTO reCommentRes = ReCommentDTO.ResponseDTO.builder()
                                .id(reComment.getId())
                                .content(reComment.getContent())
                                .createdDate(reComment.getCreatedDate())
                                .modifiedDate(reComment.getModifiedDate())
                                .userId(reComment.getAuthor().getId())
                                .userNickname(reComment.getAuthor().getNickname())
                                .userProfileImageId(reComment.getAuthor().getProfileImage().getId())
                                .reCommentVoterId(reCommentVoterId)
                                .build();
                        reCommentList.add(reCommentRes);
                    }
                }
                CommentDTO.ResponseDTO commentRes = CommentDTO.ResponseDTO.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .createdDate(comment.getCreatedDate())
                        .modifiedDate(comment.getModifiedDate())
                        .userId(comment.getAuthor().getId())
                        .userNickname(comment.getAuthor().getNickname())
                        .userProfileImageId(profileImageId)
                        .reCommentList(reCommentList)
                        .commentVoterId((commentVoterId))
                        .build();
                comments.add(commentRes);
            }
        }

        List<PostImageDTO.ResponseDTO> imageIdList = this.imageService.findAllByPostId(req.getId());
        List<Long> images = new ArrayList<>();
        for(PostImageDTO.ResponseDTO id : imageIdList){
            images.add(id.getFileId());
        }

        Set<User> postVoter = req.getVoter();
        Set<Long> postVoterId = new HashSet<>();
        if(!postVoter.isEmpty()){
            for(User user : postVoter) {
                postVoterId.add(user.getId());
            }
        }

        PostDTO.PostResponseDTO res = PostDTO.PostResponseDTO.builder()
                .id(req.getId())
                .title(req.getTitle())
                .content(req.getContent())
                .createdDate(req.getCreatedDate())
                .modifiedDate(req.getModifiedDate())
                .authorId(req.getAuthor().getId())
                .nickname(req.getAuthor().getNickname())
                .authorProfileImageId(profileImageId)
                .commentList(comments)
                .imageId(images)
                .postVoterId(postVoterId).build();
        return res;
    }


}
