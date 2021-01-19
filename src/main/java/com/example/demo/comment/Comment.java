package com.example.demo.comment;

import com.example.demo.post.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private int up;

    private int down;

    private boolean isBest;

    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;

}
