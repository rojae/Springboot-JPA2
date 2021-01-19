package com.example.demo.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAll();
    Optional<Comment> findById(Long id);

    // 제너릭 타입을 주어
    // Comment 타입의 프로젝션과 CommentOnly
    // 타입의 프로젝션을 사용하자
    <T> List<T> findByPost_Id(Long id, Class<T> type);

}
