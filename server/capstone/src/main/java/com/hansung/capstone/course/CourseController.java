package com.hansung.capstone.course;

import com.hansung.capstone.response.ResponseService;
import com.hansung.capstone.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/community/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseServiceImpl courseService;

    private final ResponseService responseService;

    @PostMapping("/create")
    public ResponseEntity<SingleResponse> createCourse(
            @RequestPart(value = "requestDTO") CourseDTO.CreateRequestDTO req,
            @RequestPart(value = "imageList") List<MultipartFile> files)  {
        try {
            return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(this.courseService.createCourse(req, files)), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
