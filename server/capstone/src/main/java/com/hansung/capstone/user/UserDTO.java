package com.hansung.capstone.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class SignUpRequestDTO {
        private String email;
        private String password;
        private String nickname;
        private String username;
        private String birthday;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class SignUpResponseDTO {
        private Long id;
        private String nickname;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class SignInRequestDTO {
        private String email;
        private String password;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class SignInResponseDTO {
        private String nickname;
    }

}
