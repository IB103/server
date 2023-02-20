package com.hansung.capstone.community;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageDTO {
    private String originFileName;
    private String filePath;
    private Long fileSize;

    @Builder
    public ImageDTO(String originFileName, String filePath, Long fileSize){
        this.originFileName = originFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    @Getter
    public static class ResponseDTO{
        private Long fileId;
        public ResponseDTO(Image entity){
            this.fileId = entity.getId();
        }
    }

}
