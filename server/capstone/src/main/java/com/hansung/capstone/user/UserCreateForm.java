package com.hansung.capstone.user;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserCreateForm {
    private String username;
    private String password1;
    private String password2;
    private String email;
}
