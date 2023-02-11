package com.hansung.capstone.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "AppUser")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String nickname;

    private String username;

    private String birthday;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder
    public User(String email, String password, String nickname, String username, String birthday){
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.birthday = birthday;
        this.role = UserRole.USER;
    }


    public void modifyPW(String password){
        this.password = password;
    }

    public void modifyNick(String nickname){
        this.nickname = nickname;
    }

}
