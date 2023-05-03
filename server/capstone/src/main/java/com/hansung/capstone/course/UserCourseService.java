package com.hansung.capstone.course;

import com.hansung.capstone.community.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserCourseService {
    PostDTO.FreePostResponseDTO createCourse(UserCourseDTO.CreateRequestDTO req, List<MultipartFile> files, MultipartFile thumbnail) throws Exception;

    Page<UserCourse> getCourseListByRegion(int page, String region);

    PostDTO.FreePostResponseDTO getDetailPost(Long id);
}
