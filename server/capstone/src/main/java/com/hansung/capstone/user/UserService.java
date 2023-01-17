package com.hansung.capstone.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public AppUser create(String username, String password, String email) {
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        this.userRepository.save(user);
        return user;
    }

    public Boolean check(AppUser req){
        AppUser db = userRepository.findByusername(req.getUsername()).get();
        if (req.getUsername().equals(db.getUsername()) && req.getPassword().equals(db.getPassword())){
            return Boolean.TRUE;
        } else{
            return Boolean.FALSE;
        }
    }

//    public User getUser(String username){
//        Optional<User> user = this.userRepository.findByUsername(username);
//        if(user.isPresent()){
//            return user.get();
//        }else{
//            throw new DataNotFoundException("");
//        }

}
