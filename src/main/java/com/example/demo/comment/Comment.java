package com.example.demo.comment;

import com.example.demo.account.Account;
import com.example.demo.post.Post;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private int up;

    private int down;

    private boolean isBest;

    @CreatedDate
    private Date created;

    @CreatedBy
    @ManyToOne
    private Account createdBy;

    @LastModifiedDate
    private Date updated;

    @LastModifiedBy
    @ManyToOne
    private Account updatedBy;

    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;

    // auditAware 대신 @PrePersist를 사용할 수 있다.
    @PrePersist
    public void prePersist(){
        System.out.println("pre Persist is called");
    }
}
