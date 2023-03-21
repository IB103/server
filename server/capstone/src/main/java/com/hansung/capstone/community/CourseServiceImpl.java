package com.hansung.capstone.community;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;

    private final PostRepository postRepository;

    public String createCourse(String string, Long postId){
        Post post = this.postRepository.findById(postId).get();
        Course course = Course.builder()
                .coordinates(string)
                .post(post).build();
        this.courseRepository.save(course);
        return null;
    }
}
