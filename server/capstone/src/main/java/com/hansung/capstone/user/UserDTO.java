package com.hansung.capstone.user;

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
        private boolean check;
        private String nickname;
        private String token;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class DuplicateEmailCheckResponseDTO{
        private String email;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class DuplicateNicknameCheckResponseDTO{
        private String nickname;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    public static class FindIdRequestDTO {
        private String username;
        private String birthday;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ModifyPWRequestDTO {
        private String email;
        private String password;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ModifyNickRequestDTO {
        private String email;
        private String nickname;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PostUserResponseDTO{
        private Long id;
        private String nickname;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CommentUserResponseDTO{
        private Long id;
        private String nickname;
    }

}
