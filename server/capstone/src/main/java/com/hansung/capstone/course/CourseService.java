package com.hansung.capstone.course;

import com.hansung.capstone.community.PostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {
    PostDTO.PostResponseDTO createCourse(CourseDTO.CreateRequestDTO req, List<MultipartFile> files) throws Exception;

}
