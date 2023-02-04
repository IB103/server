package com.hansung.capstone.user;

import com.hansung.capstone.DataExistException;
import com.hansung.capstone.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public List<String> findEmail(String username, String birthday){
        List<UserEmailInterface> appuser = this.userRepository.findByUsernameAndBirthday(username, birthday);
        List<String> res = new ArrayList<>();
        if (appuser.isEmpty()){
            throw new DataNotFoundException("AppUser Not Found");
        }
        else {
            for(UserEmailInterface s : appuser){
            String email = s.getEmail();
            int atIndex = email.indexOf("@");
            res.add(email.substring(0,2) + "*".repeat(atIndex-1) + email.substring(atIndex));
        }
            return res;
        }
    }

    @Override
    public Boolean dupCheck(String req){
        Optional<User> user1 = this.userRepository.findByEmail(req);
        Optional<User> user2 = this.userRepository.findByNickname(req);
        if(user1.isPresent() || user2.isPresent()){
            return false;
        } else{
            return true;
        }
    }

    @Override
    public Optional<User> updatePassword(UserDTO.UpdatePWRequestDTO req) {
        Optional<User> user = this.userRepository.findByEmail(req.getEmail());
        user.ifPresent(selectUser -> {
            selectUser.setPassword(passwordEncoder.encode(req.getPassword()));
            this.userRepository.save(selectUser);
        });
        user = this.userRepository.findByEmail(req.getEmail());
        return user;
    }


}
