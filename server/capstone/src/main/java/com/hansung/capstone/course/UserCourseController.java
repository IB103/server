package com.hansung.capstone.course;

import com.hansung.capstone.response.CommonResponse;
import com.hansung.capstone.response.ResponseService;
import com.hansung.capstone.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user-course")
@RequiredArgsConstructor
public class UserCourseController {

    private final UserCourseServiceImpl courseService;

    private final ResponseService responseService;

    @PostMapping("/create")
    public ResponseEntity<SingleResponse> createCourse(
            @RequestPart(value = "requestDTO") UserCourseDTO.CreateRequestDTO req,
            @RequestPart(value = "imageList") List<MultipartFile> files)  {
        try {
            return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(this.courseService.createCourse(req, files)), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<CommonResponse> getCourseListByRegion(
            @RequestParam(value = "region") String region,
            @RequestParam(defaultValue = "0") int page){
        Page<UserCourse> paging = this.courseService.getCourseListByRegion(page, region);
        return new ResponseEntity<>(this.responseService.getPageResponse(paging.getTotalPages(), preProcess(paging)), HttpStatus.OK);
    }


    private List<UserCourseDTO.CourseResponseDTO> preProcess(Page<UserCourse> paging){
        List<UserCourseDTO.CourseResponseDTO> res = new ArrayList<>();
        for(UserCourse userCourse : paging){
            res.add(this.courseService.createResponse(userCourse));
        }
        return res;
    }

}