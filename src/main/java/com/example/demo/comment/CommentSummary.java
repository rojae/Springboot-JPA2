package com.example.demo.comment;

import org.springframework.beans.factory.annotation.Value;

public interface CommentSummary {
    String getComment();

    int getUp();

    int getDown();

    // Comment의 up 컬럼과 down 컬럼을 합침
    // target은 comment이기 때문에, comment를 모두 가져올 수 밖에 없다
    // 즉, open projection
    @Value("#{target.up + ' ' + target.down}")
    String getVotes();
}

