package com.hansung.capstone.user;

import com.hansung.capstone.user.AppUser;
import com.hansung.capstone.user.UserCreateForm;
import com.hansung.capstone.user.UserRepository;
import com.hansung.capstone.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;


    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @PostMapping("/check")
    private ResponseEntity<String> loginCheck(@RequestBody AppUser req) {
        System.out.println(req.getUsername());
        System.out.println(req.getEmail());
        System.out.println(req.getPassword());
        if (userService.check(req)){
            return new ResponseEntity<>("good", HttpStatus.OK);
        } else{
            return new ResponseEntity<>("bad",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    private ResponseEntity register(@RequestBody Optional<UserCreateForm> req){
        UserCreateForm user = req.get();
        userService.create(user.getUsername(), user.getPassword1(), user.getEmail());
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("/hello")
    public Optional<AppUser> hello(){
        AppUser user = userRepository.findByusername("hoon").get();
        System.out.println(user.getPassword());
        return userRepository.findByusername("hoon");
    }

    @GetMapping("/hi")
    public ResponseEntity<String> hi(){
        return new ResponseEntity<>("hi", HttpStatus.OK);

    }

    @PostMapping("/tt")
    public ResponseEntity<String> t(@RequestBody UserCreateForm user){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/ho")
    public ResponseEntity<String> ho(Authentication d) {
        return new ResponseEntity<>("hi " + d.getName(), HttpStatus.OK);
    }

    @PostMapping("/blog")
    public String blogPost(@RequestBody AppUser req){
        return req.getUsername() + "의 블로그입니다.";
    }
}
