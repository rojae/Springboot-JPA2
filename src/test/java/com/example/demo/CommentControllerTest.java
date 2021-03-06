package com.example.demo;

import com.example.demo.comment.*;
import com.example.demo.post.Post;
import com.example.demo.post.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
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

    // closed projection에서 getVotes를 확장하여 사용함
    @Test
    public void projection1() {
        Post post = new Post();
        post.setTitle("Test title");
        Post savedPost = postRepository.save(post);

        Comment comment = new Comment();
        comment.setComment("Test comment");
        comment.setPost(savedPost);
        commentRepository.save(comment);

        commentRepository.findByPost_Id(1l, CommentSummary.class).forEach(x -> {
            System.out.println(x.getVotes());
        });

    }

    // closed projection에서 getComment를 확장하여 사용함
    @Test
    public void projection2() {
        Post post = new Post();
        post.setTitle("Test title");
        Post savedPost = postRepository.save(post);

        Comment comment = new Comment();
        comment.setComment("Test comment");
        comment.setPost(savedPost);
        commentRepository.save(comment);
        commentRepository.findByPost_Id(1l, CommentOnly.class).forEach(x -> {
            System.out.println(x.getComment());
        });
    }

    @Test
    public void isBest(){
        commentRepository.findAll(CommentSpec.isBest());
    }

    @Test
    public void isGood(){
        commentRepository.findAll(CommentSpec.isGood());
    }

    @Test
    public void specPagable(){
        Page<Comment> page
                = commentRepository.findAll(CommentSpec.isBest()
                    .or(CommentSpec.isGood()), PageRequest.of(0, 10));
    }

    // Query By Example
    @Test
    public void queryByExample(){
        Comment prove = new Comment();
        prove.setBest(true);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                .withIgnorePaths("up", "down");
        Example<Comment> example = Example.of(prove,exampleMatcher);

        commentRepository.findAll(example);
    }

}
