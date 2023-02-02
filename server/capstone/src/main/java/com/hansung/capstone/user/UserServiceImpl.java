package com.hansung.capstone.user;

import com.hansung.capstone.DataExistException;
import com.hansung.capstone.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO.SignUpResponseDTO signUp(UserDTO.SignUpRequestDTO req) {
        Optional<User> appuser = this.userRepository.findByEmail(req.getEmail());
        if(!appuser.isPresent()) {
            User newuser = User.builder()
                    .email(req.getEmail())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .nickname(req.getNickname())
                    .username(req.getUsername())
                    .birthday(req.getBirthday())
                    .build();
            this.userRepository.save(newuser);
            Optional<User> nUser = this.userRepository.findByEmail(req.getEmail());
            UserDTO.SignUpResponseDTO res = UserDTO.SignUpResponseDTO.builder()
                    .id(nUser.get().getId())
                    .nickname(req.getNickname()).build();
            return res;
        }
        else{
            throw new DataExistException("Already exist");
        }
    }


    @Override
    public UserDTO.SignInResponseDTO signIn(UserDTO.SignInRequestDTO req){
        Optional<User> appuser = this.userRepository.findByEmail(req.getEmail());
        if (!appuser.isPresent()){
            throw new DataNotFoundException("");
        }
        if (req.getEmail().equals(appuser.get().getEmail()) && passwordEncoder.matches(req.getPassword(), appuser.get().getPassword())){
            return UserDTO.SignInResponseDTO.builder()
                    .nickname(appuser.get().getNickname())
                    .check(Boolean.TRUE).build();
        } else{
            return UserDTO.SignInResponseDTO.builder()
                    .check(Boolean.FALSE).build();
        }
    }

    @Override
    public String findID(String email){
        Optional<User> appuser = this.userRepository.findByEmail(email);
        if (!appuser.isPresent()){
            throw new DataNotFoundException("AppUser Not Found");
        }
        else {
            return appuser.get().getUsername();
        }
    }

//    public void updatePW(String username, String newpw){
//        User req = this.userRepository.findByusername(username).get();
//        req.setPassword(passwordEncoder.encode(newpw));
//        userRepository.save(req);
//    }


}
