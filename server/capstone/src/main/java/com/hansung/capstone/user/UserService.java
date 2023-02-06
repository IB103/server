package com.hansung.capstone.user;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO.SignUpResponseDTO SignUp(UserDTO.SignUpRequestDTO req);

    UserDTO.SignInResponseDTO SignIn(UserDTO.SignInRequestDTO req);

    List<String> findEmail(String username, String birthday);

    Boolean EmailDupCheck(String email);

    Boolean NicknameDupCheck(String nickname);

    Optional<User> modifyPassword(UserDTO.ModifyPWRequestDTO req);

    Optional<User> modifyNickname(UserDTO.ModifyNickRequestDTO req);

}

