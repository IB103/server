package com.hansung.capstone.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "AppUser")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Setter
    private String password;

    @Column(unique = true)
    @Setter
    private String nickname;

    private String username;

    private String birthday;

    @Builder
    public User(String email, String password, String nickname, String username, String birthday){
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.birthday = birthday;
    }
}
