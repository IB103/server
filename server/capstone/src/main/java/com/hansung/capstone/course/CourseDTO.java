package com.hansung.capstone.course;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CourseDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "CourseCreateDTO")
    public static class CreateRequestDTO{
        @NotBlank
        private String coordinates;
        @NotBlank
        private CourseRegion region;
        @NotBlank
        private String originToDestination;
        @Positive
        private Long userId;
        @NotBlank
        private String category;
        @NotBlank
        private String title;
        @NotBlank
        private String content;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CourseResponseDTO{
        private String coordinates;
        private String region;
        private String originToDestination;
        private Long postId;
    }
}
