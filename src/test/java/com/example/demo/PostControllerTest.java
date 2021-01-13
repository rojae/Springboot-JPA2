package com.example.demo;

import com.example.demo.post.Post;
import com.example.demo.post.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class PostControllerTest {

    @Autowired
    PostRepository postRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void save(){
        Post post = new Post();
        post.setTitle("test");
        Post savedPost = postRepository.save(post);             // persist

        // 현재의 경우에는 savedPost와 post는 같지만
        // 다를 수도 있다. merge의 위험성 때문
        // 값을 사용할때는 persist 상태인
        // 반환된 객체 savedPost를 사용하자
        assertThat(entityManager.contains(savedPost)).isTrue();
        assertThat(entityManager.contains(post)).isTrue();
        assertThat(savedPost == post).isTrue();

        post.setTitle("update1");

        List<Post> all = postRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    public void findByTitleStartWith(){
        // When
        Post post = new Post();
        post.setTitle("test title");
        postRepository.save(post);

        // Then
        List<Post> list = postRepository.findByTitleStartingWith("test");
        list.forEach(System.out::println);
        assertThat(list.size()).isEqualTo(1);
    }

    // queryMethod sort test
    @Test
    public void findByTitleStartingWithSort(){
        // When
        Post post = new Post();
        post.setTitle("test title");
        postRepository.save(post);

        Post post2 = new Post();
        post2.setTitle("taaaa");
        postRepository.save(post2);

        // Then
        List<Post> list = postRepository.findByTitleStartingWith("t", Sort.by("title"));
        list.forEach(System.out::println);
        assertThat(list.size()).isEqualTo(2);
    }

    // jpql queryMethod sort test
    @Test
    public void findAll(){
        // When
        Post post = new Post();
        post.setTitle("a");
        postRepository.save(post);

        Post post2 = new Post();
        post2.setTitle("b");
        postRepository.save(post2);

        // Then
        List<Post> list = postRepository.findAll(Sort.by("pTitle"));
        list.forEach(System.out::println);
        assertThat(list.size()).isEqualTo(2);
    }

    // jpql queryMethod unsafe case
    @Test
    public void unsafe(){
        // When
        Post post = new Post();
        post.setTitle("a");
        postRepository.save(post);

        Post post2 = new Post();
        post2.setTitle("bbbbb");
        postRepository.save(post2);

        // Then
        List<Post> list = postRepository.findAll(JpaSort.unsafe(Sort.Direction.DESC, "LENGTH(title)"));
        list.forEach(System.out::println);
        assertThat(list.size()).isEqualTo(2);
    }

}
