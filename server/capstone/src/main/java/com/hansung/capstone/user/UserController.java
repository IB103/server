package com.hansung.capstone.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        UserDTO.SignUpResponseDTO res = userService.signUp(req);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @PostMapping("/signin")
    private ResponseEntity<String> loginCheck(@RequestBody UserDTO.SignInRequestDTO req) {
        UserDTO.SignInResponseDTO res = userService.signIn(req);
        if (res.isCheck()){
            return new ResponseEntity<>(res.getNickname(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>("BAD",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/duplicate-check")
    private ResponseEntity<String> emailNickcheck(@RequestParam String mailOrNick) {
        Boolean check = userService.dupCheck(mailOrNick);
        if (check) {
            return new ResponseEntity<>("GOOD", HttpStatus.OK);
        } else{
            return new ResponseEntity<>("BAD", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findID")
    private ResponseEntity<List> findID(@RequestParam String username, @RequestParam String birthday){
        List<String> email = this.userService.findEmail(username, birthday);
        return new ResponseEntity<>(email, HttpStatus.OK);
    }

    @PutMapping("/modifyPW")
    public ResponseEntity<Optional<User>> updatePW(@RequestBody UserDTO.UpdatePWRequestDTO req){
        Optional<User> user = this.userService.updatePassword(req);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}