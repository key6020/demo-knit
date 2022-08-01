package com.task.demo2.repository;

import com.task.demo2.domain.ThreadLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadLikeRepository extends JpaRepository<ThreadLike, Long> {
    Integer countAllByThreadId(Long threadId);
}
