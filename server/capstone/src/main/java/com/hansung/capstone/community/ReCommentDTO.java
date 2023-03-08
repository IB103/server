package com.hansung.capstone.community;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

public class ReCommentDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "ReCommentCreateDTO")
    public static class CreateRequestDTO{
        Long postId;
        Long commentId;
        Long userId;
        String content;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ModifyRequestDTO{
        Long commentId;
        Long id;
        String content;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @Schema(name = "ReCommentResponseDTO")
    public static class ResponseDTO{
        Long id;
        String content;
        LocalDateTime createdDate;
        LocalDateTime modifiedDate;
        Long userId;
        String userNickname;
        Long userProfileImageId;

        Set<Long> reCommentVoterId;
    }
}
