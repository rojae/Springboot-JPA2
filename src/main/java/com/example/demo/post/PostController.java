package com.example.demo.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;

    /*
        DomainClassConverter
        repository를 가지고
        id <~> entity 변환
        @PathVariable("id") Post post
        id에 해당하는 post의 값을 가져오겠다.

        비슷한 예로는 Formatter가 있는데
        문자열 <~> 엔티티 변환하는 법이다.
     */
    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable("id") Post post) {
        return post.getTitle();
    }
}
