package com.example.demo;

import com.example.demo.post.Post;
import com.example.demo.post.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

        Post postUpdate = new Post();
        postUpdate.setId(post.getId());
        postUpdate.setTitle("test2");
        Post savedUpdate = postRepository.save(postUpdate);         // merge

        assertThat(entityManager.contains(savedUpdate)).isTrue();
        assertThat(entityManager.contains(postUpdate)).isFalse();   // persist가 아닌 merge가 되었기 때문에, 42번줄과는 다르다.
        assertThat(savedUpdate == postUpdate).isFalse();     // savedPost가 merge 되어, 다름

        // manage되고 있는 persist 상태의 객체를 수정해보자
        // 결과 : update가 된다
        savedUpdate.setTitle("update");

        List<Post> all = postRepository.findAll();

        all.forEach(System.out::println);

        assertThat(all.size()).isEqualTo(1);
    }

}
