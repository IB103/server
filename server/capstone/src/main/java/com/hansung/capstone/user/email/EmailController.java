package com.hansung.capstone.user.email;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailServiceImpl emailService;

    public EmailController(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }


    @PostMapping("/send")
    public String emailSend(@RequestParam String email) throws Exception {
        String code = emailService.createKey();
        emailService.sendSimpleMessage(email,code);
        return code;
    }

    @PostMapping("/confirm")
    public String emailConfirm(@RequestParam String email, @RequestParam String code) throws Exception {
        if(emailService.checkCode(email,code)){
            return "good";
        } else{
            return "bad";
        }
    }
}
