package com.hansung.capstone.user;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO.SignUpResponseDTO signUp(UserDTO.SignUpRequestDTO req);

    UserDTO.SignInResponseDTO signIn(UserDTO.SignInRequestDTO req);

    List<String> findEmail(String username, String birthday);

    Boolean dupCheck(String mailOrNick);

    Optional<User> updatePassword(UserDTO.UpdatePWRequestDTO req);

}

