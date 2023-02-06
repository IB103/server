package com.hansung.capstone.user;

import com.hansung.capstone.response.ListResponse;
import com.hansung.capstone.response.ResponseService;
import com.hansung.capstone.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    private final ResponseService responseService;

    @PostMapping("/signup")
    private ResponseEntity<SingleResponse<UserDTO.SignUpResponseDTO>> SignUp(@RequestBody UserDTO.SignUpRequestDTO req){
        SingleResponse<UserDTO.SignUpResponseDTO> res = this.responseService.getSuccessSingleResponse(this.userService.SignUp(req));
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @PostMapping("/signin")
    private ResponseEntity<SingleResponse<UserDTO.SignInResponseDTO>> SignIn(@RequestBody UserDTO.SignInRequestDTO req) {
        UserDTO.SignInResponseDTO res = userService.SignIn(req);
        if (res.isCheck()){
            return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(res), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(this.responseService.getFailureSingleResponse(),HttpStatus.OK);
        }
    }

    @GetMapping("/email/duplicate-check")
    public ResponseEntity<SingleResponse> EmailDuplicateCheck(@RequestParam String email) {
        Boolean isCheck = userService.EmailDupCheck(email);
        if (isCheck) {
            return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(email), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(this.responseService.getFailureSingleResponse(), HttpStatus.OK);
        }
    }

    @GetMapping("/nickname/duplicate-check")
    public ResponseEntity<SingleResponse<String>> NicknameDuplicateCheck(@RequestParam String nickname){
        Boolean isCheck = this.userService.NicknameDupCheck(nickname);
        if (isCheck){
            return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(nickname), HttpStatus.OK);
        } else
            return new ResponseEntity<>(this.responseService.getFailureSingleResponse(), HttpStatus.OK);
    }

    @GetMapping("/findID")
    public ResponseEntity<ListResponse<String>> findID(@RequestParam String username, @RequestParam String birthday){
        List<String> email = this.userService.findEmail(username, birthday);
        return new ResponseEntity<>(this.responseService.getListResponse(email), HttpStatus.OK);
    }

    @PutMapping("/modifyPW")
    public ResponseEntity<SingleResponse<String>> modifyPW(@RequestBody UserDTO.ModifyPWRequestDTO req){
        this.userService.modifyPassword(req);
        return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(null),HttpStatus.OK);
    }

    @PutMapping("/modifyNick")
    public ResponseEntity<SingleResponse<String>> modifyNick(@RequestBody UserDTO.ModifyNickRequestDTO req){
        this.userService.modifyNickname(req);
        return new ResponseEntity<>(this.responseService.getSuccessSingleResponse(req.getNickname()), HttpStatus.OK);
    }
}