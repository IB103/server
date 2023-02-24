package com.hansung.capstone.user;

import jakarta.annotation.security.RolesAllowed;
import lombok.*;

public class UserDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
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
    @NoArgsConstructor
    public static class SignInRequestDTO {
        private String email;
        private String password;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class SignInResponseDTO {
        private boolean check;

        private String email;

        private Long userId;

        private String username;

        private String birthday;

        private String nickname;

        private Long profileImageId;

        private TokenInfo tokenInfo;
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
    @NoArgsConstructor
    public static class FindIdRequestDTO {
        private String username;
        private String birthday;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ModifyPWRequestDTO {
        private String email;
        private String password;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
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

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProfileImageRequestDTO{
        private Long id;

        private Long profileImageId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ProfileImageResponseDTO{
        private Long id;

        private String email;

        private String username;

        private String birthday;

        private String nickname;

        private Long profileImageId;
    }

}
