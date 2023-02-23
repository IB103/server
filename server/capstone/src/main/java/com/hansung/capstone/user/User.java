package com.hansung.capstone.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hansung.capstone.community.PostImage;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "AppUser")
@Getter
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

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne
    @JoinColumn(name = "profileImage_Id")
    @JsonIgnore
    private ProfileImage profileImage;

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

    public void modifyProfileImage(ProfileImage profileImage){
        this.profileImage = profileImage;
    }
    public void addProfileImage(ProfileImage profileImage){
        this.profileImage = profileImage;

        if(profileImage.getUser() != this) {
            profileImage.setUser(this);
        }
    }

}
