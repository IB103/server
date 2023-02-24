package com.hansung.capstone.community;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hansung.capstone.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.CompletionException;

@Entity
@Getter
@NoArgsConstructor
public class ReComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recomment_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @ManyToOne
    @JsonIgnore
    private Comment comment;

    @ManyToOne
    private User author;

    @ManyToMany
    private Set<User> voter;

    @Builder
    public ReComment(String content, LocalDateTime createdDate, Comment comment, User author){
        this.content = content;
        this.createdDate = createdDate;
        this.comment = comment;
        this.author = author;
    }

    public void modify(String content, LocalDateTime modifiedDate){
        this.content = content;
        this.modifiedDate = modifiedDate;
    }
}
