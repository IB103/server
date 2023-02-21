package com.hansung.capstone.community;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    /*
        이미지 개별 조회
     */
    @Transactional(readOnly = true)
    public PostImageDTO findByFileId(Long id){
        PostImage postImage = imageRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 파일이 존재하지 않습니다.")
        );

        PostImageDTO postImageDTO = PostImageDTO.builder()
                .originFileName(postImage.getOriginFileName())
                .filePath(postImage.getFilePath())
                .fileSize(postImage.getFileSize()).build();

        return postImageDTO;
    }

    @Transactional(readOnly = true)
    public List<PostImageDTO.ResponseDTO> findAllByPostId(Long postId){
        List<PostImage> postImageList = this.imageRepository.findAllByPostId(postId);
        return postImageList.stream()
                .map(PostImageDTO.ResponseDTO::new)
                .collect(Collectors.toList());

    }
}
