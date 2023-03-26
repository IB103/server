package com.hansung.capstone.community;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {
    PostDTO.PostResponseDTO createCourse(CourseDTO.CreateRequestDTO req, List<MultipartFile> files) throws Exception;

}
