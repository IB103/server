package com.hansung.capstone.user;

import com.hansung.capstone.DataExistException;
import com.hansung.capstone.DataNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
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

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    public UserDTO.SignUpResponseDTO SignUp(UserDTO.SignUpRequestDTO req) {
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

//
//    @Override
//    public TokenInfo SignIn(UserDTO.SignInRequestDTO req){
//        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
//        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword());
//
//        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
//        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//        // 3. 인증 정보를 기반으로 JWT 토큰 생성
//        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
//
//        return tokenInfo;
//    }

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
            res.add(email.substring(0,2) + "*".repeat(atIndex-2) + email.substring(atIndex));
        }
            return res;
        }
    }

    @Override
    public Boolean EmailDupCheck(String email){
        Optional<User> user = this.userRepository.findByEmail(email);
        if(user.isPresent()){
            return false;
        } else{
            return true;
        }
    }

    @Override
    public Boolean NicknameDupCheck(String nickname){
        Optional<User> user = this.userRepository.findByNickname(nickname);
        if(user.isPresent()){
            return false;
        } else{
            return true;
        }
    }

    @Transactional
    @Override
    public Optional<User> modifyPassword(UserDTO.ModifyPWRequestDTO req) {
        Optional<User> user = this.userRepository.findByEmail(req.getEmail());
        user.ifPresent(s -> {
            user.get().modifyPW(passwordEncoder.encode(req.getPassword()));
        });
        Optional<User> modifiedUser = this.userRepository.findByEmail(req.getEmail());
        return modifiedUser;
    }

    @Transactional
    @Override
    public Optional<User> modifyNickname(UserDTO.ModifyNickRequestDTO req){
        Optional<User> user = this.userRepository.findByEmail(req.getEmail());
        user.ifPresent(s-> {
            user.get().modifyNick(req.getNickname());
        });
        Optional<User> modifiedUser = this.userRepository.findByEmail(req.getEmail());
        return modifiedUser;
    }
}
