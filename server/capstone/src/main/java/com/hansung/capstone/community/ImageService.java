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
    public ImageDTO findByFileId(Long id){
        Image image = imageRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 파일이 존재하지 않습니다.")
        );

        ImageDTO imageDTO = ImageDTO.builder()
                .originFileName(image.getOriginFileName())
                .filePath(image.getFilePath())
                .fileSize(image.getFileSize()).build();

        return imageDTO;
    }

    @Transactional(readOnly = true)
    public List<ImageDTO.ResponseDTO> findAllByPostId(Long postId){
        List<Image> imageList = this.imageRepository.findAllByPostId(postId);
        return imageList.stream()
                .map(ImageDTO.ResponseDTO::new)
                .collect(Collectors.toList());

    }
}
