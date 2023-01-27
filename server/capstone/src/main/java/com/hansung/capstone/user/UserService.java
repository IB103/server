package com.hansung.capstone.user;

import com.hansung.capstone.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUser create(String username, String password, String email) {
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }


    public Boolean check(AppUser req){
        Optional<AppUser> appuser = this.userRepository.findByusername(req.getUsername());
        if (!appuser.isPresent()){
            throw new DataNotFoundException("AppUser Not Found");
        }
        if (req.getUsername().equals(appuser.get().getUsername()) && passwordEncoder.matches(req.getPassword(), appuser.get().getPassword())){
            return Boolean.TRUE;
        } else{
            return Boolean.FALSE;
        }
    }

    public String findID(String email){
        Optional<AppUser> appuser = this.userRepository.findByEmail(email);
        if (!appuser.isPresent()){
            throw new DataNotFoundException("AppUser Not Found");
        }
        else {
            return appuser.get().getUsername();
        }
    }


}
