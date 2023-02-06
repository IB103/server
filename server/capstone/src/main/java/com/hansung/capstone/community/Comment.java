package com.hansung.capstone.community;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifiedDate;

    @ManyToOne
    @JsonIgnore
    private Post post;

    @Builder
    public Comment(String content, LocalDateTime createDate, Post post){
        this.content = content;
        this.createDate = createDate;
        this.post = post;
    }

}
