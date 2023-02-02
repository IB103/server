package com.hansung.capstone.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    private ResponseEntity<UserDTO.SignUpResponseDTO> register(@RequestBody UserDTO.SignUpRequestDTO req){
        UserDTO.SignUpResponseDTO res = userService.create(req);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @PostMapping("/singin")
    private ResponseEntity<String> loginCheck(@RequestBody User req) {
        if (userService.check(req)){
            return new ResponseEntity<>("good", HttpStatus.OK);
        } else{
            return new ResponseEntity<>("bad",HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/findID")
    private ResponseEntity<String> findID(@RequestBody Map<String, String> emailMap){
        String id = userService.findID(emailMap.get("email"));
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}