package com.example.demo.post;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
 * Sort의 경우 entity의 property 혹은 query의 alias가 아닌 경우, 사용 불가
 * 우회를 하자 JpaSort.unsafe()
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitleStartingWith(String title);
    List<Post> findByTitleStartingWith(String title, Sort sort);

    // #{#entityName}은 현재 리파지토리가 참조하고 있는 entity를 뜻합니다.
    // @Param을 사용한 namedParam > :title
    @Query("SELECT p, p.title AS #{#entityName} FROM Post AS p Where p.title = :title")
    List<Post> findByTitle(@Param("title") String title);

    @Query("SELECT p, p.title AS pTitle FROM Post AS p")
    List<Post> findAll(Sort sort);


}
