package com.hansung.capstone.course;

import com.hansung.capstone.community.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserCourseService {
    PostDTO.PostResponseDTO createCourse(UserCourseDTO.CreateRequestDTO req, List<MultipartFile> files) throws Exception;

    Page<UserCourse> getCourseListByRegion(int page, String region);

    PostDTO.PostResponseDTO getDetailPost(Long id);
}
