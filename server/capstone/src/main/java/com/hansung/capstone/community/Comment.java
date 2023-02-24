package com.hansung.capstone.community;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hansung.capstone.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @ManyToOne
    @JsonIgnore
    private Post post;

    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<ReComment> reCommentList;

    @ManyToMany
    private Set<User> voter;

    @Builder
    public Comment(String content, LocalDateTime createdDate, Post post, User author){
        this.content = content;
        this.createdDate = createdDate;
        this.post = post;
        this.author = author;
    }


    public void modify(String content, LocalDateTime modifiedDate){
        this.content = content;
        this.modifiedDate = modifiedDate;
    }

}
