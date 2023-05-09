package com.hansung.capstone.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRidingRepository extends JpaRepository<UserRiding, Long> {

    @Query(
            value = "SELECT * FROM user_riding WHERE user_id = :userId and created_date <= :now and created_date >= :period",
            nativeQuery = true
    )
    List<UserRiding> findAllByPeriod(@Param("userId") Long userId, @Param("now") LocalDateTime now, @Param("period") LocalDateTime period);
}
