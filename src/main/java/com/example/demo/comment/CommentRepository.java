package com.example.demo.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAll();
    Optional<Comment> findById(Long id);

    // closed projection
    <T> List<T> findByPost_Id(Long id, Class<T> type);

}
