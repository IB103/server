package com.hansung.capstone.user;

public interface UserService {
    UserDTO.SignUpResponseDTO signUp(UserDTO.SignUpRequestDTO req);

    UserDTO.SignInResponseDTO signIn(UserDTO.SignInRequestDTO req);

    String findID(String email);


}

