package com.hansung.capstone.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    private ResponseEntity<UserDTO.SignUpResponseDTO> SignUp(@RequestBody UserDTO.SignUpRequestDTO req){
        UserDTO.SignUpResponseDTO res = userService.SignUp(req);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @PostMapping("/signin")
    private ResponseEntity<String> SignIn(@RequestBody UserDTO.SignInRequestDTO req) {
        UserDTO.SignInResponseDTO res = userService.SignIn(req);
        if (res.isCheck()){
            return new ResponseEntity<>(res.getNickname(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>("BAD",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/email/duplicate-check")
    private ResponseEntity<String> EmailDuplicateCheck(@RequestParam String email) {
        Boolean isCheck = userService.EmailDupCheck(email);
        if (isCheck) {
            return new ResponseEntity<>("GOOD", HttpStatus.OK);
        } else{
            return new ResponseEntity<>("BAD", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/nickname/duplicate-check")
    public ResponseEntity<String> NicknameDuplicateCheck(@RequestParam String nickname){
        Boolean isCheck = this.userService.NicknameDupCheck(nickname);
        if (isCheck){
            return new ResponseEntity<>("GOOD", HttpStatus.OK);
        } else
            return new ResponseEntity<>("BAD", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/findID")
    private ResponseEntity<List> findID(@RequestParam String username, @RequestParam String birthday){
        List<String> email = this.userService.findEmail(username, birthday);
        return new ResponseEntity<>(email, HttpStatus.OK);
    }

    @PutMapping("/modifyPW")
    public ResponseEntity<String> modifyPW(@RequestBody UserDTO.ModifyPWRequestDTO req){
        this.userService.modifyPassword(req);
        return new ResponseEntity<>("success",HttpStatus.OK);
    }

    @PutMapping("/modifyNick")
    public ResponseEntity<String> modifyNick(@RequestBody UserDTO.ModifyNickRequestDTO req){
        this.userService.modifyNickname(req);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}