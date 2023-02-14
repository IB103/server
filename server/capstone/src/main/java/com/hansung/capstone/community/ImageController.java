package com.hansung.capstone.community;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @CrossOrigin
    @GetMapping(value = "/image/{id}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws Exception{
        ImageDTO imageDTO = this.imageService.findByFileId(id);
        String absolutePath
                = new File("").getAbsolutePath() + File.separator; // File.separator
        String path = imageDTO.getFilePath();

        InputStream imageStream = new FileInputStream(absolutePath + path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();;

        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }
}
