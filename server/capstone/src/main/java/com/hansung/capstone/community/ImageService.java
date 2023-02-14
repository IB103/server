package com.hansung.capstone.community;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
