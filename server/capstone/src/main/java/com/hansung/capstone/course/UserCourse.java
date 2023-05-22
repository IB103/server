package com.hansung.capstone.course;

import com.hansung.capstone.community.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class UserCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String coordinates;

    private String region;

    private String originToDestination;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @Builder
    public UserCourse(String coordinates, Post post, String region, String originToDestination){
        this.coordinates = coordinates;
        this.post = post;
        this.region = region;
        this.originToDestination = originToDestination;
    }
}
