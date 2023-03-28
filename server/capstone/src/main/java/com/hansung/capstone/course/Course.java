package com.hansung.capstone.course;

import com.hansung.capstone.community.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String coordinates;

    @Enumerated(EnumType.STRING)
    private CourseRegion region;

    private String originToDestination;

    @OneToOne
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @Builder
    public Course(String coordinates, Post post, CourseRegion region){
        this.coordinates = coordinates;
        this.post = post;
        this.region = region;
    }
}
