package com.hansung.capstone.community;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class PostDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CreateRequestDTO {
        private String title;
        private String content;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ModifyRequestDTO {
        private Long id;
        private String title;
        private String content;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CommentResponseDTO{
        private Long id;
        private String title;
        private String content;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
        private List<Comment> commentList;
    }
}
