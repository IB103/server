package com.hansung.capstone.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {

    @Query(
            value = "select * from user_course where region = :region and post_id in (select post_post_id from post_voter group by post_post_id HAVING COUNT(*) >= 1)",
            nativeQuery = true
    )
    Page<UserCourse> findAllByRegion(Pageable pageable, @Param("region") String region);
}
