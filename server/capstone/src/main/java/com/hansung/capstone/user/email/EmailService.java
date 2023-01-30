package com.hansung.capstone.user.email;

public interface EmailService {
    String sendSimpleMessage(String to, String ePw) throws Exception;
}
