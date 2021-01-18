package com.example.demo;

import com.example.demo.comment.Comment;
import com.example.demo.comment.CommentRepository;
import com.example.demo.comment.CommentSummary;
import com.example.demo.post.Post;
import com.example.demo.post.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentControllerTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Test
    public void findAll() {
        Post post = new Post();
        post.setTitle("Test title");
        Post savedPost = postRepository.save(post);

        Comment comment = new Comment();
        comment.setComment("Test comment");
        comment.setPost(savedPost);
        commentRepository.save(comment);

        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList.size()).isEqualTo(1);
    }

    @Test
    public void findById() {
        Post post = new Post();
        post.setTitle("Test title");
        Post savedPost = postRepository.save(post);

        Comment comment = new Comment();
        comment.setComment("Test comment");
        comment.setPost(savedPost);
        commentRepository.save(comment);

        Optional<Comment> byId = commentRepository.findById(1l);
        byId.ifPresent(c -> {
            System.out.println(byId.get().getPost().getTitle());
            byId.get().setComment("test");
        });
    }

   /* @Test
    public void closedProjection() {
        Post post = new Post();
        post.setTitle("Test title");
        Post savedPost = postRepository.save(post);

        Comment comment = new Comment();
        comment.setComment("Test comment");
        comment.setPost(savedPost);
        commentRepository.save(comment);

        List<CommentSummary> byId = commentRepository.findByPost_Id(1l);
        assertThat(byId.size()).isEqualTo(1);
        byId.forEach(x -> System.out.println(x.getComment()));
    }*/

    @Test
    public void openProjection() {
        Post post = new Post();
        post.setTitle("Test title");
        Post savedPost = postRepository.save(post);

        Comment comment = new Comment();
        comment.setComment("Test comment");
        comment.setPost(savedPost);
        commentRepository.save(comment);

        commentRepository.findByPost_Id(1l).forEach(x -> {
            System.out.println(x.getVotes());
        });
    }

}
