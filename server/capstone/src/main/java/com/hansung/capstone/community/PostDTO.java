package com.hansung.capstone.community;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
}
