package com.hansung.capstone.community;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/community/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseServiceImpl courseService;

    @PostMapping("/create")
    public String createCourse(@RequestBody CourseDTO courseDTO){
        return this.courseService.createCourse(courseDTO.getString(), courseDTO.getPostId());
    }
}
