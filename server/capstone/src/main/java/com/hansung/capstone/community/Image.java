package com.hansung.capstone.community;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne
    private Post post;

    private String originFileName;

    private String filePath;

    private Long fileSize;


    @Builder
    public Image(String originFileName, String filePath, Long fileSize){
        this.originFileName = originFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public void setPost(Post post){
        this.post = post;

        if(!post.getImages().contains(this)){
            post.getImages().add(this);
        }
    }

}
