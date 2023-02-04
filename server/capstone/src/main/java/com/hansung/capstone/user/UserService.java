package com.hansung.capstone.user;

import java.util.List;

public interface UserService {
    UserDTO.SignUpResponseDTO signUp(UserDTO.SignUpRequestDTO req);

    UserDTO.SignInResponseDTO signIn(UserDTO.SignInRequestDTO req);

    List<String> findEmail(String username, String birthday);

    Boolean dupCheck(String mailOrNick);

}

