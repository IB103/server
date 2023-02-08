package com.hansung.capstone.community;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class CommentDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CreateRequestDTO {
        Long postId;
        String content;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ModifyRequestDTO {
        Long postId;
        Long id;
        String content;
    }
}
