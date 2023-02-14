package com.hansung.capstone.community;

import com.hansung.capstone.user.User;
import com.hansung.capstone.user.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Image> images = new ArrayList<>();

    @Builder
    public Post(Post post ,String title, String content, LocalDateTime createdDate, User author){
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.author = author;
    }

    public void modify(String title, String content, LocalDateTime modifiedDate){
        this.title = title;
        this.content = content;
        this.modifiedDate = modifiedDate;
    }

    public void addImage(Image image){
        this.images.add(image);

        if(image.getPost() != this) {
            image.setPost(this);
        }
    }

}
