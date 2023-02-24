package com.hansung.capstone.community;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class PostDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateRequestDTO {
        private Long userId;
        private String title;
        private String content;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PostResponseDTO {
        private Long id;
        private String title;
        private String content;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
        private Long authorId;
        private String nickname;
        private Long authorProfileImageId;
        private List<CommentDTO.ResponseDTO> commentList;
        private List<Long> imageId;

        private Set<Long> postVoterId;

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
