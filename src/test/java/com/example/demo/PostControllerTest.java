package com.example.demo;

import com.example.demo.post.Post;
import com.example.demo.post.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @Test
    public void getPost() throws Exception {
        Post post = new Post();
        post.setTitle("Test title");
        postRepository.save(post);

        mockMvc.perform(get("/posts/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Test title"));
    }

    @Test
    public void getPosts() throws Exception {
        Post post = new Post();
        post.setTitle("Test title");
        postRepository.save(post);

        mockMvc.perform(get("/posts/")
                .param("page","0")
                .param("size","10")
                .param("sort","created,desc")
                .param("sort","title"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    jsonPath("$.content[0].title", is("Test title"));
                });
    }
}
