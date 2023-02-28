package com.hansung.capstone.community;

import com.hansung.capstone.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_Id")
    private Long id;

    @Column(length = 40)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "post")
    private List<PostImage> postImages = new ArrayList<>();

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<User> voter = new HashSet<>();

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

    public void addImage(PostImage postImage){
        this.postImages.add(postImage);

        if(postImage.getPost() != this) {
            postImage.setPost(this);
        }
    }

}
