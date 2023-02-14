package com.hansung.capstone.community;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ImageHandler {
    private final ImageService imageService;

    public ImageHandler(ImageService imageService) {
        this.imageService = imageService;
    }

    public List<Image> parseFileInfo(List<MultipartFile> multipartFiles) throws Exception {
        List<Image> imageList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(multipartFiles)){
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern("yyyyMMdd");
            String current_date = now.format(dateTimeFormatter);

            String absolutePath = new File("").getAbsolutePath() + File.separator;

            String path = "images" + File.separator + current_date;
            File file = new File(path);

            if(!file.exists()) {
                boolean wasSuccessful = file.mkdirs();
                if(!wasSuccessful) {
                    log.info("file: was not successful");
                }
            }

            for(MultipartFile multipartFile : multipartFiles){
                String contentType = multipartFile.getContentType();
                String extension;
                String originalFileName = multipartFile.getOriginalFilename();
                String originalFileExtension = FilenameUtils.getExtension(originalFileName);
                if(ObjectUtils.isEmpty(contentType)){
                    break;
                }
                else {  // 확장자가 jpeg, png인 파일들만 받아서 처리
                    if(originalFileExtension.equals("jpeg") || originalFileExtension.equals("png")){
                        extension = '.' + originalFileExtension;
                    }
                    else  // 다른 확장자일 경우 처리 x
                        break;
                }

                String new_file_name = System.nanoTime() + extension;

                ImageDTO imageDTO = ImageDTO.builder()
                        .originFileName(multipartFile.getOriginalFilename())
                        .filePath(path + File.separator + new_file_name)
                        .fileSize(multipartFile.getSize()).build();

                Image image = new Image(
                        imageDTO.getOriginFileName(),
                        imageDTO.getFilePath(),
                        imageDTO.getFileSize()
                );
                imageList.add(image);

                file = new File(absolutePath + path + File.separator + new_file_name);
                multipartFile.transferTo(file);

                file.setWritable(true);
                file.setReadable(true);
            }
        }
        return imageList;
    }
}
