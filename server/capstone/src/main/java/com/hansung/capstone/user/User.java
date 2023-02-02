package com.hansung.capstone.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "AppUser")
@Getter
@Builder
@NoArgsConstructor
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
}
