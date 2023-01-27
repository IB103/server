package com.hansung.capstone.user;

import com.hansung.capstone.user.AppUser;
import com.hansung.capstone.user.UserCreateForm;
import com.hansung.capstone.user.UserRepository;
import com.hansung.capstone.user.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Map;
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
        if (userService.check(req)){
            return new ResponseEntity<>("good", HttpStatus.OK);
        } else{
            return new ResponseEntity<>("bad",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    private ResponseEntity<String> register(@RequestBody Optional<UserCreateForm> req){
        UserCreateForm user = req.get();
        userService.create(user.getUsername(), user.getPassword1(), user.getEmail());
        return new ResponseEntity<>("good",HttpStatus.OK);
    }

    @GetMapping("/findID")
    private ResponseEntity<String> findID(@RequestBody Map<String, String> emailMap){
        String id = userService.findID(emailMap.get("email"));
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
